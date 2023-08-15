package main

import (
	"fmt"
	// "golearn/day006/package/a/b/c/internal"     //go build时会报错：use of internal package golearn/day006/a/b/c/internal not allowed
	// "golearn/day006/package/a/b/c/internal/e/f" //go build时会报错：use of internal package golearn/day006/a/b/c/internal/e/f not allowed
)

var B int

// func f1() {
// 	internal.Internal = 4
// }

// func f2() {
// 	f.F = 4
// }

func main() {
	// f1()
	// f2()
	fmt.Printf("B=%d\n", B)
}

//go build -o b golearn/day006/package/a/b
