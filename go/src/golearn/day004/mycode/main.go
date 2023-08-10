package main

import "fmt"

func deferExeTime() (i int) {
	i = 9
	fmt.Printf("i=%d\n", i)
	defer func() {
		// 打印出来的是5，返回值i return 的是5
		fmt.Printf("i=%d\n", i)
	}()
	defer func(a int) {
		// 打印出来的是9
		fmt.Printf("i=%d\n", a)
	}(i)
	defer fmt.Printf("i=%d\n", i)
	return 5
}

func main() {
	fmt.Println("A")
	// defer 延迟调用,后注册的先执行
	defer fmt.Println("1")
	fmt.Println("B")
	defer fmt.Println("2")
	fmt.Println("C")
	deferExeTime()
}
