class Line5:
    def __init__(self, k, b):
        self.k = k
        self.b = b

    def __call__(self, x):
        print(self.k * x + self.b)


line = Line5(1, 2)
line(5)


# 闭包
def line6(k, b):
    def create_y(x):
        print(k*x+b)
    return create_y


f1 = line6(1, 2)
f1(5)