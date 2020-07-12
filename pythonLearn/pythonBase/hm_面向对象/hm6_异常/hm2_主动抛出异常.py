def input_password():
    password = input("请输入密码：")
    if len(password) < 8:
        ex = Exception("密码长度不够")
        raise ex
    else:
        return password


try:
    input_password()
except Exception as result:
    print(result)