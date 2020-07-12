class Woman:
    def __init__(self, name):
        self.name = name
        self.__age = 18

    def say(self):
        # 在类的内部可以访问私有属性
        print("%s,%s" % (self.name, self.__age))
        self.__secret()

    def __secret(self):
        print("私有方法")


w1 = Woman("小芳")
w1.say()
# 加上__代表私有属性，私有方法不能在外面访问
# print(w1.__age)
# 私有方法在外部不能直接访问
# w1.__secret()

# 但也可以访问,不推荐使用
print(w1._Woman__age)
w1._Woman__secret()