package main

import (
	"fmt"
	"time"
)

func broadcast(ch chan struct{}) {
	fmt.Println("咳咳，乡亲们，我要广播了")
	close(ch)
}

func worker(ch chan struct{}) {
	<-ch
	fmt.Println("我收到广播了")
}

func main_broadcast() {
	ch := make(chan struct{})
	for i := 0; i < 5; i++ {
		go worker(ch)
	}

	time.Sleep(5 * time.Second)
	broadcast(ch)
	time.Sleep(10 * time.Millisecond) //等worker协程执行结束
}
