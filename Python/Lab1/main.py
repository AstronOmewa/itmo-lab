import unittest
from CustomExceptions import *


def add(nums: list[int], target: int) -> list[int]:
    
    if any(type(el)!=int for el in nums):
        types = [(type(nums[i]), i) for i in range(len(nums)) if type(nums[i])!=int]
        s = ",\n".join([f"{types[i][0]} at pos {types[i][1]}" for i in range(len(types))])
        raise TypeError(f"list[int] argument contains non-int elements (\n"+s+"\n)")
    
    if type(nums) != list:
        raise TypeError("list[int] isn't instance of List()")
    
    if type(target) != int:
        raise TypeError("target isn't instance of int()")
    
    # if len(set(nums))==1 and (not (nums[0]*target//nums[0]!=target or target//nums[0]>len(nums))):
    #     raise Exception("all elements in nums are equal between each other")
    
    if len(set(nums))==1 and (nums[0]*target//nums[0]!=target or target//nums[0]>len(nums)):
        raise Exception("all elements in nums are equal between each other AND no matching indicies detected")
    
    for i in range(len(nums)):
        for j in range(i+1,len(nums)):
            if nums[i]+nums[j] == target:
                return [i, j]
        
    raise NoMatchingIndiciesFoundException("None")
