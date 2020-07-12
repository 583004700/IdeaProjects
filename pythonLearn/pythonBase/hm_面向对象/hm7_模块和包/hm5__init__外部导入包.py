# 直接导入包，在hm_message包中__init__中声明的文件都会被导入
import hm_面向对象.hm7_模块和包.hm_message as hm_message

hm_message.send_message.send("你好")
print(hm_message.receive())
