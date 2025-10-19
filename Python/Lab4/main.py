import timeit, matplotlib.pyplot as plt
from fact_iterative import fact_iterative as fact_it
from fact_recursive import fact_recursive as fact_rec
from fact_recursive import cached_fact_recursive as cached_fact_rec
from benchmark import benchmark

def main():
    test_data = range(10,3000,10)

    res_recursive = []
    res_rec_cached = []
    res_iterative = []

    for n in test_data:
        res_recursive.append(benchmark(fact_rec, [n], number=100, repeat=5))
        res_rec_cached.append(benchmark(cached_fact_rec, [n], number=100, repeat=5))
        res_iterative.append(benchmark(fact_it, [n], number=100, repeat=5))
        
    plt.plot(test_data, res_recursive, label = 'Рекурсия без кеша')
    plt.plot(test_data, res_iterative, label = "Итеративный")
    plt.plot(test_data, res_rec_cached, label = "Рекурсия с кешем")
    plt.xlabel("n")
    plt.ylabel("Time (s)")
    plt.title("Сравнение рекурсивной (с кешем, без кеша) и итеративной реализации факториала")
    plt.legend()
    plt.show()
    
    
if __name__=="__main__":
    main()