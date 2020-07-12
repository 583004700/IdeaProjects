import re


r = re.match(r"hello", "hello world")
print(r)

# \d匹配数字 0-9
r = re.match(r"速度与激情\d", "速度与激情5")
# 提取匹配到的数据结果
print(r.group())

r = re.match(r"速度与激情[1-5]", "速度与激情2")
print(r.group())

r = re.match(r"速度与激情[1-57abcdA-Z]*", "速度与激情Mb000")
print(r.group())

r = re.match(r"速度与激情[\w好]", "速度与激情好_b000")
print(r.group())
# {1,3}修饰前面的\d出现1至3次
r = re.match(r"速度与激情\d{1,3}", "速度与激情12484")
print(r.group())
# 表示-出现0次或1次
r = re.match(r"021-?\d{8}", "021-12345678")
print(r.group())

r = re.match(r"(.)*", "5mf\nosfs", re.S)
print(r.group())
# group(1) 取出第一个小括号里面的内容
print("------%s" % r.group(1))

# \.转义为.，|符号表示或者
r = re.match(r"[A-Za-z0-9_]{4,20}@(163|189)\.com$", "58694@163.com")
print(r.group())

# match会从头匹配，而search不会从头开始匹配
r = re.search(r"\d+", "文章的阅读次数9999,点赞100")
print(r.group())

r = re.findall(r"\d+", "文章的阅读次数9999,点赞100")
print(r)