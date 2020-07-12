package main

import "fmt"

func Adder() func(int) int {
	var c = 10
	return func(k int) int {
		c = k + c
		return c
	}
}

func main() {
	f := Adder()
	fmt.Println(f(1))
	fmt.Println(f(1))
	fmt.Println(f(1))
	fmt.Println(f(1))
	fmt.Println(f(1))
}
