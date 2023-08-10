package main

import "fmt"

func update_array(arr [10]int) {
	arr[0] = 8
}

func real_update_array(arr *[10]int) {
	arr[0] = 8
}

func real_update_array2(arr *[5][10]int) {
	arr[1][1] = 9
}

func main() {
	var arr = [10]int{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}
	// 这个方法不能修改数组的值
	update_array(arr)
	fmt.Printf("%v\n", arr)
	// 这个方法可以修改数组的值
	real_update_array(&arr)
	fmt.Printf("%v\n", arr)

	var arr2 = [5][10]int{}
	real_update_array2(&arr2)
	fmt.Printf("%v\n", arr2)

	for index, ele := range arr {
		fmt.Printf("%d=%v\n", index, ele)
	}
}
