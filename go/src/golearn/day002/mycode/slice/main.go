package main

import "fmt"

func update_slice(arr []int) {
	arr[0] = 8
}

func main() {
	var slice = []int{1, 3, 5, 7, 9}
	// 切片可以直接更新，而数组不可以
	update_slice(slice)
	fmt.Printf("%v\n", slice)

	arr := []int{2, 4, 8, 16, 32}
	// 截取子切片
	brr := arr[1:3]
	fmt.Printf("%d,%d\n", len(brr), cap(brr))
	// append 也会影响arr，因为此时 brr 数组的内存空间和 arr 是一样的
	brr = append(brr, 1)
	brr = append(brr, 1)
	// brr 数组长度超过了 arr 长度时，内存空间就不再相同，添加的元素和修改元素不会再影响
	brr = append(brr, 1)
	fmt.Printf("%d,%d\n", len(brr), cap(brr))
	fmt.Printf("%#v\n", arr)
	fmt.Printf("%#v\n", brr)
	brr[0] = 999
	fmt.Printf("%#v\n", arr)
	fmt.Printf("%#v\n", brr)

	for index, ele := range arr {
		fmt.Printf("%d=%v\n", index, ele)
	}

	for i := 0; i < len(arr); i++ {
		fmt.Printf("%d=%v\n", i, arr[i])
	}
}
