import unittest, guessr


class Test(unittest.TestCase):
    def testSlowGuessing(self):
        """Тестирует медленный перебор
        """
        self.assertEqual(
            guessr.guess_number(
                32,list(range(1,32+1)),'seq'
            ),
            (32,32)
        )
    def testBinGuessing(self):
        """Тестирует бинпоиск
        """
        self.assertEqual(
            guessr.guess_number(
                32,list(range(1,32+1)),"bin"
            ),
            (32, 6)
        )
        
    def testIncorrectGuessingWay(self):
        """Тестирует случай неправильного аргумента guessing_way
        """
        with self.assertRaises(ValueError, 
                          guessr.guess_number(
                              -1,
                              list(range(1,2)),
                              "bin")
                        ) as e:
            pass
        
    def testIncorrectList(self):
        """Тестирует случай неправильного типа в num_list
        """
        with self.assertRaises(TypeError,
                               guessr.guess_number(
                                   1,
                                   ['',1,set([1,2])],
                                   "seq")
                        ) as e:
            pass
    
    def testNumNotInList(self):
        """Тестирует случай отсутствия искомого значения в списке
        """
        with self.assertRaises(ValueError,
                               guessr.guess_number(
                                   3,
                                   [1,2],
                                   "bin"
                               )
                        ) as e:
            pass
        
