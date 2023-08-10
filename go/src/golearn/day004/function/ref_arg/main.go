/*
slice、map、channel都是引用类型，它们作为函数参数时其实跟普通struct没什么区别，都是对struct内部的各个字段做一次拷贝传到函数内部
*/
package main

import (
	"fmt"
)

type user struct {
	name string
}

func update_user(u user) { //形参是实参的拷贝
	u.name = "宋江" //修改形参，不影响实参
}

func update_user_ptr(u *user) { //传指针，形参和实参指向同一块内存空间
	u.name = "宋江" //通过形参指针修改内存里的值，实参也会受影响
}

func slice_arg_1(arr []int) { //slice作为参数，实际上是把slice的arrayPointer、len、cap拷贝了一份传进来
	arr[0] = 1           //修改底层数据里的首元素
	arr = append(arr, 1) //arr的len和cap发生了变化，不会影响实参
	fmt.Printf("slice_arg_1 len %d, cap %d\n", len(arr), cap(arr))
}

func slice_arg_2(brr []int) {
	brr = append(brr, 1) //扩容，不影响实参
	brr = append(brr, 1)
	fmt.Printf("slice_arg_2 len %d, cap %d\n", len(brr), cap(brr))
}

func slice_arg_3(arr *[]int) {
	(*arr)[0] = 1
	(*arr) = append((*arr), 1)
	fmt.Printf("slice_arg_3 len %d, cap %d\n", len(*arr), cap(*arr))
}

func slice_arg_4(brr *[]int) {
	(*brr) = append((*brr), 1)
	(*brr) = append((*brr), 1)
	fmt.Printf("slice_arg_4 len %d, cap %d\n", len(*brr), cap(*brr))
}

func main() {
	u := user{name: "晁盖"}
	update_user(u)
	fmt.Println(u.name)
	update_user_ptr(&u)
	fmt.Println(u.name)
	fmt.Println()

	arr := make([]int, 1)
	slice_arg_1(arr)
	fmt.Printf("arr %v\n", arr)
	fmt.Printf("arr len %d, cap %d\n", len(arr), cap(arr))
	fmt.Println()

	brr := make([]int, 0, 1)
	slice_arg_2(brr)
	fmt.Printf("brr %v\n", brr)
	fmt.Printf("brr len %d, cap %d\n", len(brr), cap(brr))
	fmt.Println()

	slice_arg_3(&arr)
	fmt.Printf("arr %v\n", arr)
	fmt.Printf("arr len %d, cap %d\n", len(arr), cap(arr))
	fmt.Println()

	slice_arg_4(&brr)
	fmt.Printf("brr %v\n", brr)
	fmt.Printf("brr len %d, cap %d\n", len(brr), cap(brr))
	fmt.Println()
}

//go run function/ref_arg/main.go
