def input_number():
    try:
        num = float(input("请输入一个数字"))
        print("你输入的数字为 %s" % num)
    except:
        input_number()
    print("哈哈")

# input_number()


try:
    i = int(input("请输入一个整数"))
    result = 8 / i
    print(i)
except ZeroDivisionError:
    print("除零错误")
except (ValueError,ZeroDivisionError):
    print("值错误")
except Exception as result:
    print("异常 % s" % result)
else:
    print("没有异常才会执行的代码")
finally:
    print("不管有没有异常都会执行的代码")


