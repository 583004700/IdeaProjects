package main

import (
	"fmt"
	"time"
)

func main() {
	ch := make(chan struct{}, 1)
	ch <- struct{}{} //有1个缓冲可以用，无需阻塞，可以立即执行
	go func() {      //子协程1
		time.Sleep(5 * time.Second) //sleep一个很长的时间
		<-ch                        //如果把本行代码注释掉，main协程5秒钟后会报fatal error
		fmt.Println("sub routine 1 over")
	}()

	ch <- struct{}{} //由于子协程1已经启动，寄希望于子协程1帮自己解除阻塞，所以会一直等子协程1执行结束。如果子协程1执行结束后没帮自己解除阻塞，则希望完全破灭，报出deadlock
	fmt.Println("send to channel in main routine")
	go func() { //子协程2
		time.Sleep(2 * time.Second)
		ch <- struct{}{} //channel已满，子协程2会一直阻塞在这一行
		fmt.Println("sub routine 2 over")
	}()
	time.Sleep(3 * time.Second)
	fmt.Println("main routine exit")
}

//go run concurrence/channel_deadlock.go
