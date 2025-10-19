import timeit

def benchmark(function, data, repeat = 1, number = 100):
    """Вернет среднее время выполнения фунции function
    
    Позиционные аргументы:
    function -- тестируемая функция
    data -- набор данных (бенчмарк) для тестов
    
    repeat -- количество повторов тестирования для каждого x из data
    
    """
    times = []
    for n in data:
        times.append(min(timeit.repeat(lambda: function(n), number=number, repeat=repeat)))
    return sum(times)/len(times)