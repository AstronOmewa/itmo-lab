import unittest



def add(nums: list[int], target: int) -> list[int]:
    
    if any(type(el)!=int for el in nums):
        types = [(type(nums[i]), i) for i in range(len(nums)) if type(nums[i])!=int]
        s = ",\n".join([f"{types[i][0]} at pos {types[i][1]}" for i in range(len(types))])
        raise TypeError(f"list[int] argument contains non-int elements (\n"+s+"\n)")
    
    if type(nums) != list:
        raise TypeError("list[int] isn't instance of List()")
    
    if type(target) != int:
        raise TypeError("target isn't instance of int()")
    
    for i in range(len(nums)):
        for j in range(i+1,len(nums)):
            if nums[i]+nums[j] == target:
                return [i, j]
        
    return None
