package main

import (
	"fmt"
	"golearn/day006/package/common"
)

func init() { //先执行这个init()
	fmt.Println("enter package/biz/server")
}

// 在一个目录，甚至一个go文件里，init()可以重复定义
func init() { //再执行这个init()
	fmt.Println("reenter package/biz/server")
}

func main() {
	teacher := new(common.Teacher)
	teacher.Examine()
}

// go run package/biz/server.go
/*
enter package/user
enter package/common/math/basic
enter package/common/student
enter package/common/teacher
enter package/biz/server
reenter package/biz/server
sum is 21
*/
