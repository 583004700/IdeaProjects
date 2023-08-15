# http编程
## http协议
- http：超文本传输协议Hyper Text Transfer Protocol。
- http属于应用层协议，它在传输层用的是tcp协议。
- 无状态，对事务处理没有记忆能力（对比TCP协议里的确认号）。如果要保存状态需要引用其他技术，如cookie。
- 无连接，每次连接只处理一个请求。早期带宽和计算资源有限，这么做是为了追求传输速度快，后来通过Connection: Keep-Alive实现长连接。http1.1废弃了Keep-Alive，默认支持长连接。

<img src=img/request.png width=700 />  

### 请求方法
|请求方法|解释|
| :---: | :--- |
|GET|请求获取Request-URI所标识的资源|
|POST|向URI提交数据（例如提交表单或上传数据）|
|HEAD|类似于GET，返回的响应中没有具体的内容，用于获取报头|
|PUT|对服务器上已存在的资源进行更新|
|DELETE|请求服务器删除指定的页面|
|CONNECT|HTTP/1.1预留，能够将连接改为管道方式的代理服务器|
|OPTIONS|查看服务端性能|
|TRACE|回显服务器收到的请求，主要用于测试或诊断|
|PATCH|同PUT，可只对资源的一部分更新，资源不存在时会创建|  

&#8195;&#8195;GET、POST和HEAD是http1.0就有的，后面的请求方法是http1.1新增的。客户端发起一个请求时，这个请求可能要穿过防火墙、代理、网关或其他一些应用程序。每个中间节点都可能会修改原始的HTTP请求。TRACE 方法允许客户端在 最终将请求发送给服务器时，看看它变成了什么样子。TRACE请求会在目的服务器端发起一个环回诊断。行程最后一站的服务器会弹回一条TRACE响应，并在响应主体中携带它收到的原始请求报文。这样客户端就可以查看在所有中间HTTP应用程序组成的请求/响应链上，原始报文是否，以及如何被毁坏或修改过。CONNECT方法是HTTP/1.1协议预留的，能够将连接改为管道方式的代理服务器。通常用于SSL加密服务器的链接与非加密的HTTP代理服务器的通信。OPTIONS方法请求Web服务器告知其支持的各种功能。通过使用OPTIONS，客户端可以在与服务器进行交互之前，确定服务器的能力，这样它就可以更方便地与具备不同特性的代理和服务器进行互操作了。  
&#8195;&#8195;实际中server对各种request method的处理方式可能不是按协义标准来的，比如server收到PUT请求时偏偏执行DELETE操作，同理仅用一个GET方法也能实现增删改查的全部功能。大多数浏览器只支持GET和POST。  

### URL
- URI：uniform resource identifier，统一资源标识符，用来唯一的标识一个资源。
- URL： uniform resource locator，统一资源定位器，它是一种具体的URI，指明了如何locate这个资源。
- URL举例：

<img src=img/url.png width=700 />

### 协议版本
&#8195;&#8195;现在广泛应用的协议版本是HTTP/1.1。  

### 请求头
|Header|解释|示例|
| :---: | :--- | :--- |
|Accept|指定客户端能够接收的内容类型|Accept: text/plain, text/html|
|Accept-Charset|浏览器可以接受的字符编码集|Accept-Charset: iso-8859-5|
|Accept-Encoding|指定浏览器可以支持的web服务器返回内容压缩编码类型|Accept-Encoding: compress, gzip|
|Accept-Language|浏览器可接受的语言|Accept-Language: en,zh|
|Authorization|HTTP授权的授权证书|Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==|
|Cache-Control|指定请求和响应遵循的缓存机制|Cache-Control: no-cache|
|Connection|表示是否需要持久连接（HTTP 1.1默认进行持久连接）|Connection: close|
|Cookie|HTTP请求发送时，会把保存在该请求域名下的所有cookie值一起发送给web服务器|Cookie: $Version=1; Skin=new;|
|Content-Length|请求的内容长度|Content-Length: 348|
|Content-Type|指定正文(body)的数据格式|Content-Type: application/x-www-form-urlencoded|
|User-Agent|浏览器信息|Mozilla/5.0 (Windows NT 6.1; Win64; x64)|  

