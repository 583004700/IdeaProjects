package main

import "fmt"

func testDefer() {

	defer func() {
		if err := recover(); err != nil {
			fmt.Println(err)
		}
	}()

	fmt.Println("111111111111111111111")
	defer fmt.Println("AAAAAAAAAAAAAA")
	fmt.Println("22222222222222222")
	defer fmt.Println("BBBBBBBBBBBBBBBB")
	panic("异常了！")
	fmt.Println("异常后面的代码")
}

func main() {
	testDefer()
}
