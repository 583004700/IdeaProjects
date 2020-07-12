# 参数前一个*可以接收元组，参数前两个*可以接收字典
def demo(*args, **kwargs):
    print(args)
    print(kwargs)


# 1,3,4,7,2传给了args,name和age传给了kwargs
demo(1, 3, 4, 7, 2, name="小明", age=18)

args = (4, 5, 7)
kwargs = {"name": "张三", "age": 18}
# 元组和字典的拆包
demo(*args, **kwargs)


def demo1(yz):
    print(yz[0])


demo1((1, 4, 7))