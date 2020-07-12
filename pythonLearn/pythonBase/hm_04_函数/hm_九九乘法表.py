a = 100
# 注释


def multiple_table():
    """文档注释，方便调用时查看，打印九九乘法表"""
    print(a)
    i = 1
    while i < 10:
        k = 1
        while k <= i:
            print(str(k)+"*"+str(i)+"="+str(i*k)+"  ", end="")
            k += 1
        print("")
        i += 1
