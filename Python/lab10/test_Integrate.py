import unittest
import math
from integrate import integrate
from timeit import timeit
from DivergentException import *

class TestIntegrate(unittest.TestCase):
    
    def test_cos(self):
        self.assertAlmostEqual(
            integrate(math.cos, 0, math.pi/2, n_iter=1000),
            1.0,
            places=2
        )

    def test_square(self):
        self.assertAlmostEqual(
            integrate(lambda x: x*x, 0, 1, n_iter=2000),
            1/3,
            places=2
        )

    def test_divergent(self):
        with self.assertRaises(ZeroDivisionError):
            integrate(lambda x: 1/x, -1, 1, n_iter=1000)


if __name__ == "__main__":
    timer = timeit(
    "integrate(math.sin, 0, math.pi, n_iter=10000, bunches=100)",
    globals=globals(),
    number=5
    )

    print("Время выполнения:", timer/5, "секунд")
    unittest.main()