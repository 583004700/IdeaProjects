# WebSocket编程
## WebSocket协议解读
![avatar](img/websocket.png)  
websocket和http协议的关联：
- 都是应用层协议，都基于tcp传输协议。
- 跟http有良好的兼容性，ws和http的默认端口都是80，wss和https的默认端口都是443。
- websocket在握手阶段采用http发送数据。  

websocket和http协议的差异：
- http是半双工，而websocket通过多路复用实现了全双工。
- http只能由client主动发起数据请求，而websocket还可以由server主动向client推送数据。在需要及时刷新的场景中，http只能靠client高频地轮询，浪费严重。
- http是短连接(也可以实现长连接, HTTP1.1 的连接默认使用长连接)，每次数据请求都得经过三次握手重新建立连接，而websocket是长连接。
- http长连接中每次请求都要带上header，而websocket在传输数据阶段不需要带header。

&#8195;&#8195;WebSocket是HTML5下的产物，能更好的节省服务器资源和带宽，websocket应用场景举例：
- html5多人游戏
- 聊天室
- 协同编辑
- 基于实时位置的应用
- 股票实时报价
- 弹幕
- 视频会议

websocket握手协议:  
**Request Header**
```
Sec-Websocket-Version:13
Upgrade:websocket
Connection:Upgrade
Sec-Websocket-Key:duR0pUQxNgBJsRQKj2Jxsw==
```
**Response Header**
```
Upgrade:websocket
Connection:Upgrade
Sec-Websocket-Accept:a1y2oy1zvgHsVyHMx+hZ1AYrEHI=
```
- Upgrade:websocket和Connection:Upgrade指明使用WebSocket协议。
- Sec-WebSocket-Version 指定Websocket协议版本。
- Sec-WebSocket-Key是一个Base64 encode的值，是浏览器随机生成的。
- 服务端收到Sec-WebSocket-Key后拼接上一个固定的GUID，进行一次SHA-1摘要，再转成Base64编码，得到Sec-WebSocket-Accept返回给客户端。客户端对本地的Sec-WebSocket-Key执行同样的操作跟服务端返回的结果进行对比，如果不一致会返回错误关闭连接。如此操作是为了把websocket header跟http header区分开。
## WebSocket CS架构实现
&#8195;&#8195;首先需要安装gorilla的websocket包。  
```
go get github.com/gorilla/websocket
```
1. 将http升级到WebSocket协议。
```Go
func (u *Upgrader) Upgrade(w http.ResponseWriter, r *http.Request, responseHeader http.Header) (*websocket.Conn, error)
```
2. 客户端发起握手，请求建立连接。
```Go
func (*websocket.Dialer) Dial(urlStr string, requestHeader http.Header) (*websocket.Conn, *http.Response, error)
```
3. 基于connection进行read和write。  

ws_server.go
```Go
package main

import (
	"fmt"
	"golearn/day011/socket"
	"net"
	"net/http"
	"strconv"
	"time"

	"github.com/gorilla/websocket"
)

type WsServer struct {
	listener net.Listener
	addr     string
	upgrade  *websocket.Upgrader
}

func NewWsServer(port int) *WsServer {
	ws := new(WsServer)
	ws.addr = "0.0.0.0:" + strconv.Itoa(port)
	ws.upgrade = &websocket.Upgrader{
		HandshakeTimeout: 5 * time.Second, //握手超时时间
		ReadBufferSize:   2048,            //读缓冲大小
		WriteBufferSize:  1024,            //写缓冲大小
		//请求检查函数，用于统一的链接检查，以防止跨站点请求伪造。如果Origin请求头存在且原始主机不等于请求主机头，则返回false
		CheckOrigin: func(r *http.Request) bool {
			fmt.Printf("request url %s\n", r.URL)
			fmt.Println("handshake request header")
			for key, values := range r.Header {
				fmt.Printf("%s:%s\n", key, values[0])
			}
			return true
		},
		//http错误响应函数
		Error: func(w http.ResponseWriter, r *http.Request, status int, reason error) {},
	}
	return ws
}

//httpHandler必须实现ServeHTTP接口
func (ws *WsServer) ServeHTTP(w http.ResponseWriter, r *http.Request) {
	if r.URL.Path != "/add" {
		fmt.Println("path error")
		http.Error(w, "请求的路径不存在", 222) //把出错的话术写到ResponseWriter里
		return
	}
	conn, err := ws.upgrade.Upgrade(w, r, nil) //将http协议升级到websocket协议
	if err != nil {
		fmt.Printf("upgrade http to websocket error: %v\n", err)
		return
	}
	fmt.Printf("establish conection to client %s\n", conn.RemoteAddr().String())
	go ws.handleConnection(conn)
}

//处理连接里发来的请求数据
func (ws *WsServer) handleConnection(conn *websocket.Conn) {
	defer func() {
		conn.Close()
	}()
	for { //长连接
		conn.SetReadDeadline(time.Now().Add(20 * time.Second))
		var request socket.Request
		if err := conn.ReadJSON(&request); err != nil {
			//判断是不是超时
			if netError, ok := err.(net.Error); ok { //如果ok==true，说明类型断言成功
				if netError.Timeout() {
					fmt.Printf("read message timeout, remote %s\n", conn.RemoteAddr().String())
					return
				}
			}
			//忽略websocket.CloseGoingAway/websocket.CloseNormalClosure这2种closeErr，如果是其他closeErr就打一条错误日志
			if websocket.IsUnexpectedCloseError(err, websocket.CloseGoingAway, websocket.CloseNormalClosure) {
				fmt.Printf("read message from %s error %v\n", conn.RemoteAddr().String(), err)
			}
			return //只要ReadMessage发生错误，就关闭这条连接
		} else {
			response := socket.Response{Sum: request.A + request.B}
			if err = conn.WriteJSON(&response); err != nil {
				fmt.Printf("write response failed: %v", err)
			} else {
				fmt.Printf("write response %d\n", response.Sum)
			}
		}
	}
}

func (ws *WsServer) Start() (err error) {
	ws.listener, err = net.Listen("tcp", ws.addr) //http和websocket都是建立在tcp之上的
	if err != nil {
		fmt.Printf("listen error:%s\n", err)
		return
	}
	err = http.Serve(ws.listener, ws) //开始对外提供http服务。可以接收很多连接请求，其他一个连接处理出错了，也不会影响其他连接
	if err != nil {
		fmt.Printf("http server error: %v\n", err)
		return
	}

	// if err:=http.ListenAndServe(ws.addr, ws);err!=nil{	//Listen和Serve两步合成一步
	// 	fmt.Printf("http server error: %v\n", err)
	// 	return
	// }
	return nil
}

func main() {
	ws := NewWsServer(5657)
	ws.Start()
}
```

