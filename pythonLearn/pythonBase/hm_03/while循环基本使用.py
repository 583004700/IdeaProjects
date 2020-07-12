i = 1
while i <= 5:
    print(i)
    i += 1

result = 0
count = 0
while count <= 100:
    result += count
    count += 1
print(result)

result = 0
count = 0
while count <= 100:
    if count % 2 == 0:
        result += count
    count += 1
print(result)