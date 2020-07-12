import copy


class B:
    pass


class A:
    def __init__(self):
        self.b = B()
    pass


aa = A()
bb = copy.copy(aa)
print(id(aa))
print(id(bb))

arr = [A(), A()]
arrc = copy.copy(arr)
arrcd = copy.deepcopy(arr)
print("arr[0] %s" % id(arr[0]))
print("arrc[0] %s" % id(arrc[0]))
print("arrcd[0] %s" % id(arrcd[0]))

print("arr[0]b %s" % id(arr[0].b))
print("arrc[0]b %s" % id(arrc[0].b))
print("arrcd[0]b %s" % id(arrcd[0].b))

a = [0]
print(id(a))
m = copy.deepcopy(a)
print(id(m))
# 如果元组中的数据是不可变的类型，那拷贝就会直接指向原来的元组
y = (1, 2)
y1 = copy.deepcopy(y)
print(id(y))
print(id(y1))