ws_client.go
```Go
package main

import (
	"encoding/json"
	"fmt"
	"golearn/day011/socket"
	"io/ioutil"
	"net/http"
	"time"

	"github.com/gorilla/websocket"
)

func main() {
	dialer := &websocket.Dialer{}
	header := http.Header{
		"Cookie": []string{"name=zcy"},
	}
	conn, resp, err := dialer.Dial("ws://localhost:5657/add", header) //Dial:握手阶段，会发送一条http请求。请求一个不存在的路径试试看
	defer resp.Body.Close()
	if err != nil {
		fmt.Printf("dial server error:%v\n", err)
		fmt.Println(resp.StatusCode)
		msg, _ := ioutil.ReadAll(resp.Body)
		fmt.Println(string(msg))
		return
	}
	fmt.Println("handshake response header")
	for key, values := range resp.Header {
		fmt.Printf("%s:%s\n", key, values[0])
	}
	// time.Sleep(5 * time.Second)
	defer conn.Close()
	for i := 0; i < 10; i++ {
		request := socket.Request{A: 7, B: 4}
		requestBytes, _ := json.Marshal(request)
		err = conn.WriteJSON(request) //websocket.Conn直接提供发json序列化和反序列化方法
		socket.CheckError(err)
		fmt.Printf("write request %s\n", string(requestBytes))
		var response socket.Response
		err = conn.ReadJSON(&response)
		socket.CheckError(err)
		fmt.Printf("receive response: %d\n", response.Sum)
		time.Sleep(1 * time.Second)
	}
	time.Sleep(30 * time.Second)
}
```
&#8195;&#8195;websocket发送的消息类型有5种：TextMessag,BinaryMessage, CloseMessag,PingMessage,PongMessage。TextMessag和BinaryMessage分别表示发送文本消息和二进制消息。CloseMessage关闭帧，接收方收到这个消息就关闭连接
PingMessage和PongMessage是保持心跳的帧，发送方接收方是PingMessage，接收方发送方是PongMessage，目前浏览器没有相关api发送ping给服务器，只能由服务器发ping给浏览器，浏览器返回pong消息。  
## 聊于室实现
&#8195;&#8195;gorilla的websocket项目中有一个聊天室的demo，此处讲一下它的设计思路。[我们的代码](../../socket/chat_room)基于[原代码](https://github.com/gorilla/websocket/tree/master/examples/chat)进行了简化和修改，并加上中文注释。总体架构如下图所示  
![avatar](img/chat.png) 
Hub  
- Hub持有每一个Client的指针，broadcast管道里有数据时把它写入每一个Client的send管道中。
- 注销Client时关闭Client的send管道。  

Client
- 前端(browser)请求建立websocket连接时，为这条websocket连接专门启一个协程，创建一个client。
- client把前端发过来的数据写入hub的broadcast管道。
- client把自身send管道里的数据写给前端。
- client跟前端的连接断开时请求从hub那儿注销自己。  

Front
- 当打开浏览器页面时，前端会请求建立websocket连接。
- 关闭浏览器页面时会主动关闭websocket连接。  

存活监测
- 当hub发现client的send管道写不进数据时，把client注销掉。
- client给websocket连接设置一个读超时，并周期性地给前端发ping消息，如果没有收到pong消息则下一次的conn.read()会报出超时错误，此时client关闭websocket连接。  