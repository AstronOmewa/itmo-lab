from main import *

class TestPy(unittest.TestCase):
    def test1(self):
        self.assertEqual(
            add(nums = [2,7,11,15], target = 9), [0,1]
        )
    
    def test2(self):
        self.assertEqual(
            add(nums = [3,2,4], target = 6), [1,2]
        )
    
    def test3(self):
        self.assertEqual(
            add(nums = [3,3], target = 6), [0,1]
        )
        
    def test_nums_not_list(self):
        
        self.assertEqual(add("", 1), TypeError)
        with self.assertRaises(TypeError):
            raise TypeError("Type of nums=list[int] doesn't match")
        
    def test_all_els_in_nums_is_instance_of_int(self):
        
        self.assertEqual(add(['',1.2, "asdsada",[],{}], 1), TypeError)
        with self.assertRaises(TypeError):
            raise TypeError("Type of nums: list[int] doesn't match")
        
    def test_equal_nums(self):
        
        self.assertEqual(add([1,1,1,1], 1), None)
        with self.assertRaises(TypeError):
            raise Warning("Equal ints in nums: list[int] detected")
        
    def test_none(self):
        self.assertEqual(self):
        with self.assertRaises(NoMatchingIndiciesFoundException):
            raise NoMatchingIndiciesFoundException("None")
    

if __name__== "__main__":
    unittest.main(verbosity=2)