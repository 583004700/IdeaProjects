class A:
    def test(self):
        print("Atest方法")


class B:
    def demo(self):
        print("Bdemo方法")

    def test(self):
        print("Btest方法")


class C(B, A):
    """同时继承了A类和B类，如果有重名的方法，会调用先继承的那个"""
    pass

c = C()
c.test()
c.demo()

# 确定C类的对象方法的调用顺序
# [<class '__main__.C'>, <class '__main__.B'>, <class '__main__.A'>, <class 'object'>]
print(C.mro())