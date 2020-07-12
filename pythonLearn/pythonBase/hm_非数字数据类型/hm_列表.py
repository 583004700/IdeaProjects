name_list = ["zhangsan","lisi","wangwu"]

print(name_list[2])
print(name_list.index("lisi"))

name_list[1] = "李四"

name_list.append("赵六")
name_list.insert(2,"马七")
temp_list = ["孙悟空","司马光"]
name_list.extend(temp_list)

# pop不加参数时删除最后一个元素
name_list.pop()
# 删除列表中第一次出现的数据，如果数据不存在，会报错
name_list.remove("wangwu")
# name_list.clear()
# del关健字可以从列表中删除一个元素
del name_list[2]

name = "张三"
# del关健字也可以删除一个变量
del name

# 统计lisi出现的次数
count = name_list.count("孙悟空")
print(count)
print(len(name_list))
print(len("孙悟"))

print(name_list)

i = [4,3,6,8,87,32,56,32]
i.sort()
print(i)
i.sort(reverse=True)
print(i)
i.reverse()
print(i)

for c in i:
    print(c)


