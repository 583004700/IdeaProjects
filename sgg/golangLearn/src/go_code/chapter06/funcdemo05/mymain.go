package main

import "fmt"

func myFunct(f func(int, int) int, num1 int, num2 int) int {
	return f(num1, num2)
}

func myGetSum(num1 int, num2 int) int {
	return num1 + num2
}

func main() {
	sum := myFunct(myGetSum, 3, 4)
	fmt.Println(sum)
}
