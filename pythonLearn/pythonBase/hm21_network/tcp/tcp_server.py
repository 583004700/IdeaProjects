import socket


tcp_server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
tcp_server.bind(("192.168.0.151", 1234))
tcp_server.listen(128)
client_socket, client_address = tcp_server.accept()
while True:
    try:
        recv_data = client_socket.recv(1024)
    except:
        print("异常")
        break
    if recv_data:
        print(recv_data.decode("utf8"))
        client_socket.send(b"hh")

print(client_socket)
print(client_address)