s = "Hello Python"
s2 = '我的名字叫"zhuwb"'
for c in s:
    print(c)

print(s2)
print(s2[2])
print(s2.index("叫"))
print(len(s2))
print(s2.count("z"))

str_num = "9\u00b2哈"
print(str_num.isdecimal())
print(str_num.isdigit())
print(str_num.isnumeric())
print(str_num.find("哈"))
print(str_num)
str_num = "0123456789"
#  截取字符串[起始:结束:步长]
print(str_num[2:6])
print(str_num[5:])
print(str_num[::2])
print(str_num[1::2])
print(str_num[-1::-1])
