package main

import (
	"errors"
	"fmt"
)

func myerror() error {
	return errors.New("异常")
}

func main() {
	defer func() {
		erra := recover()
		fmt.Println("erra", erra)
	}()

	err := myerror()
	if err != nil {
		//抛出错误，终止程序
		panic(err)
	}

	fmt.Println("后面的语句")
}
