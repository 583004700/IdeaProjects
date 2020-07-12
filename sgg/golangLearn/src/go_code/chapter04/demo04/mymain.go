package main

import "fmt"

func mytest() bool {
	fmt.Println("mytest()")
	return true
}

func main() {
	var i int = 10
	if i > 9 || mytest() {
		fmt.Printf("hello...")
	}
}
