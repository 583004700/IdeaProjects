import time
import multiprocessing


# 多进程之间不共享全局变量
count = 1

# 多进程之间可以通过此队列通信
q = multiprocessing.Queue(100)


def sing(qu):
    for i in range(5):
        print("唱歌")
        global count
        count += 1
        qu.put(count)
        print(qu)
        print(count)
        time.sleep(1)


def dance(qu):
    for i in range(5):
        print("跳舞")
        global count
        count += 1
        qu.put(count)
        print(qu)
        print(count)
        time.sleep(1)


def main():
    t1 = multiprocessing.Process(target=sing, args=(q,))
    t2 = multiprocessing.Process(target=dance, args=(q,))
    t1.start()
    t2.start()
    # time.sleep(8)
    # print("count:%s" % count)
    # for i in range(10):
    #     print(q.get())


if __name__ == '__main__':
    main()

    # while True:
    #     print(threading.enumerate())
    #     time.sleep(1)