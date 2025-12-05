import matplotlib.pyplot as plt
import math
from integrate import integrate
import numpy as np

f = np.cos
a, b = 0, math.pi/2

configs = [
    (10, 1),
    (20, 2),
    (40, 3)
]

x_plot = np.linspace(a, b, 400)
y_plot = f(x_plot)

plt.figure(figsize=(12, 7))

plt.plot(x_plot, y_plot, linewidth=2)

colors_upper = ["red", "purple", "brown"]
colors_lower = ["green", "blue", "orange"]

for (n_iter, bunches), cu, cl in zip(configs, colors_upper, colors_lower):
    xs = np.linspace(a, b, n_iter + 1)
    step = (b - a) / n_iter
    
    # lower rects
    for x in xs[:-1]:
        plt.gca().add_patch(
            plt.Rectangle((x, 0), step, f(x),
                          alpha=0.25, color=cl)
        )

    # upper rects
    for x in xs[:-1]:
        plt.gca().add_patch(
            plt.Rectangle((x, 0), step, f(x + step),
                          alpha=0.25, color=cu)
        )

    val = integrate(f, a, b, n_iter=n_iter, bunches=bunches)
    plt.text(b + 0.02, f(a) - 0.1*configs.index((n_iter,bunches)),
             f"n={n_iter}, b={bunches}, I≈{val:.5f}", fontsize=10)

plt.xlabel("x")
plt.ylabel("f(x)")
plt.title("График cos(x) с верхними и нижними интегральными суммами\nдля разных n_iter и bunches")
plt.xlim(a, b + 0.3)
plt.ylim(0, 1.1)

plt.show()