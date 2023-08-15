package main

import (
	"fmt"
	"golearn/day011/socket"
	"net"
	"strconv"
	"time"
)

// 收发简单的字符串消息
func main_tcp_server1() {
	go socket.DealSigPipe()
	ip := "127.0.0.1" //ip换成0.0.0.0和空字符串试试
	port := 5656      //改成1023试一下，会报bind: permission denied
	tcpAddr, err := net.ResolveTCPAddr("tcp4", ip+":"+strconv.Itoa(port))
	socket.CheckError(err)
	listener, err := net.ListenTCP("tcp4", tcpAddr)
	socket.CheckError(err)
	fmt.Println("waiting for client connection ......")
	conn, err := listener.Accept()
	socket.CheckError(err)
	fmt.Printf("establish connection to client %s\n", conn.RemoteAddr().String()) //操作系统会随机给客户端分配一个49152~65535上的端口号
	request := make([]byte, 256)                                                  //设定一个最大长度，防止flood attack
	n, err := conn.Read(request)
	socket.CheckError(err)
	fmt.Printf("receive request %s\n", string(request[:n]))
	m, err := conn.Write([]byte("hello " + string(request[:n])))
	socket.CheckError(err)
	fmt.Printf("write response %d bytes\n", m)

	time.Sleep(3 * time.Second)
	m, err = conn.Read(request) //对方关闭连接后，再在connn上调用Read会报错"EOF"
	if err != nil {
		fmt.Println(err)
	} else {
		fmt.Printf("read request %d bytes\n", m)
	}
	m, err = conn.Read(request)
	if err != nil {
		fmt.Println(err)
	} else {
		fmt.Printf("read request %d bytes\n", m)
	}

	m, err = conn.Write([]byte("oops")) //对方关闭连接后，再在connn上调用Write可能会报错"broken pipe"也可能不会，跟tcp缓冲区情况有关
	if err != nil {
		fmt.Println(err)
	} else {
		fmt.Printf("write response %d bytes\n", m)
	}
	m, err = conn.Write([]byte("oops"))
	if err != nil {
		fmt.Println(err)
	} else {
		fmt.Printf("write response %d bytes\n", m)
	}
}

//go run socket/server/tcp_server1.go
