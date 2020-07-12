class Animal:

    def __init__(self, name):
        self.name = name

    def eat(self):
        print("吃")
        self.run()
        return self

    def drink(self):
        print("喝")

    def run(self):
        print("跑")

    def sleep(self):
        print("%s 睡" % self.name)


class Dog(Animal):

    def bark(self):
        print("汪汪叫")
    # 重写run方法
    def run(self):
        print("%s 跑" % self.name)

wangcai = Dog("旺财");
wangcai.eat()
wangcai.drink()
wangcai.run()
wangcai.sleep()
wangcai.bark()
wangcai.eat().bark()