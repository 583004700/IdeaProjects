package main

import (
	"fmt"
	"strconv"
)

func main() {
	var i int = 45
	var s = strconv.Itoa(i)
	fmt.Println(s)
	var i1 int64 = 87
	var s1 = strconv.FormatInt(i1, 10)
	fmt.Println(s1)
	var s2 string = "45678"
	var i2, _ = strconv.ParseInt(s2, 10, 32)
	fmt.Println(i2)
	var s3 = "你好"
	ca := []rune(s3)
	fmt.Println(len(ca))
}
