xiaoming = {"name": "小明", "gender": True, "age": 18}

print(xiaoming["name"])
xiaoming["width"] = 10
xiaoming["age"] = 19
print(xiaoming)
xiaoming.pop("gender")
print(xiaoming)
print(len(xiaoming))
temp_dict = {"height":1.75}
xiaoming.update(temp_dict)
print(xiaoming)
# xiaoming.clear()
# print(xiaoming)
for k in xiaoming:
    print(xiaoming[k])

card_list = [{"name":"张三"},{}]
for c in card_list:
    print(c)