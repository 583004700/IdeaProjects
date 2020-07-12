import os

# 重命名
# os.rename("ar.txt", "a.txt")
# 目录列表
files = os.listdir("./")
print(files)
# 判断是否是文件夹
isdir = os.path.isdir("a.txt")
print(isdir)

