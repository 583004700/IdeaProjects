class Cat:
    def __init__(self, name, age):
        self.name = name
        self.age = age

    def say(self):
        print(self.name)

tom = Cat("tom", 18)
lazy = Cat("tom", None)
tom2 = tom
print(tom is tom2)
print(tom is lazy)
print(tom == lazy)
print(lazy.age is None)

arr1 = [2,3]
arr2 = [2,3]
print(arr1 == arr2)
print(arr1 is arr2)
print(arr1 is not arr2)