def sum_2_num(num1, num2):
    """对两个数字求和"""
    result = num1 + num2
    return result
    print("后面的代码不会被执行")


sum1 = sum_2_num(10, 20)
sum2 = sum_2_num(20, 30)
print(sum1)
print(sum2)
sum3 = sum_2_num(num1=10,num2=20)
print(sum3)