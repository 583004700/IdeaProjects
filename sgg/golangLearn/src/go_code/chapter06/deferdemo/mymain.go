package main

import "fmt"

func mydefer(num1 int, num2 int) {
	defer fmt.Println("num2=", num2)
	defer fmt.Println("num1=", num1)
	sum := num1 + num2
	fmt.Println("sum=", sum)
}

func main() {
	mydefer(10, 20)
}
