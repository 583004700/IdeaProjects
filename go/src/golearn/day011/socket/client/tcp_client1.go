package main

import (
	"fmt"
	"golearn/day011/socket"
	"net"
	"strconv"
)

// 收发简单的字符串消息
func main_tcp_client1() {
	ip := "127.0.0.1" //ip换成0.0.0.0和空字符串试试
	port := 5656
	tcpAddr, err := net.ResolveTCPAddr("tcp4", ip+":"+strconv.Itoa(port)) //换成www.baidu.com:80试试
	socket.CheckError(err)
	fmt.Printf("ip %s port %d\n", tcpAddr.IP.String(), tcpAddr.Port)
	conn, err := net.DialTCP("tcp", nil, tcpAddr)
	socket.CheckError(err)
	fmt.Printf("establish connection to server %s\n", conn.RemoteAddr().String())
	n, err := conn.Write([]byte("china"))
	socket.CheckError(err)
	fmt.Printf("write request %d bytes\n", n)
	response := make([]byte, 256)
	_, err = conn.Read(response)
	// response, err := ioutil.ReadAll(conn) //从conn中读取所有内容，直到遇到error(比如连接关闭)或EOF
	socket.CheckError(err)
	fmt.Printf("receive response: %s\n", string(response))
	conn.Close()
	fmt.Println("close connection")
}

//先启动tcp_server，再在另外一个终端启动tcp_client
//go run socket/client/tcp_client1.go
