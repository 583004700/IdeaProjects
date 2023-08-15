package main

import (
	"fmt"
	"golearn/day011/socket"
	"net"
	"time"
)

// UDP是无连接的。UDP是面向报文的
func main_udp_server2() {
	udpAddr, err := net.ResolveUDPAddr("udp", ":5657")
	socket.CheckError(err)
	conn, err := net.ListenUDP("udp", udpAddr) //跟TCP不一样，即使没有client请求建立连接，该行代码也不会阻塞。它返回的conn是个虚拟连接
	socket.CheckError(err)
	defer conn.Close()
	fmt.Println("build connect")

	time.Sleep(3 * time.Second) //3秒后，对方已关闭连接。但UDP是无连接的，所以这边在conn上调用Read和Write都不会报错
	fmt.Println("awake")
	//接收缓冲区有2条报文可读
	content := make([]byte, 256)
	n, remoteAddr, err := conn.ReadFromUDP(content) //UDP是面向报文的，一次Read只读一个报文
	socket.CheckError(err)
	fmt.Println(string(content[:n]))

	content = make([]byte, 4)
	n, remoteAddr, err = conn.ReadFromUDP(content) //如果没有把一个报文读完，后面的内容会被丢弃掉，下次就读不到了
	socket.CheckError(err)
	fmt.Println(string(content[:n]))

	content = make([]byte, 4)
	n, remoteAddr, err = conn.ReadFromUDP(content) //如果没有把一个报文读完，后面的内容会被丢弃掉，下次就读不到了
	socket.CheckError(err)
	fmt.Println(string(content[:n]))

	n, err = conn.WriteToUDP([]byte("oops"), remoteAddr) //不会报错
	if err != nil {
		fmt.Println(err)
	} else {
		fmt.Printf("write response %d bytes\n", n)
	}
	n, err = conn.WriteToUDP([]byte("oops"), remoteAddr) //不会报错
	if err != nil {
		fmt.Println(err)
	} else {
		fmt.Printf("write response %d bytes\n", n)
	}

	n, remoteAddr, err = conn.ReadFromUDP(content) //接收缓冲区已无报文可读，该行代码会阻塞
	if err != nil {
		fmt.Println(err)
	} else {
		fmt.Printf("read request %d bytes\n", n)
	}
}
