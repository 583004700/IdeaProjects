package main

import "fmt"

func main() {
	var sli = []int{1,2,3,4,5,6,7}
	var sli2 = make([]int,10)
	copy(sli2,sli)

	for i,v := range sli2{
		fmt.Println(i,v)
	}
}
