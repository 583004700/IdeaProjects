import socket
import time


def main():
    socket_client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    socket_client.connect(("192.168.0.151", 1234))
    socket_client.send(b"wq")
    while True:
        print("客户端发送")
        socket_client.send(b"wq")
        recv_data = socket_client.recv(1024)
        print(recv_data.decode("utf8"))
        time.sleep(5)

    socket_client.close()


if __name__ == '__main__':
    main()