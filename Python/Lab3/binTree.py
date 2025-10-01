# Root = 8; height = 4, 
# left_leaf = root+root/2, 
# right_leaf = root^2
# Вариант 6-L

def left_branch(root):
    return root + root / 2


# Вариант 6-R
def right_branch(root):
    return root ** 2

def gen_bin_tree(height: int, root: int, l_b=left_branch, r_b=right_branch): # recursive
    if height > 0:
        return {str(root) : [gen_bin_tree(height-1, l_b(root)), gen_bin_tree(height-1, r_b(root))]}
    else: 
        return {str(root) : []}
        

if __name__ == "__main__":
    print(gen_bin_tree(root = 8, height = 4))
