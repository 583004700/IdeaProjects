import requests

# 指定从某个地址下载requests模块
# pip install -i https://pypi.tuna.tsinghua.edu.cn/simple/ requests

headers = {"User-Agent": "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36"}
kw = {"wd": "1314"}
response = requests.get("http://www.baidu.com", headers=headers, params=kw)
assert response.status_code == 200
print(response.content.decode("utf-8"))
print(response.request.headers)
print(response.request.url)
print("你好{}{}".format("吗", "?"))

# 访问时会证书认证失败,添加verify=False时就可以不验证证书
requests.get("https://www.12306.cn/mormhweb", verify=False, timeout=10)


class Test(object):

    def __enter__(self):
        print("In __enter__()")
        return "test_with"

    def __exit__(self, type, value, trace):
        print("In __exit__()")


def get_example():
    return Test()


with get_example() as example:
    print("example:", example)
    1/0
    print("example:", example)