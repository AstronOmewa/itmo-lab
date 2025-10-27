import matplotlib.pyplot as plt
from binTree import gen_bin_tree as gbt_it
from binTree_recursive import gen_bin_tree as gbt_rec
from benchmark import *

def main():
    r = list(range(1,10+1))
    test_depths = zip(r, [1 for _ in r])

    res_recursive = []
    res_iterative = []

    for n in test_depths:
        res_iterative.append(benchmark(gbt_it, [n], number=100, repeat=5))
        res_recursive.append(benchmark(gbt_rec, [n], number=100, repeat=5))
        
    plt.plot(r, res_recursive, label = 'Рекурсия без кеша')
    plt.plot(r, res_iterative, label = "Итеративно")
    plt.xlabel("n")
    plt.ylabel("Time (s)")
    plt.title("Сравнение рекурсивной (без кеша) и итеративной генерации бинарного дерева")
    plt.legend()
    plt.show()
    
    
if __name__=="__main__":
    main()