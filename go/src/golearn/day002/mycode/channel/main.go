package main

import "fmt"

// 可以修改channel的值
func updateChannel(c chan int) {
	c <- 100000
}

func main() {
	ch := make(chan int, 5)
	ch <- 8
	ch <- 9
	ch <- 10
	ch <- 11
	updateChannel(ch)
	fmt.Println(len(ch))
	fmt.Println(<-ch)
	fmt.Println(len(ch))
	close(ch)
	for ele := range ch {
		fmt.Printf("len:%d\n", len(ch))
		fmt.Println(ele)
	}
}
