# 无组中的数据不能修改，列表可以存不同的数据，但一般存储相同类型的数据，而无组可以存储类型的数据
info_tuple = ("zhangsan", 18, 1.75)
print(type(info_tuple))
print(info_tuple[1])

# 定义一个空元组
info_tuple = ()
# 定义一个元素的元组,需要在后面加逗号，否则的话会被认为整数类型
info_tuple = (5,)
print(info_tuple[0])

info_tuple = (1, 28, 5, 4, "张三")
print(info_tuple.count(28))
print(info_tuple.index(5))
print(len(info_tuple))
for i in info_tuple:
    print(str(i)+"\t", end="")

print()
info_list = list(info_tuple)
info_tuple = tuple(info_list)
print(info_list)
