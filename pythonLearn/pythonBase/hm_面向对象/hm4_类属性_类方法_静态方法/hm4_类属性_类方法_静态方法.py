class Tool(object):
    # 类属性，所有对象共有
    count = 0

    @classmethod
    def class_method(cls, tool):
        # 定义一个类方法,cls引用的是类
        print(cls.count)
        print(tool.count)

    # 如果即不需要访问类的属性，也不需要访问对象的属性，那么就可以为静态方法
    @staticmethod
    def run():
        print("跑")

    def __init__(self, name):
        self.name = name
        Tool.count += 1

    def __f1(self, f):
        print(f())

    def f2(self):
        # 调用f1时将f3作为参数
        self.__f1(self.__f3)

    def __f3(self):
        print("f3")
        return "f3"

tool1 = Tool("斧头")
tool2 = Tool("剪刀")
print(Tool.count)
# 用对象名.类 属性赋值,不会改变类属性的值，只会给对象添加一个属性
tool1.count = 100
print(Tool.count)
print(tool1.count)
tool1.f2()
# 调用类方法，也可以将当前对象传递给类方法
Tool.class_method(tool1)
# 调用静态方法
Tool.run()