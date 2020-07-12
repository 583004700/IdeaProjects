import time
import threading


def task1():
    while True:
        print("1")
        time.sleep(2)
        yield


def task2():
    while True:
        print("2")
        time.sleep(2)
        yield


t1 = task1()
t2 = task2()

while True:
    next(t1)
    next(t2)
    print(threading.enumerate())
