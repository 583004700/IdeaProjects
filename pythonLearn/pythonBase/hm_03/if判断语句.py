age = int(input("请输入年龄："))
if age < 0 or age > 120:
    print("年龄不正确")
elif age >= 18:
    print("可以进网吧happy了！")
else:
    print("回家写作业吧！")
print("呵呵")

ticket = True
dan = True
if ticket:
    if dan:
        print("不能带危险物品，不能进")
    else:
        print("可以进")
else:
    print("没有车票，不能进")

if dan:
    print("不能带危险物品，不能进")
elif ticket:
    print("可以进")
else:
    print("没有车票，不能进")