package main

import (
	"fmt"
	"golearn/day011/socket"
	"net"
	"time"
)

func main_tcp_client5() {
	tcpAddr, err := net.ResolveTCPAddr("tcp4", ":5657")
	socket.CheckError(err)
	conn, err := net.DialTCP("tcp", nil, tcpAddr)
	socket.CheckError(err)
	defer conn.Close() //连接关闭后，再调用conn.Write()和conn.Read()会返回fatal error
	fmt.Printf("connect to server %s\n", conn.RemoteAddr().String())

	time.Sleep(5 * time.Second)

	//连续写2次
	_, err = conn.Write([]byte("hello|"))
	socket.CheckError(err)

	_, err = conn.Write([]byte("world|"))
	socket.CheckError(err)
}