Content-Type  

- application/x-www-form-urlencoded
    - 浏览器的原生form表单，如果不设置 Content-Type 属性，则默认以 application/x-www-form-urlencoded 方式传输数据
    - 正文例如：name=manu&message=this_is_great
- multipart/form-data
    - 上传文件时使用multipart/form-data，支持多种文件格式
    - 正文例如： name="text"name="file"; filename="chrome.png"Content-Type: image/png... content of chrome.png
- application/json
    - 正文例如：{"title":"test","sub":[1,2,3]}
- text/xml
    - 正文例如：<?xml version="1.0"?><methodCall>    <methodName>examples.getStateName</methodName>    </methodCall>
### 请求正文
&#8195;&#8195;GET请求没有请求正文。POST即可以把一部分参数放在url里，也可以把一部分参数放在请求正文里，如下是一个完整的POST请求
```
POST /post?id=1234&page=1 HTTP/1.1
Content-Type: application/x-www-form-urlencoded

name=manu&message=this_is_great
```
GET和POST的区别：
1. get的请求参数全部在url里，参数变时url就变；post可以把参数放到请求正文里，参数变时url不变。
2. 虽然http协议并没有对url和请求正文做长度限制，但在实际中浏览器对url的长度限制比请求正文要小很多，所以post可以提交的数据比get要大得多。
3. get比post更容易受到攻击（源于get的参数直接暴露在url里）。  

### http response

<img src=img/response.png width=550 />

响应状态及话术  
|code|phrase|说明|
|:---:|:---|:---|
|200|Ok|请求成功|
|400|Bad Request|客户端有语法错误，服务端不理解|
|401|Unauthorized|请求未经授权|
|403|Forbidden|服务端拒绝提供服务|
|404|Not Found|请求资源不存在|
|500|Internal Server Error|服务器发生不可预期的错误|
|503|Server Unavailable|服务器当前有问题，过段时间可能恢复|

响应头  
|Header|解释|示例|
|:---:|:---|:---|
|Allow|对某网络资源的有效的请求行为|Allow: GET, HEAD|
|Date|原始服务器消息发出的时间|Date: Tue, 15 Nov 2010 08:12:31 GMT|
|Content-Encoding|服务器支持的返回内容压缩编码类型|Content-Encoding: gzip|
|Content-Language|响应体的语言|Content-Language: en,zh|
|Content-Length|响应体的长度|Content-Length: 348|
|Cache-Control|指定请求和响应遵循的缓存机制|Cache-Control: no-cache|
|Content-Type|返回内容的MIME类型|Content-Type: text/html; charset=utf-8|  

&#8195;&#8195;响应正文可以是html、json、xml、普通文本，等等。  
完整http response举例：
```
HTTP/1.1 200 OK 
Date: Fri, 22 May 2009 06:07:21 GMT 
Content-Type: text/html; charset=UTF-8 

<html> 
	<head></head>
	 <body>
		 <!--body goes here--> 
	</body> 
</html>
```
### https

<img src=img/https.png width=400 />

HTTP + 加密 + 认证 + 完整性保护 = HTTPS（HTTP Secure）  

