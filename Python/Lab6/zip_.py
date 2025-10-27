def zip_(*its):
    return list(map(lambda *its: its, *its))

print(zip_(["x",1],["y",2],["z", 3, 4]))

lst = [132, 118, 123, 129, 114, 127, 45, 118, 128, 45, 112, 124, 122, 118, 123, 116]

s = ""

for x in lst:
    s += chr(x-13)
    
print(s)