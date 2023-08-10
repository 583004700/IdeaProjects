package main

import "fmt"

func break_for() {
	arr := []int{1, 2, 3, 4, 5}
	for i, ele := range arr {
		fmt.Printf("当前元素%d\n", ele)
		if ele > 3 {
			break //退出for循环，且本轮break下面的代码不再执行
		}
		fmt.Printf("将要检查第%d个元素\n", i+1)
	}
}

func continue_for() {
	arr := []int{1, 2, 3, 4, 5}
	for i, ele := range arr {
		fmt.Printf("当前元素%d\n", ele)
		if ele > 3 {
			continue //本轮continue下面的代码不再执行，进入for循环的下一轮
		}
		fmt.Printf("将要检查第%d个元素\n", i+1)
	}
}

// break和continue都是针对for循环的，不针对if或switch
// break和continue都是针对套在自己外面的最靠里的那层for循环，不针对更外层的for循环（除非使用Label）
func complex_break_continue() {
	const SIZE = 5
	arr := [SIZE][SIZE]int{}
	for i := 0; i < SIZE; i++ {
		fmt.Printf("开始检查第%d行\n", i)
		if i%2 == 1 {
			for j := 0; j < SIZE; j++ {
				fmt.Printf("开始检查第%d列\n", j)
				if arr[i][j]%2 == 0 {
					continue //针对第二层for循环
				}
				fmt.Printf("将要检查第%d列\n", j+1)
			}
			break //针对第一层for循环
		}
	}
}

func main() {
	break_for()
	fmt.Println("=================")
	continue_for()
	fmt.Println("=================")
	complex_break_continue()
}

//go run process_control/break/main.go