## go语言http标准库
http_server.go  
```Go
import ("net/http";"fmt")
func HelloHandler(w http.ResponseWriter, r *http.Request) {
	fmt.Fprint(w, "Hello Boy") //把返回的内容写入http.ResponseWriter
}
func main() {
	http.HandleFunc("/", HelloHandler) //路由，请求要目录时去执行HelloHandler  
	//ListenAndServe如果不发生error会一直阻塞。为每一个请求创建一个协程去处理
	http.ListenAndServe(":5656", nil)
}
```
http_client.go
```Go
import ("net/http";"io";"os")
func main(){
	if resp, err := http.Get("http://127.0.0.1:5656"); err != nil {
		panic(err)
	} else {
	    //注意一定要调用resp.Body.Close()，否则会协程泄漏（同时引发内存泄漏）
		defer resp.Body.Close() 
		io.Copy(os.Stdout, resp.Body) //把resp.Body输出到标准输出流
	}
}
```
## http router
- 安装 go get -u github.com/julienschmidt/httprouter。
- Router实现了http.Handler接口。
- 为各种request method提供了便捷的路由方式。
- 支持restful请求方式。
- 支持ServeFiles访问静态文件。
- 可以自定义捕获panic的方法。
```Go
package main

import (
	"fmt"
	"io"
	"net/http"
	"os"

	"github.com/julienschmidt/httprouter"
)

func handle(method string, w http.ResponseWriter, r *http.Request, params httprouter.Params) {
	fmt.Printf("request method: %s\n", r.Method)
	fmt.Printf("request body: ")
	io.Copy(os.Stdout, r.Body) //把r.Body流里的内容拷贝到os.Stdout流里
	fmt.Println()
	w.Write([]byte("Hi boy, you request " + method))
}

func get(w http.ResponseWriter, r *http.Request, params httprouter.Params) {
	handle("get", w, r, params)
}

func post(w http.ResponseWriter, r *http.Request, params httprouter.Params) {
	handle("post", w, r, params)
}

func panic(w http.ResponseWriter, r *http.Request, params httprouter.Params) {
	var arr []int
	_ = arr[1] //数组越界panic
}

func main() {
	router := httprouter.New()
	router.GET("/", get)
	router.POST("/", post)

	//*只能有一个，且必须放path的末尾。catch-all routes are only allowed at the end of the path
	router.POST("/user/:name/:type/*addr", func(rw http.ResponseWriter, r *http.Request, p httprouter.Params) {
		fmt.Printf("name:%s, type:%s, addr:%s\n", p.ByName("name"), p.ByName("type"), p.ByName("addr"))
	})

	//必须以/*filepath结尾，因为要获取我们要访问的路径信息
	//在浏览器中访问：http://127.0.0.1:5656/file/home.html
	//或 http://127.0.0.1:5656/file/readme.md
	router.ServeFiles("/file/*filepath", http.Dir("./http/static"))

	//通过recover捕获panic
	router.PanicHandler = func(w http.ResponseWriter, r *http.Request, err interface{}) {
		w.WriteHeader(http.StatusInternalServerError) //设置response status
		fmt.Fprintf(w, "error:%s", err)               //线上环境不要把原始错误信息返回给前端。测试阶段可以这么搞
	}
	router.GET("/panic", panic)

	//Router实现了ServerHTTP接口，所以它是一种http.Handler
	http.ListenAndServe(":5656", router)
}
```
## 请求校验
&#8195;&#8195;跨站脚本攻击Cross-Site Scripting， XSS(为了区别于CSS)。通过注入脚本获取敏感信息。  
```html
<html>
<head>
	<title>留言板</title>
</head>
<body>
	<div id="board">
		<!--从数据库中读出用户的留言内容，展示在这里-->
		<script>alert("hey!you are attacked")</script>
	</div> 
</body>
</html>
```
CSRF(Cross-site request forgery, 跨站请求伪造)
1. 登录A网站(银行网站)的个人中心
    www.bank.com/my.php
2. 登录危险的B网站
    <img src=http://www.bank.com/Transfer.php?toBankId=11&money=1000>
    从B网站向A网站发起了转账请求(携带着A网站的认证Cookie)。  

jsonp  
- 主流浏览器不允许跨域访问数据（端口不同也属于跨域）。
- < script >标签的src属性不受同源策略限制。
- 通过script的src请求返回的数据，浏览器会当成js脚本去处理。所以服务端可以返回一个在客户端存在的js函数。
&#8195;&#8195;首先安装go get github.com/go-playground/validator。
```Go
type RegistRequest struct {
    UserName string `validate:"gt=0"` // >0 长度大于0
    PassWord string `validate:"min=6,max=12"` //密码长度[6, 12]
    PassRepeat string `validate:"eqfield=PassWord"` //跨字段相等校验
    Email string `validate:"email"` //需要满足email的格式
}
```

