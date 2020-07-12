class Animal:

    def __init__(self, name):
        print("父类初始化方法")
        self.name = name
        # 子类中不能访问父类的私有属性和方法
        self.__age = 18
        self.age = 20

    def eat(self):
        print("吃")
        return self

    def drink(self):
        print("喝")

    def run(self):
        print("狗狗跑%s" % self.name)

    def sleep(self):
        print("%s 睡" % self.name)


class Dog(Animal):
    def __init__(self, name):
        super().__init__(name)
        self.name = name

    def bark(self):
        print("汪汪叫")

    def run(self):
        #  在子类中显示调用父类
        super().run()
        # 或者,不推荐使用
        Animal.run(self)
        print("%s 跑" % self.name)
        print(self.age)

tom = Dog("tom")
tom.run()