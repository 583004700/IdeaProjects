class Cat:

    def __init__(self, name):
        # self.name = "Tom"
        self.name = name
        print("初始化方法，创建对象时会调用")

    def eat(self):
        print("%s吃鱼" % self.name)

    def drink(self):
        print("%s喝水" % self.name)

    def __del__(self):
        print("对象销毁时会被调用 % s" % self.name)

    def __str__(self):
        return "toString" + self.name

tom = Cat("tom")
# 可以在类的外部给对象增加属性，不推荐使用
# tom.name = "tom"
tom.eat()
tom.drink()
print(tom)
print(id(tom))
print("%x" % id(tom))

lazy = Cat("大懒猫")
#lazy.name = "大懒猫"
lazy.eat()
lazy.drink()
del tom
print(lazy)
