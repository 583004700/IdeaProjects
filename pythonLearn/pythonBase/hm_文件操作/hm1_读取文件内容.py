# *-* coding:utf8 *-*
# 增加上面的注释可以读取中文文件名
filename = "中文文件名.txt"
file = open(filename, encoding="utf-8")
text = file.read()

print("-------%s" % text)
print(len(text))
# 读取文件会把文件指针移动到最后，所以再次执行不会读取到数据
text = file.read()
print("-------%s" % text)
print(len(text))
file.close()

print("-"*100)

# 分行读取文件
filename = "b.txt"
file = open(filename)

line = file.readline()
while(line):
    print(line)
    line = file.readline()

file.close()

filename = "readwrite.png"
file = open(filename, "rb")
content = file.read()

filew = open("readwritecopy.png", "wb")
filew.write(content)
file.close()
filew.close()


