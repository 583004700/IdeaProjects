package main

import "fmt"

func main() {
	var ch chan int //声明
	if ch == nil {
		fmt.Println("ch is nil")
	}
	if len(ch) == 0 { //引用类型未初始化时都是nil，可以对它们执行len()函数，返回0
		fmt.Println("ch length is 0")
	}
	ch = make(chan int, 8) //初始化，环形队列里可容纳8个int
	ch <- 1                //往管道里写入(send)数据
	ch <- 2
	ch <- 3
	ch <- 4
	ch <- 5
	v := <-ch //从管道里取走(recv)数据
	fmt.Println(v)
	v = <-ch
	fmt.Println(v)
	fmt.Println()

	close(ch) //遍历前必须先关闭管道，禁止再写入元素
	//遍历管道里剩下的元素
	for ele := range ch {
		fmt.Println(ele)
	}
}

//go run data_type/channel/main.go
