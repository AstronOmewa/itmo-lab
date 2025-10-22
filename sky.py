import os
import math
import numpy as np
import matplotlib.pyplot as plt
from skyfield.api import Loader
from skyfield.data import hipparcos

m = 7.5
maxR = 1
H = np.random.randint(100,400)/6378

def draw_horizon(ax, angular_distance, max_radius=2.0, **kwargs):
    """
    Рисует горизонт на заданном угловом расстоянии от зенита
    
    Параметры:
    ax - оси matplotlib
    angular_distance - угловое расстояние от зенита в градусах (0-90)
    max_radius - радиус горизонта для 90° (по умолчанию 2.0)
    kwargs - дополнительные параметры для окружности (цвет, стиль и т.д.)
    """
    # Преобразуем угловое расстояние в радианы
    angle_rad = math.radians(angular_distance)
    
    # Рассчитываем радиус окружности для стереографической проекции
    # Формула: r = tan(θ/2)
    # Для 90°: r = tan(45°) = 2 * 1 = 2
    radius = math.tan(angle_rad / 2) * max_radius
    


    # Создаем окружность
    circle = plt.Circle((0, 0), radius, fill=False, **kwargs)
    ax.add_artist(circle)
    
    return circle

# Создаем директорию для данных
data_dir = os.path.expanduser('~/.skyfield-data')
os.makedirs(data_dir, exist_ok=True)

# Загрузка каталога Hipparcos
loader = Loader(data_dir)
hip_file = "C:\\Users\\AstronOmewa\\.skyfield-data\\hip_main.dat"
constellation_file = "C:\\Users\\AstronOmewa\\.skyfield-data\\constellationship.fab"

# Загрузка данных каталога
with open(hip_file, 'rb') as f:
    stars = hipparcos.load_dataframe(f)
    # print(stars)

# Фильтруем звезды
bright_stars = stars[stars['magnitude'] <= m].copy()

# Случайный центр карты
np.random.seed(np.random.randint(10,1000))
ra_center = np.random.uniform(0, 360)
dec_center = np.random.uniform(0, 90)

def stereographic_projection(ra, dec, ra0, dec0):
    # Преобразование в радианы
    ra_rad = np.radians(ra)
    dec_rad = np.radians(dec)
    ra0_rad = np.radians(ra0)
    dec0_rad = np.radians(dec0)
    
    # Разность RA
    delta_ra = ra_rad - ra0_rad
    
    # Тригонометрические компоненты
    cos_dec = np.cos(dec_rad)
    sin_dec = np.sin(dec_rad)
    cos_dec0 = np.cos(dec0_rad)
    sin_dec0 = np.sin(dec0_rad)
    cos_delta_ra = np.cos(delta_ra)
    
    # Формула стереографической проекции
    denominator = 1 + sin_dec0 * sin_dec + cos_dec0 * cos_dec * cos_delta_ra
    k = 2 / denominator
    x = k * cos_dec * np.sin(delta_ra)
    y = k * (cos_dec0 * sin_dec - sin_dec0 * cos_dec * cos_delta_ra)
    
    return x, y

# Применяем проекцию к звездам
x, y = stereographic_projection(
    bright_stars['ra_degrees'],
    bright_stars['dec_degrees'],
    ra_center,
    dec_center
)

# Фильтруем только видимые звезды
r = (x**2+y**2)**0.5
visible = r <= maxR + H/maxR
x_vis = x[visible]
y_vis = y[visible]
magnitudes = bright_stars[visible]['magnitude']

# Вычисляем положение северного полюса
x_np, y_np = stereographic_projection(
    [ra_center],
    [90],
    ra_center,
    dec_center
)

# Создаем фигуру с увеличенным DPI
plt.figure(figsize=(10, 10), facecolor='white', dpi=1200)  # Увеличиваем DPI для более четких мелких деталей
ax = plt.subplot(111, facecolor='white', aspect='equal')

# Размер точек - используем нелинейное масштабирование
sizes = 40 * 8**(-0.4 * magnitudes)   # Экспоненциальная зависимость от звездной величины
for size in sizes:
    size = max(0.25,size)
# sizes = np.clip(sizes, 0, 100)  # Ограничиваем минимальный и максимальный размер

# Ключевое исправление: убираем границы и работаем с цветом
# Вместо прозрачности регулируем яркость через цвет
colors = []
min_mag = np.min(magnitudes)
max_mag = np.max(magnitudes)

for mag in magnitudes:
    # Нормализуем величину от 0 (яркие) до 1 (тусклые)
    norm_mag = (mag - min_mag) / (max_mag - min_mag) if max_mag > min_mag else 0.5
    
    # Яркие звезды - белые, тусклые - серые
    brightness = 0  # Сохраняем диапазон яркости
    
    # Используем RGB без прозрачности
    colors.append((brightness, brightness, brightness))

# Рисуем звезды БЕЗ границ и БЕЗ прозрачности
ax.scatter(x_vis, y_vis, s=sizes, c=colors, edgecolors='none', alpha=1.0)

# Отмечаем северный полюс, если он виден
if (x_np**2+y_np**2)**0.5 <= maxR:
    # Для полюса используем другой маркер
    ax.scatter(x_np, y_np, s=5, color='red', alpha=1, edgecolor='none')
    ax.text(x_np[0], y_np[0], 'NCP', color='black', 
            fontsize=12, ha='center', va='bottom', weight='bold')


# Настройки осей
ax.set_xlim(-1.2*(maxR+H/maxR), 1.2*(maxR+H/maxR))
ax.set_ylim(-1.2*(maxR+H/maxR), 1.2*(maxR+H/maxR))
ax.axis('off')
plt.tight_layout(pad=0)
draw_horizon(ax, 90,maxR,edgecolor='green', linestyle='-.', linewidth=0.5, label='Мат. горизонт')
draw_horizon(ax, 90,maxR+H/maxR,edgecolor='red', linestyle='-', linewidth=0.5, label='Видимый горизонт')
# Сохраняем с повышенным DPI
plt.savefig(f'sky', bbox_inches='tight', pad_inches=0, transparent=False, dpi=600)

eq_ra = np.linspace(0,360,1000)
eq_dec = [0]*len(eq_ra)


for i in range(len(eq_ra)):
    
    ra = eq_ra[i]
    dec = eq_dec[i]
    x,y = stereographic_projection(
    eq_ra[i],
    eq_dec[i],
    ra_center,
    dec_center
    )
    if (x**2+y**2)**0.5<=maxR+H/maxR:
        ax.scatter(x, y, s=10, color='blue', alpha=0.5, edgecolor='none')
plt.savefig(f'sol-sky', bbox_inches='tight', pad_inches=0, transparent=False, dpi=600)
plt.close()