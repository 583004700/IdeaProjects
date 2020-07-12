package main

import "fmt"

func testZ(n *int)  {
	fmt.Println(n)
	fmt.Println(*n)
}

func main() {
	var num = 100
	testZ(&num)
}
