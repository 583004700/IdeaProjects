class Foo:
    def func(self):
        pass

    # 定义property属性，装饰器,在foo.prop取值时会调用这个方法
    @property
    def prop(self):
        return 100

    # 在foo.prop = 100 这种赋值语句时会调用这个方法，value为100
    @prop.setter
    def prop(self, value):
        self.a = value

    # 在del foo.prop时会调用这个方法
    @prop.deleter
    def prop(self):
        print("delete")

    # 通过 foo.prop1取值时会调用这个方法
    def get_prop1(self):
        return "哈哈"

    def set_prop1(self, value):
        self.b = value

    def del_prop1(self):
        pass

    # Foo.prop1.__doc__时，可以查看description...
    prop1 = property(get_prop1, set_prop1, del_prop1, "description...")


foo = Foo()
foo.func()
# 调用时不用小括号
print(foo.prop)
# 不能再调用这个方法
# print(foo.prop())
print(foo.prop1)
