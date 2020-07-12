# 缺省参数必须在参数的末尾
def print_info(name, gender=True):
    gender_text = "男"
    if not gender:
        gender_text = "女"
    return gender_text


print(print_info("张三", gender=False))
