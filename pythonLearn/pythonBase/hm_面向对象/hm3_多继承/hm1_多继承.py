class A:
    def test(self):
        print("Atest方法")


class B:
    def demo(self):
        print("Bdemo方法")
        # 与java不同的是如果子类有这个属性，则可以直接使用
        print(self.name)

    def test(self):
        print("Btest方法")


class C(B, A):
    """同时继承了A类和B类，如果有重名的方法，会调用先继承的那个"""
    def __init__(self, name):
        self.name = name
        print("C初始化%s" % self.name)

c = C("小明")
c.test()
c.demo()