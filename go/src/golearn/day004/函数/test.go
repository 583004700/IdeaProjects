package main

import "fmt"

func slice_arg_1(arr []int) { //slice作为参数，实际上是把slice的arrayPointer、len、cap拷贝了一份传进来
	arr[0] = 1           //修改底层数据里的首元素
	arr = append(arr, 1) //arr的len和cap发生了变化，不会影响实参
}

func main() {
	fmt.Print("a中"[1])
}
