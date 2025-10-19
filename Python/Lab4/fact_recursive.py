from functools import lru_cache
import sys
sys.setrecursionlimit(10000)

def fact_recursive(n):
    """Вернет факториал n (рекурсивная реализация)"""
    if n == 0: return 1
    return fact_recursive(n-1)*n

@lru_cache
def cached_fact_recursive(n):
    """Факториал с кэшированием. 
    
    Вернет факториал числа n."""
    
    if n == 0: return 1
    return fact_recursive(n-1)*n