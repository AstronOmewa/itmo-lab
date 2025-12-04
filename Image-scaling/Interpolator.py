from PIL import Image
import numpy as np
from Color import Color
from typing import Optional, Tuple
import os
os.chdir(os.path.dirname(__file__))

class Interpolator:
    """
    Интерполятор для масштабирования изображений с поддержкой пользовательского тензора пулинга.
    
    Тензор пулинга (pooling tensor) содержит промежуточные значения цветов между основными пикселями.
    Это позволяет задавать качество интерполяции вручную.
    """
    
    def __init__(self, image_path: str = "image.jpg"):
        """
        Инициализация интерполятора с загрузкой изображения.
        
        Args:
            image_path: Путь к файлу изображения
        """
        self.image = Image.open(image_path)
        self.image_rgb = self.image.convert('RGB')
        self.width, self.height = self.image_rgb.size
        self.pixels = self.image_rgb.load()
        
        # Инициализируем тензор пулинга пустым (будет заполнен при необходимости)
        self.pooling_tensor = None
        
    def set_pooling_tensor(self, tensor: np.ndarray) -> None:
        """
        Устанавливает тензор пулинга вручную.
        
        Args:
            tensor: NumPy массив формата (height, width, 3) с промежуточными значениями цветов
        """
        if tensor.shape[2] != 3:
            raise ValueError("Тензор должен иметь 3 канала (RGB)")
        self.pooling_tensor = tensor
        
    def get_pooling_tensor(self) -> np.ndarray:
        """
        Получает текущий тензор пулинга или создает его по умолчанию.
        
        Returns:
            NumPy массив с значениями пикселей (height, width, 3)
        """
        if self.pooling_tensor is None:
            # Создаем тензор пулинга из текущего изображения
            self.pooling_tensor = np.array(self.image_rgb)
        return self.pooling_tensor
    
    def interpolate_linear(self, pixel_distance: float = 1.0) -> Image.Image:
        """
        Линейная интерполяция (bilinear interpolation) для масштабирования изображения.
        Оптимизирована с использованием векторизованных операций NumPy.
        
        Args:
            pixel_distance: Расстояние между интерполируемыми пикселями.
                          1.0 = каждый пиксель интерполируется
                          2.0 = каждый второй пиксель интерполируется (уменьшение в 2 раза)
                          0.5 = интерполируется каждый пол-пиксель (увеличение в 2 раза)
            
        Returns:
            Интерполированное PIL изображение
        """
        tensor = self.get_pooling_tensor()
        
        scale_factor = 1.0 / pixel_distance
        new_height = int(self.height * scale_factor)
        new_width = int(self.width * scale_factor)
        
        # Создаем сетку координат для всех новых пикселей (векторизация)
        y_coords = np.arange(new_height) / scale_factor
        x_coords = np.arange(new_width) / scale_factor
        
        # Целые части координат
        y0 = np.floor(y_coords).astype(np.int32)
        x0 = np.floor(x_coords).astype(np.int32)
        y1 = np.minimum(y0 + 1, self.height - 1)
        x1 = np.minimum(x0 + 1, self.width - 1)
        
        # Дробные части
        dy = (y_coords - y0)[:, np.newaxis]  # (new_height, 1)
        dx = (x_coords - x0)[np.newaxis, :]  # (1, new_width)
        
        # Весовые коэффициенты для билинейной интерполяции
        w00 = (1 - dx) * (1 - dy)  # (new_height, new_width)
        w10 = dx * (1 - dy)
        w01 = (1 - dx) * dy
        w11 = dx * dy
        
        # Применяем интерполяцию векторизованно
        # Используем broadcasting для всех каналов сразу
        new_image_array = (
            tensor[np.ix_(y0, x0)] * w00[..., np.newaxis] +
            tensor[np.ix_(y0, x1)] * w10[..., np.newaxis] +
            tensor[np.ix_(y1, x0)] * w01[..., np.newaxis] +
            tensor[np.ix_(y1, x1)] * w11[..., np.newaxis]
        )
        
        return Image.fromarray(np.uint8(np.clip(new_image_array, 0, 255)), 'RGB')
    
    def interpolate_nearest(self, pixel_distance: float = 1.0) -> Image.Image:
        """
        Интерполяция методом ближайшего соседа (nearest neighbor).
        Быстрый, но менее гладкий метод. Полностью векторизирован.
        
        Args:
            pixel_distance: Расстояние между интерполируемыми пикселями.
                          1.0 = каждый пиксель интерполируется
                          2.0 = каждый второй пиксель интерполируется
                          0.5 = интерполируется каждый пол-пиксель (увеличение в 2 раза)
            
        Returns:
            Интерполированное PIL изображение
        """
        tensor = self.get_pooling_tensor()
        
        scale_factor = 1.0 / pixel_distance
        new_height = int(self.height * scale_factor)
        new_width = int(self.width * scale_factor)
        
        # Векторизованное вычисление ближайших индексов
        y_coords = np.round(np.arange(new_height) / scale_factor).astype(np.int32)
        x_coords = np.round(np.arange(new_width) / scale_factor).astype(np.int32)
        
        # Клипируем индексы
        y_coords = np.clip(y_coords, 0, self.height - 1)
        x_coords = np.clip(x_coords, 0, self.width - 1)
        
        # Используем meshgrid и fancy indexing для быстрого доступа
        yy, xx = np.meshgrid(y_coords, x_coords, indexing='ij')
        new_image_array = tensor[yy, xx]
        
        return Image.fromarray(np.uint8(new_image_array), 'RGB')
    
    def interpolate_cubic(self, pixel_distance: float = 1.0) -> Image.Image:
        """
        Кубическая интерполяция (более качественная, чем билинейная).
        Оптимизирована с использованием векторизованных операций.
        
        Args:
            pixel_distance: Расстояние между интерполируемыми пикселями.
                          1.0 = каждый пиксель интерполируется
                          2.0 = каждый второй пиксель интерполируется
                          0.5 = интерполируется каждый пол-пиксель (увеличение в 2 раза)
            
        Returns:
            Интерполированное PIL изображение
        """
        tensor = self.get_pooling_tensor()
        
        scale_factor = 1.0 / pixel_distance
        new_height = int(self.height * scale_factor)
        new_width = int(self.width * scale_factor)
        
        def cubic_kernel_vectorized(t):
            """Кубическое ядро интерполяции (векторизированное)"""
            t = np.abs(t)
            result = np.zeros_like(t)
            
            mask1 = t < 1
            mask2 = (t >= 1) & (t < 2)
            
            result[mask1] = 1 - 2*t[mask1]**2 + t[mask1]**3
            result[mask2] = -4 + 8*t[mask2] - 5*t[mask2]**2 + t[mask2]**3
            
            return result
        
        # Координаты в исходном изображении
        y_coords = np.arange(new_height) / scale_factor
        x_coords = np.arange(new_width) / scale_factor
        
        # Целые части
        x_int = np.floor(x_coords).astype(np.int32)
        y_int = np.floor(y_coords).astype(np.int32)
        
        # Дробные части
        dx = x_coords - x_int
        dy = y_coords - y_int
        
        # Инициализируем результат
        new_image_array = np.zeros((new_height, new_width, 3), dtype=np.float32)
        
        # Применяем кубическую интерполяцию для всех соседних пикселей
        for j in range(-1, 3):
            for i in range(-1, 3):
                # Индексы соседних пикселей (с клипированием)
                py = np.clip(y_int + j, 0, self.height - 1)
                px = np.clip(x_int + i, 0, self.width - 1)
                
                # Весовые коэффициенты (векторизированные)
                wy = cubic_kernel_vectorized(j - dy)  # (new_height,)
                wx = cubic_kernel_vectorized(i - dx)  # (new_width,)
                
                # Внешнее произведение весов для всех пикселей
                weight = wy[:, np.newaxis] * wx[np.newaxis, :]  # (new_height, new_width)
                
                # Применяем веса ко всем каналам сразу (broadcasting)
                new_image_array += tensor[np.ix_(py, px)] * weight[..., np.newaxis]
        
        return Image.fromarray(np.uint8(np.clip(new_image_array, 0, 255)), 'RGB')
    
    def save_result(self, image: Image.Image, filename: str) -> None:
        """
        Сохраняет результат интерполяции в файл.
        
        Args:
            image: PIL изображение для сохранения
            filename: Имя файла для сохранения
        """
        image.save(filename)
        print(f"Изображение сохранено в {filename}")


