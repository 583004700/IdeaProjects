from distutils.core import setup

setup(name="hm_message",
      version="1.0",
      description="发送和接收消息模块",
      long_description="完整的发送和接收消息模块",
      author="zhuwb",
      author_email="583004700@qq.com",
      url="www.baidu.com",
      py_modules=["hm_message.send_message", "hm_message.receive"])

# 1生成压缩包
# 执行 python setup.py build
# 执行 python setup.py sdist将hm_message包制作成tar.gz包
# 2安装使用模块
# tar -zxvf hm_message-1.0.tar.gz
# sudo python setup.py install
# 3缷载模块
# import hm_message __file__查看模块路径，sudo rm -rf hm_message*删除这个模块
