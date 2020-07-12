def priviege(url):
    print(url)
    def set_func(func):
        print("1")
        def check(*args, **kwargs):
            print("权限验证")
            return func(*args, **kwargs)+"1"
        return check
    return set_func


def priviege2(func):
    print("2")
    def check(*args, **kwargs):
        print("权限验证2")
        return func(*args, **kwargs)+"2"
    return check


# 当有多个装饰器时，会先装饰下面的，但调用时是先调用上面的
@priviege("image")
@priviege2
def login(username, *args, **kwargs):
    print("登录方法")
    print(username)
    print(args)
    print(kwargs)
    return "OK"


# check = priviege(login)
# check()

# ret = login("zhangsan", 100, 200, m=1)
# print(ret)

