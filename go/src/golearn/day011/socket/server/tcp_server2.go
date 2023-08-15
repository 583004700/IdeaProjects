package main

import (
	"encoding/json"
	"fmt"
	"golearn/day011/socket"
	"net"
	"strconv"
)

// 序列化请求和响应的结构体
func main_tcp_server2() {
	ip := "127.0.0.1" //ip换成0.0.0.0和空字符串试试
	port := 5656
	tcpAddr, err := net.ResolveTCPAddr("tcp4", ip+":"+strconv.Itoa(port))
	socket.CheckError(err)
	listener, err := net.ListenTCP("tcp4", tcpAddr)
	socket.CheckError(err)
	fmt.Println("waiting for client connection ......")
	conn, err := listener.Accept()
	socket.CheckError(err)
	fmt.Printf("establish connection to client %s\n", conn.RemoteAddr().String()) //操作系统会随机给客户端分配一个49152~65535上的端口号
	requestBytes := make([]byte, 256)                                             //初始化后byte数组每个元素都是0
	read_len, err := conn.Read(requestBytes)
	socket.CheckError(err)
	fmt.Printf("receive request %s\n", string(requestBytes)) //[]byte转string时，0后面的会自动被截掉

	var request socket.Request
	json.Unmarshal(requestBytes[:read_len], &request) //json反序列化时会把0都考虑在内，所以需要指定只读前read_len个字节
	response := socket.Response{Sum: request.A + request.B}

	responseBytes, _ := json.Marshal(response)
	_, err = conn.Write(responseBytes)
	socket.CheckError(err)
	fmt.Printf("write response %s\n", string(responseBytes))
	conn.Close()
}

//go run socket/server/tcp_server2.go
