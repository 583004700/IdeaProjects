num = 100


def sayHello():
    print("hello")


class Dog:
    def run(self):
        print("小狗跑")


# 在被其它模块导入时，下面的代码会自动执行
# __name__属性在当前模块中运行时值为__main__，而在其它模块导入时，值为包名.模块名
if __name__ == "__main__":
    print("模块1")