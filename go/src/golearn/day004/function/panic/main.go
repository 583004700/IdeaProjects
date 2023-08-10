package main

import (
	"errors"
	"fmt"
	"math/rand"
	"time"
)

// 自定义error
type PathError struct {
	path       string
	op         string
	createTime string
	message    string
}

func NewPathError(path, op, message string) PathError {
	return PathError{
		path:       path,
		op:         op,
		createTime: time.Now().Format("2006-01-02"),
		message:    message,
	}
}

// error接口要求实现Error() string方法
func (err PathError) Error() string {
	return err.createTime + ": " + err.op + " " + err.path + " " + err.message
}

func divide(a, b int) (int, error) {
	if b == 0 {
		return -1, errors.New("divide by zero")
	}
	return a / b, nil
}

func delete_path(path string) error {
	if rand.Int31()%2 == 0 { //模拟正常情况
		return nil
	} else { //模拟异常情况
		return NewPathError(path, "delete", "path not exists") //返回自定义error
	}
}

func soo() {
	fmt.Println("enter soo")

	defer func() { //去掉这个defer试试，看看panic的流程。把这个defer放到soo函数末尾试试
		//recover必须在defer中才能生效
		if err := recover(); err != nil {
			fmt.Printf("soo函数中发生了panic:%s\n", err)
		}
	}()
	fmt.Println("regist recover")

	defer fmt.Println("hello")
	//defer func() {
	n := 0
	_ = 3 / n //除0异常，发生panic，下一行的defer没有注册成功
	defer fmt.Println("how are you")
	//}()
}

func main() {
	// if res, err := divide(3, 0); err == nil {
	// 	fmt.Println(res)
	// } else {
	// 	fmt.Println(err.Error())
	// }
	// fmt.Println()
	soo()
	fmt.Println("soo没有使main协程退出")
}

//go run function/panic/main.go
