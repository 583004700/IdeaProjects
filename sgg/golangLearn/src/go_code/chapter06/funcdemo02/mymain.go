package main

import "fmt"

func myGetSum(num1 int, num2 int) int {
	var sum int = num1 + num2
	return sum
}

func main() {
	sum := myGetSum(2, 3)
	fmt.Println(sum)
}