范围约束  
- 对于字符串、切片、数组和map，约束其长度。len=10, min=6, max=10, gt=10。
- 对于数值，约束其取值。min, max, eq, ne, gt, gte, lt, lte, oneof=6 8。

跨字段约束  
- 跨字段就在范围约束的基础上加field后缀。
- 如果还跨结构体(cross struct)就在跨字段的基础上在field前面加cs：范围约束 cs field。

字符串约束  
- contains包含子串。
- containsany包含任意unicode字符， containsany=abcd。
- containsrune包含rune字符， containsrune= ☻。
- excludes不包含子串。
- excludesall不包含任意的unicode字符，excludesall=abcd。
- excludesrune不包含rune字符，excludesrune=☻。
- startswith以子串为前缀。
- endswith以子串为后缀。

唯一性uniq  

- 对于数组和切片，约束没有重复的元素。
- 对于map，约束没的重复的value。
- 对于元素类型为结构体的切片，unique约束结构体对象的某个字段不重复，通过unqiue=field指定这个字段名。
    Friends []User `validate:"unique=Name"`

自定义约束  
```Go
func validateEmail(fl validator.FieldLevel) bool {
    input := fl.Field().String()
    if pass, _ := regexp.MatchString(`^([\w\.\_]{2,10})@(\w{1,})\.([a-z]{2,4})$`, input); pass {
	  return true
 }
 return false
}

//注册一个自定义的validator
val.RegisterValidation("my_email", validateEmail)

Email string `validate:"my_email"`
```
## http中间件
&#8195;&#8195;中间件的作用：将业务代码和非业务代码解耦。非业务代码指限流、超时控制、打日志等等。  
&#8195;&#8195;中间件的实现原理：传入一个http.Handler，外面套上一些非业务功能代码，再返回一个http.Handler。支持中间件层层嵌套。通过HandlerFunc把一个func(rw http.ResponseWriter, r *http.Request)函数转为Handler。
```Go
func timeMiddleWare(next http.Handler) http.Handler {
    return http.HandlerFunc(func(rw http.ResponseWriter, r     *http.Request) {
        begin := time.Now()
        next.ServeHTTP(rw, r)
        timeElapsed := time.Since(begin)
        log.Printf("request %s use %d ms\n", r.URL.Path, timeElapsed.Milliseconds())
    })
}
```
## GIN
&#8195;&#8195;Gin是一款高性能的、简单轻巧的http Web框架。安装方式go get -u github.com/gin-gonic/gin。
### 路由
&#8195;&#8195;Gin的路由是基于httprouter做的，支持GET、POST、PUT、PATCH、DELETE、OPTIONS、HEAD。支持路由分组，不用重复写上级路径。  
```Go
package main

import (
	"net/http"

	"github.com/gin-gonic/gin"
)

func boy(c *gin.Context) { //你所需要的东西全都封装在了gin.Context里面，包括http.Request和ResponseWriter
	c.String(http.StatusOK, "hi boy") //通过gin.Context.String返回一个text/plain类型的正文
}

func girl(c *gin.Context) {
	c.String(http.StatusOK, "hi girl")
}

func main() {
	// gin.SetMode(gin.ReleaseMode)        //发布模式，默认是Debug模式
	engine := gin.Default() //默认的engine已自带了Logger和Recovery两个中间件
	engine.GET("/", boy)
	engine.POST("/", girl)

	//路由分组
	oldVersion := engine.Group("/v1")
	oldVersion.GET("/student", boy) //http://localhost:5656/v1/student
	oldVersion.GET("/teacher", boy) //http://localhost:5656/v1/teacher

	newVersion := engine.Group("/v2")
	newVersion.GET("/student", girl) //http://localhost:5656/v2/student
	newVersion.GET("/teacher", girl) //http://localhost:5656/v2/teacher

	engine.Run(":5656")
}
```
### 参数获取
- c.Query() 从GET请求的URL中获取参数。
- c.Param()从Restful风格的url中获取参数。
- c.PostForm() 从post表单中获取参数。
- c.FormFile() 获取上传的文件，消息类型为form-data。
- c. MultipartForm() multipart/form-data可以上传多个form-data 并且用分隔符进行分割。
```Go
//从GET请求的URL中获取参数
func url(engine *gin.Engine) {
	engine.GET("/student", func(ctx *gin.Context) {
		name := ctx.Query("name")
		addr := ctx.DefaultQuery("addr", "China") //如果没传addr参数，则默认为China
		ctx.String(http.StatusOK, name+" live in "+addr)
	})
}

//从Restful风格的url中获取参数
func restful(engine *gin.Engine) {
	engine.GET("/student/:name/*addr", func(ctx *gin.Context) {
		name := ctx.Param("name")
		addr := ctx.Param("addr")
		ctx.String(http.StatusOK, name+" live in "+addr)
	})
}

//从post表单中获取参数
func post(engine *gin.Engine) {
	engine.POST("/student", func(ctx *gin.Context) {
		name := ctx.PostForm("name")
		addr := ctx.DefaultPostForm("addr", "China") //如果没传addr参数，则默认为China
		ctx.String(http.StatusOK, name+" live in "+addr)
	})
}

//上传单个文件
func upload_file(engine *gin.Engine) {
	//限制表单上传大小为8M，默认上限是32M
	engine.MaxMultipartMemory = 8 << 20
	engine.POST("/upload", func(ctx *gin.Context) {
		file, err := ctx.FormFile("file")
		if err != nil {
			fmt.Printf("get file error %v\n", err)
			ctx.String(http.StatusInternalServerError, "upload file failed")
		} else {
			ctx.SaveUploadedFile(file, "./data/"+file.Filename) //把用户上传的文件存到data目录下
			ctx.String(http.StatusOK, file.Filename)
		}
	})
}

//上传多个文件
func upload_multi_file(engine *gin.Engine) {
	engine.POST("/upload_files", func(ctx *gin.Context) {
		form, err := ctx.MultipartForm() //MultipartForm中不止包含多个文件
		if err != nil {
			ctx.String(http.StatusBadRequest, err.Error())
		} else {
			//从MultipartForm中获取上传的文件
			files := form.File["files"]
			for _, file := range files {
				ctx.SaveUploadedFile(file, "./data/"+file.Filename) //把用户上传的文件存到data目录下

			}
			ctx.String(http.StatusOK, "upload "+strconv.Itoa(len(files))+" files")
		}
	})
}
```
&#8195;&#8195;还可以定义struct，绑定到参数。  
```Go
type Student struct {
	Name string `form:"username" json:"name" uri:"user" xml:"user" yaml:"user" binding:"required"`
	Addr string `form:"addr" json:"addr" uri:"addr" xml:"addr" yaml:"addr" binding:"required"`
}

func formBind(engine *gin.Engine) {
	engine.POST("/stu/form", func(ctx *gin.Context) {
		var stu Student
		//跟ShouldBind对应的是MustBind。MustBind内部会调用ShouldBind，如果ShouldBind发生error会直接c.AbortWithError(http.StatusBadRequest, err)
		if err := ctx.ShouldBind(&stu); err != nil {
			fmt.Println(err)
			ctx.String(http.StatusBadRequest, "parse paramter failed")
		} else {
			ctx.String(http.StatusOK, stu.Name+" live in "+stu.Addr)
		}
	})
}

func jsonBind(engine *gin.Engine) {
	engine.POST("/stu/json", func(ctx *gin.Context) {
		var stu Student
		if err := ctx.ShouldBindJSON(&stu); err != nil {
			fmt.Println(err)
			ctx.String(http.StatusBadRequest, "parse paramter failed")
		} else {
			ctx.String(http.StatusOK, stu.Name+" live in "+stu.Addr)
		}
	})
}

func uriBind(engine *gin.Engine) {
	//GET请求的参数在uri里
	engine.GET("/stu/uri/:user/:addr", func(ctx *gin.Context) {
		fmt.Println(ctx.Request.URL)
		var stu Student
		if err := ctx.ShouldBindUri(&stu); err != nil {
			fmt.Println(err)
			ctx.String(http.StatusBadRequest, "parse paramter failed")
		} else {
			ctx.String(http.StatusOK, stu.Name+" live in "+stu.Addr)
		}
	})
}

func xmlBind(engine *gin.Engine) {
	engine.POST("/stu/xml", func(ctx *gin.Context) {
		var stu Student
		if err := ctx.ShouldBindXML(&stu); err != nil {
			fmt.Println(err)
			ctx.String(http.StatusBadRequest, "parse paramter failed")
		} else {
			ctx.String(http.StatusOK, stu.Name+" live in "+stu.Addr)
		}
	})
}

func yamlBind(engine *gin.Engine) {
	engine.POST("/stu/yaml", func(ctx *gin.Context) {
		var stu Student
		if err := ctx.ShouldBindYAML(&stu); err != nil {
			fmt.Println(err)
			ctx.String(http.StatusBadRequest, "parse paramter failed")
		} else {
			ctx.String(http.StatusOK, stu.Name+" live in "+stu.Addr)
		}
	})
}
```
### 利用postman提交http请求
提交普通post请求  
![avatar](img/post.png)  

