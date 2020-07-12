package main

import "fmt"

func main() {
	var arr = [...]int{1,2,3,4,5}
	var sli = arr[1:3]
	fmt.Println(len(sli))
	fmt.Println(sli[0])
	fmt.Println(sli[1])
}
