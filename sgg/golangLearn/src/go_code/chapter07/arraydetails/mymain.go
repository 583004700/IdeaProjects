package main

import "fmt"

func t1(arr [8]int)  {
	arr[0] = 99
	fmt.Println(arr[0])
}

func t2(arr *[8]int)  {
	(*arr)[0] = 100
}

func main() {
	var arr = [8]int{}
	t1(arr)
	//数组会复制一份当作参数传入，所以值不会被改变
	fmt.Println(arr[0])

	t2(&arr)
	fmt.Println(arr[0])
}
