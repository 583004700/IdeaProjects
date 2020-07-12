i = 1
while i <= 10:
    print(i)
    if i % 7 == 0:
        break
    i += 1
print("over")


i = 0
while i <= 10:
    i += 1
    if i == 3:
        continue
    print(i)


i = 1
while i < 10:
    k = 1
    while k <= i:
        print(str(k)+"*"+str(i)+"="+str(i*k)+"  ", end="")
        k += 1
    print("")
    i += 1
