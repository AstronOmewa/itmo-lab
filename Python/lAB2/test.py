import unittest
import guessr as gs

class TestPy(unittest.TestCase):
    def test_1(self):
        self.assertEqual(
            gs.guess_number(2^10, list(range(1,2**11+1)), 'binary'), (2^10, 8)
        )
    def test_2
        
