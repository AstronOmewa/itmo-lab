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
        
        add("", 1) # для проверки assertRaises
        with self.assertRaises(NumsNotListError) as e:
            print(e.msg)
            
    def test_all_els_in_nums_is_instance_of_int(self):
        
        add(['',1.2, "asdsada",[],{}], 1) # для проверки assertRaises
        with self.assertRaises(NumsContainsNotIntError) as e:
            print(e.msg)
    
    def test_target_isnan(self):
        
        add([1,1], "wqe") # для проверки assertRaises
        with self.assertRaises(TargetIsNANError) as e:
            print(e.msg)
    

if __name__== "__main__":
    unittest.main(verbosity=2)