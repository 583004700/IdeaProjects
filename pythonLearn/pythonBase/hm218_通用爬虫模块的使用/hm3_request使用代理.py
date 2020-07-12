import requests

proxies = {
    "http": "http://163.177.153.23:80"
}
headers = {"User-Agent": "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36"}
response = requests.get("http://www.baidu.com", headers=headers, proxies=proxies)
print(response.content.decode("utf-8"))

