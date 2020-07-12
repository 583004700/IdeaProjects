xx = globals()
print(xx)
# 内建模块
print(xx["__builtins__"].__dict__)


class Test1:
    num = 100
    num2 = 200


class Test11(Test1):
    pass


def t(self):
    pass


@classmethod
def t2(cls):
    pass


@staticmethod
def t3():
    pass
# type的返回值是一个类


Test2 = type("Test2", (), {"num": 100, "num2": 200, "t": t, "t2": t2, "t3": t3})

Test22 = type("Test22", (Test2,), {})

help(Test1)
help(Test2)
help(Test11)
help(Test22)