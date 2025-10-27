# Root = 8; height = 4, 
# left_leaf = root+root/2, 
# right_leaf = root^2
# Вариант 6-L

def left_branch(root):
    """Генерирует левый лист дерева
    
    @param root: int -- 
    """
    return root + root / 2

# Вариант 6-R
def right_branch(root):
    """Генерирует правый лист дерева
    """
    return root ** 2

def gen_bin_tree(height: int, root: int, l_b=left_branch, r_b=right_branch):
    """Итеративная генерация бинарного дерева.
    
    Позиционные аргументы:
    height -- высота (количество уровней) дерева
    root -- значение в корне дерева
    l_b, r_b -- ссылка на объекты функций, генерирующих значения левого и правого листа
    Вернет словарь, содержащий листья деревья."""

    main = {root: []}
    nodes = [main]
    new_nodes = []
    for _ in range(height):
        new_nodes = []
        for node in nodes:
            val = list(node.keys())
            val = val[0]

            node[val].append({l_b(val): []})
            node[val].append({r_b(val): []})
            new_nodes += node[val]
        nodes = new_nodes
    #     print(new_nodes)
    # print(nodes)

    return main

    
# if __name__ == "__main__":
#     print(gen_bin_tree(root = 8, height = 4))
