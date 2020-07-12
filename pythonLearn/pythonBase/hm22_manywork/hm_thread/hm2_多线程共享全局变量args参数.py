import threading
import time

num = 0
mutex = threading.Lock()


def increment(args0, args1):
    global num
    for i in range(1000000):
        mutex.acquire()
        num += 1
        mutex.release()


g_yz = ([1, 2, 3], [1, 2])


# 线程在调用target的时候，会将无组拆包，所以接收时用多个参数或者*参数名接收
def main():
    t1 = threading.Thread(target=increment, args=g_yz)
    t2 = threading.Thread(target=increment, args=g_yz)
    t1.start()
    t2.start()

    time.sleep(5)
    print(num)


if __name__ == '__main__':
    main()