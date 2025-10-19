def fact_iterative(n):
    """Вернет факториал n (итеративная реализация)"""
    res = 1
    for x in range(2,n+1):
        res*=x
    return res