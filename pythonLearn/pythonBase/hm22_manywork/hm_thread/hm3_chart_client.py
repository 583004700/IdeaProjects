import socket
import threading


class TcpClient:
    def __init__(self):
        self.socket_client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.socket_client.connect(("192.168.0.151", 1234))

    def send(self):
        while True:
            text = input()
            self.socket_client.send(text.encode("utf-8"))

    def receive(self):
        while True:
            recv_data = self.socket_client.recv(1024)
            if recv_data:
                print("接收到来自服务端的数据 %s" % recv_data.decode("utf8"))

    def start(self):
        t1 = threading.Thread(target=self.receive)
        t2 = threading.Thread(target=self.send)
        t1.start()
        t2.start()


tcp_client = TcpClient()
tcp_client.start()