# Пример использования
if __name__ == "__main__":
    # Создаем интерполятор
    interpolator = Interpolator("image.jpg")
    print(f"Загруженное изображение: {interpolator.width}x{interpolator.height}")
    
    # Пример 1: Простое увеличение в 2 раза (расстояние между пикселями = 0.5)
    print("\nБилинейная интерполяция (расстояние между пикселями: 0.5)...")
    result_linear = interpolator.interpolate_linear(0.5)
    interpolator.save_result(result_linear, "scaled_image.png")
    
    # Пример 2: Использование кубической интерполяции (лучшее качество)
    print("\nКубическая интерполяция (расстояние между пикселями: 0.5)...")
    result_cubic = interpolator.interpolate_cubic(0.5)
    interpolator.save_result(result_cubic, "scaled_image_cubic.png")
    
    # Пример 3: Уменьшение в 2 раза (расстояние между пикселями = 2.0)
    print("\nУменьшение (расстояние между пикселями: 2.0)...")
    result_small = interpolator.interpolate_linear(2.0)
    interpolator.save_result(result_small, "scaled_image_small.png")
    
    # Пример 3: Использование пользовательского тензора пулинга
    print("\nИспользование пользовательского тензора пулинга...")
    
    # Получаем текущий тензор
    custom_tensor = np.array(interpolator.image_rgb)
    
    # Можем модифицировать его по собственным правилам
    # Например, применить размытие или другие преобразования
    from scipy.ndimage import gaussian_filter
    custom_tensor_blurred = gaussian_filter(custom_tensor, sigma=1)
    
    # Устанавливаем модифицированный тензор
    interpolator.set_pooling_tensor(custom_tensor_blurred)
    
    # Интерполируем с использованием модифицированного тензора (расстояние 0.5 = увеличение в 2 раза)
    result_custom = interpolator.interpolate_linear(0.5)
    interpolator.save_result(result_custom, "scaled_image_custom_tensor.png")
    
    print("\nГотово!")
