import urllib.request as req


body = req.urlopen("http://www.baidu.com")
text = body.read()
print(text)

url = "https://rpic.douyucdn.cn/live-cover/appCovers/2018/03/20/2622693_20180320000052_small.jpg"
img_body = req.urlopen(url)
img = img_body.read()
f = open("d:/pythontest/douyu/1.jpg", "wb")
f.write(img)
f.close()

# video_url = src = "blob:http://v.qq.com/425d0aed-0dfd-4d43-b834-113c7c89c339"
# video_body = req.urlopen(video_url)
# video = video_body.read()
# f = open("d:/pythontest/douyu/1.mp4", "wb")
# f.write(img)
# f.close()