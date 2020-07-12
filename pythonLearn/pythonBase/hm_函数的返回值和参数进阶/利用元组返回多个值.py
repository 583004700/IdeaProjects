def measure():
    temp = 37
    wetness = 60
    return temp, wetness


result = measure()
print(result[0], result[1])

temp, wetness = measure()
print(temp, wetness)
