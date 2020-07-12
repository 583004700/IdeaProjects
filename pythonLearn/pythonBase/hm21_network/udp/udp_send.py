import socket


def main():
    udp_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    # 也可以绑定端口，如果不绑定端口，则分配一个随机端口
    udp_socket.bind(("", 2345))
    while True:
        text = input("请输入内容：")
        udp_socket.sendto(text.encode("utf-8"), ("192.168.0.151", 1234))

    print(bytes([0xE4, 0xBD, 0xA0, 0xE5, 0xA5, 0xBD]).decode("utf-8"))
    udp_socket.close()


if __name__ == '__main__':
    main()