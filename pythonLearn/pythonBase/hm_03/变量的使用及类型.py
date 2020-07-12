qq_number = 583004700
number = 2
print(qq_number * number)

price = 15
weight = 2
money = price * weight
money = money - 5
print(money)

name = "小明"
age = 18
gender = True
height = 1.75
print(name)

if 1:
    print(True)
print(type(age))
# 在python2.0中，显示为long类型，而在3.0中，没有long类型
print(type(2**64))

# 查看python关健字
import keyword
print(keyword.kwlist)