上传文件  
![avatar](img/file.png)  

提交json  
![avatar](img/json.png)  

提交xml  
![avatar](img/xml.png) 

提交yaml  
![avatar](img/yaml.png) 

### 生成response
- c.String() response Content-Type=text/plain。
- c.JSON() response Content-Type= application/json。
- c.XML() response Content-Type= application/xml。
- c.HTML() 前端写好模板，后端往里面填值。
- c.Redirect() 重定向。
```Go
func text(engine *gin.Engine) {
	engine.GET("/user/text", func(c *gin.Context) {
		c.String(http.StatusOK, "hi boy") //response Content-Type:text/plain
	})
}

func json1(engine *gin.Engine) {
	engine.GET("/user/json1", func(c *gin.Context) {
		c.JSON(http.StatusOK, gin.H{"name": "zcy", "addr": "bj"}) //response Content-Type:application/json
	})
}

func json2(engine *gin.Engine) {
	var stu struct { //匿名结构体
		Name string
		Addr string
	}
	stu.Name = "zcy"
	stu.Addr = "bj"
	engine.GET("/user/json2", func(c *gin.Context) {
		c.JSON(http.StatusOK, stu) //response Content-Type:application/json
	})
}

func jsonp(engine *gin.Engine) {
	// type Stident struct {
	// 	Name string
	// 	Addr string
	// }

	// var stu Student

	var stu struct {
		Name string
		Addr string
	}
	stu.Name = "zcy"
	stu.Addr = "bj"
	engine.GET("/user/jsonp", func(ctx *gin.Context) {
		//如果请求参数里有callback=xxx，则response Content-Type为application/javascript，否则response Content-Type为application/json
		ctx.JSONP(http.StatusOK, stu)
	})
}

func xml(engine *gin.Engine) {
	var stu struct {
		Name string
		Addr string
	}
	stu.Name = "zcy"
	stu.Addr = "bj"
	engine.GET("/user/xml", func(c *gin.Context) {
		c.XML(http.StatusOK, gin.H{"name": "zcy", "addr": "bj"}) //response Content-Type:application/xml
	})
}

func yaml(engine *gin.Engine) {
	var stu struct {
		Name string
		Addr string
	}
	stu.Name = "zcy"
	stu.Addr = "bj"
	engine.GET("/user/yaml", func(c *gin.Context) {
		c.YAML(http.StatusOK, stu)
	})
}

func html(engine *gin.Engine) {
	engine.LoadHTMLFiles("static/template.html")
	engine.GET("/user/html", func(c *gin.Context) {
		//通过json往前端页面上传值
		c.HTML(http.StatusOK, "template.html", gin.H{"title": "用户信息", "name": "zcy", "addr": "bj"})
	})
}

func redirect(engine *gin.Engine) {
	engine.GET("/not_exists", func(c *gin.Context) {
		c.Redirect(http.StatusMovedPermanently, "http://localhost:5656/user/html")
	})
}
```
### 参数检验
&#8195;&#8195;GIN的参数检验是基于go-playground/validator实现的。   
```Go
import (
	"github.com/gin-gonic/gin"
	"github.com/gin-gonic/gin/binding"
	"github.com/go-playground/validator/v10" //注意要用新版本v10
)

type Student struct {
	Name       string    `form:"name" binding:"required"`                                                                //required:必须上传name参数
	Score      int       `form:"score" binding:"gt=0"`                                                                   //score必须为正数
	Enrollment time.Time `form:"enrollment" binding:"required,before_today" time_format:"2006-01-02" time_utc:"8"`       //自定义验证before_today，日期格式东8区
	Graduation time.Time `form:"graduation" binding:"required,gtfield=Enrollment" time_format:"2006-01-02" time_utc:"8"` //毕业时间要晚于入学时间
}

//自定义验证器
var beforeToday validator.Func = func(fl validator.FieldLevel) bool {
	if date, ok := fl.Field().Interface().(time.Time); ok {
		today := time.Now()
		if date.Before(today) {
			return true
		} else {
			return false
		}
	} else {
		return false
	}
}
```
### 中间件
```Go
package main

import (
	"log"
	"net/http"
	"time"

	"github.com/gin-gonic/gin"
)

var limitCh = make(chan struct{}, 100) //最多并发处理100个请求

func timeMiddleWare() gin.HandlerFunc {
	return func(ctx *gin.Context) {
		begin := time.Now()
		ctx.Next() //执行业务逻辑
		timeElapsed := time.Since(begin)
		log.Printf("request %s use %d ms\n", ctx.Request.URL.Path, timeElapsed.Milliseconds())
	}
}

func limitMiddleWare() gin.HandlerFunc {
	return func(ctx *gin.Context) {
		limitCh <- struct{}{} //并发度达到100时就会阻塞
		log.Printf("concurrence %d\n", len(limitCh))
		ctx.Next() //执行业务逻辑
		<-limitCh
	}
}

func main() {
	engine := gin.Default()
	engine.Use(timeMiddleWare()) //全局MiddleWare
	engine.GET("/", func(ctx *gin.Context) {
		ctx.String(http.StatusOK, "hi boy")
	})
	engine.GET("/girl", limitMiddleWare(), func(ctx *gin.Context) { //局部MiddleWare
		ctx.String(http.StatusOK, "hi girl")
	})
	engine.Run(":5656")
}
```
&#8195;&#8195;[gin-gonic/contrib](https://github.com/gin-gonic/contrib/blob/master/README.md)上提供了丰富的第三方中间件。  
### 会话
&#8195;&#8195;http是无状态的，即服务端不知道两次请求是否来自于同一个客户端。Cookie由服务端生成，发送给客户端，客户端保存在本地。客户端每次发起请求时把Cookie带上，以证明自己的身份。HTTP请求中的Cookie头只会包含name和value信息（服务端只能取到name和value），domain、path、expires等cookie属性是由浏览器使用的，对服务器来说没有意义。Cookie可以被浏览器禁用。  
server_session.go
```Go
package main

import (
	"encoding/base64"
	"fmt"
	"net/http"
	"strconv"
	"sync"
	"time"

	"github.com/gin-gonic/gin"
)

var (
	authMap sync.Map
)

//cookie name需要符合规则，否则该cookie会被Gin框架默默地丢弃掉
func genCookieName(ctx *gin.Context) string {
	return base64.StdEncoding.EncodeToString([]byte(ctx.Request.RemoteAddr))
}

//登录
func login(engine *gin.Engine) {
	engine.POST("/login", func(ctx *gin.Context) {
		//为客户端生成cookie
		cookie_key := genCookieName(ctx)
		cookie_value := strconv.Itoa(time.Now().Nanosecond())
		//服务端维护所有客户端的cookie，用于对客户端进行认证
		authMap.Store(cookie_key, cookie_value)
		//把cookie发给客户端
		ctx.SetCookie(cookie_key, cookie_value,
			3000,        //maxAge，cookie的有效时间，时间单位秒
			"/",         //path，cookie存放目录
			"localhost", //cookie从属的域名
			false,       //是否只能通过https访问
			true,        //是否允许别人通过js获取自己的cookie
		)
		fmt.Printf("set cookie %s = %s to client\n", cookie_key, cookie_value)
		ctx.String(http.StatusOK, "登录成功")
	})
}

//用户中心
func userCenter(engine *gin.Engine) {
	engine.POST("/center", authMiddleWare(), func(ctx *gin.Context) { //为"/center"加个认证中间件
		ctx.String(http.StatusOK, "您已通过身份认证，这里是你的私人空间")
	})
}

func authMiddleWare() gin.HandlerFunc {
	return func(ctx *gin.Context) {
		cookie_key := genCookieName(ctx)
		var cookie_value string
		//读取客户端的cookie
		for _, cookie := range ctx.Request.Cookies() {
			if cookie.Name == cookie_key {
				cookie_value = cookie.Value
				break
			}
		}

		//验证Cookie Value是否正确
		if v, ok := authMap.Load(cookie_key); !ok {
			fmt.Printf("INVALID auth cookie %s = %s\n", cookie_key, cookie_value)
			ctx.JSON(http.StatusForbidden, gin.H{cookie_key: cookie_value})
			ctx.Abort() //验证不通过，调用Abort
		} else {
			if v.(string) == cookie_value {
				ctx.Next() //本中间件顺利通过
			} else {
				fmt.Printf("INVALID auth cookie %s = %s\n", cookie_key, cookie_value)
				ctx.JSON(http.StatusForbidden, gin.H{cookie_key: cookie_value})
				ctx.Abort() //验证不通过，调用Abort
			}
		}
	}
}

func main() {
	engine := gin.Default()

	//路由
	login(engine)
	userCenter(engine)

	gin.SetMode(gin.ReleaseMode) //发布模式，屏蔽debug信息
	engine.Run("127.0.0.1:5656") //测试方法，运行http/client/main.go里的authLogin()方法
}
```
client_session.go
```Go
func main() {
	if resp, err := http.Post("http://127.0.0.1:5656/login", "text/plain", nil); err != nil {
		panic(err)
	} else {
		fmt.Println("response body")
		io.Copy(os.Stdout, resp.Body) //两个io数据流的拷贝
		os.Stdout.WriteString("\n")
		loginCookies := resp.Cookies() //读取服务端返回的Cookie
		resp.Body.Close()
		if req, err := http.NewRequest("POST", "http://127.0.0.1:5656/center", nil); err != nil {
			panic(err)
		} else {
			//下次请求再带上cookie
			for _, cookie := range loginCookies {
				fmt.Printf("receive cookie %s = %s\n", cookie.Name, cookie.Value)
				// cookie.Value += "1" //修改cookie后认证不通过
				req.AddCookie(cookie)
			}
			client := &http.Client{}
			if resp, err := client.Do(req); err != nil {
				fmt.Println(err)
			} else {
				defer resp.Body.Close()
				fmt.Println("response body")
				io.Copy(os.Stdout, resp.Body) //两个io数据流的拷贝
				os.Stdout.WriteString("\n")
			}
		}
	}
}
```
## Beego
&#8195;&#8195;beego是一个大而全的http框架，用于快速开发go应用程序。bee工具提供诸多命令，帮助我们进行 beego 项目的创建、热编译、开发、测试、和部署。 
```Shell
go get github.com/astaxie/beego
go get github.com/beego/bee
cd $GOPATH/src
bee new myweb
cd myweb
go build -mod=mod
bee run
```
&#8195;&#8195;beego的八大模块互相独立，高度解耦，开发者可任意选取：  
1. 日志模块
2. ORM模块
3. Context模块。封装了request和response
4. Cache模块。封装了memcache、redis、ssdb
5. Config模块。解析.ini、.yaml、.xml、.json、.env等配置文件
6. httplib模块
7. Session模块。session保存在服务端，用于标识客户身份，跟踪会话
8. toolbox模块。健康检查、性能调试、访问统计、计划任务  

MVC开发模式  

<img src=img/mvc.png width=500 />

&#8195;&#8195;在Model层可以使用beego提供的ORM功能。