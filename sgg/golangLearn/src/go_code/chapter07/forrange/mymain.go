package main

import "fmt"

func main() {
	var arr = [...]string{"a", "b", "c", "d", "e", "f", "g", "f"}
	for i, v := range arr {
		fmt.Printf("i=%v,v=%v\n", i, v)
	}
}
