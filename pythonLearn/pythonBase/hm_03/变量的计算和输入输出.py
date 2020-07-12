i = 10
f = 10.5
b = True
print(i+f)
print(i+b)
print(f-b)
first_name = "三"
last_name = "张"
print(last_name+first_name)
print(last_name*50)

# 从键盘获取输入
password = input("请输入银行卡密码：")
print(password)
print(type(int(password)))
print(type(float(password)))
# 格式化输出
name = "小明"
print("我的名字叫 %s" % name)

student_no = 1
print("我的学号是 %06d" % student_no)  # 如果不到6位，则用0占位

price = 5.7
weight = 8.5
money = price * weight
print("苹果单价：%.03f,重量：%.02f,总价：%f" % (price,weight,money))

scale = 0.25
print("比例是：%.2f%%" % (scale*100))



