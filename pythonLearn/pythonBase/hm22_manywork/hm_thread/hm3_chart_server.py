import socket
import threading


class TcpServer:
    def __init__(self):
        self.tcp_server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.tcp_server.bind(("192.168.0.151", 1234))
        self.tcp_server.listen(128)
        self.client_socket = None

    def accept(self):
        client_socket, client_address = self.tcp_server.accept()
        self.client_socket = client_socket
        while True:
            recv_data = self.client_socket.recv(1024)
            if recv_data:
                print("接收到来自客户端的数据 %s" % recv_data.decode("utf8"))

    def send(self):
        while True:
            text = input()
            self.client_socket.send(text.encode("utf-8"))

    def start(self):
        t1 = threading.Thread(target=self.accept)
        t2 = threading.Thread(target=self.send)
        t1.start()
        t2.start()


tcp_server = TcpServer()
tcp_server.start()
