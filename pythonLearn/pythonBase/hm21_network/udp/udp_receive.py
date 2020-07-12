import socket


udp_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
local = ("", 1234)
udp_socket.bind(local)
while(True):
    recv_data = udp_socket.recvfrom(1024)
    print(recv_data)
    print(recv_data[0].decode("utf-8"))

udp_socket.close()