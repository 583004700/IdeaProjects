num = 100

def f1():
    global num
    num = 60

def f2():
    print(num)

f1()
f2()