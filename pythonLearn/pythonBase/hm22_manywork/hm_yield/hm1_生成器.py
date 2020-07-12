# 返回一个列表
nums = [x*2 for x in range(10)]
print(nums)
# 返回一个生成器
nums = (x*2 for x in range(10))
print(nums)


# yield实现生成器
def create_num(all_num):
    a, b = 0, 1
    current_num = 0
    while current_num < all_num:
        # print(a)
        ret = yield a
        print(ret)
        a, b = b, a+b
        current_num += 1


# 创建一个生成器
obj = create_num(10)
# for num in obj:
#     print(num)

print("---------------")
# print(next(obj))
print(obj.send(None))
print(obj.send("2"))
print(obj.send("3"))

# send方式取下一个值,send方法一股不会用在第一次，因为没有对象去保存值
print(obj.send("4"))

