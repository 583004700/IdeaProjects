package main

import "fmt"

// 匿名函数
var sum = func(a, b int) int {
	return a + b
}

// 不定长参数
func sum2(a ...int) int {
	result := 0
	for _, v := range a {
		result += v
	}
	return result
}

// 闭包
func count() func() int {
	result := 0
	f := func() int {
		result += 1
		return result
	}
	return f
}

func main() {
	fmt.Println(sum(6, 7))
	fmt.Println(sum2(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
	// 可以将切片展开为多个参数传递
	fmt.Println(sum2([]int{1, 2, 3, 4, 5}...))

	i := count()
	i()
	i()
	k := i()
	fmt.Println(k)
}
