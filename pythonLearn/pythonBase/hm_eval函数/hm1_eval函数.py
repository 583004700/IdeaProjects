# eval 函数会把参数当做语句执行
# *-* coding:utf8 *-*
import os
input_str = input("请输入算术题")

value = eval(input_str)

print(value)
os.system("echo hello")

