import socket


udp_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
local = ("", 1234)
udp_socket.bind(local)
while(True):
    recv_data = udp_socket.recvfrom(1024)
    print(recv_data)
    print(recv_data[0].decode("utf-8"))
    udp_socket.sendto("你好，已收到".encode("utf-8"), recv_data[1])


udp_socket.close()