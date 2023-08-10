package main

import (
	"fmt"
)

// Addable 泛型
type Addable interface {
	int | int8 | int16 | int32 | int64 | uint | uint8 | uint16 | uint32 | uint64 |
		uintptr | float32 | float64 | complex64 | complex128 | string
}

func Add[T Addable](a, b T) T {
	return a + b
}

func main() {
	fmt.Println(1)
	a := Add(5, 6)
	fmt.Println(a)
	s := Add("hello", "world")
	fmt.Println(s)
}
