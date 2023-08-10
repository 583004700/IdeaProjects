package main

import "fmt"

func basic_goto() {
	var i int = 4
MY_LABEL:
	i += 3
	i *= 2
	fmt.Println(i)
	if i > 200 {
		return //加个return，避免无限循环
	}
	goto MY_LABEL //返回定义MY_LABEL的那一行，把代码再执行一遍（会进入一个无限循环）
}

func if_goto() {
	var i int = 4
	if i%2 == 0 {
		goto L1 //Label指示的是某一个代码，并没有圈定一个代码块，所以goto L1会把25到29行的代码全部执行
	} else {
		goto L2 //先使用Label
	}
L1:
	i += 3
	fmt.Println(i)
L2: //后定义Label。Label定义后必须在代码的某个地方被使用
	i *= 3
	fmt.Println(i)
}

func for_goto() {
	const SIZE = 5
	arr := [SIZE][SIZE]int{}
L1:
	for i := 0; i < SIZE; i++ {
	L2:
		fmt.Printf("开始检查第%d行\n", i)
	L3:
		if i%2 == 1 {
			for j := 0; j < SIZE; j++ {
				fmt.Printf("开始检查第%d列\n", j)
				if arr[i][j]%3 == 0 {
					goto L1
				} else if arr[i][j]%3 == 1 {
					goto L2
				} else {
					goto L3
				}
			}
		}
	}
}

func continue_label() {
	const SIZE = 5
	arr := [SIZE][SIZE]int{}
L1:
	for i := 0; i < SIZE; i++ {
	L2:
		fmt.Printf("开始检查第%d行\n", i)
	L3:
		if i%2 == 1 {
			for j := 0; j < SIZE; j++ {
				fmt.Printf("开始检查第%d列\n", j)
				if arr[i][j]%3 == 0 {
					continue L1 //continue和break针对的Label必须写在for前面，而goto可以针对任意位置的Label
				} else if arr[i][j]%3 == 1 {
					goto L2
				} else {
					goto L3
				}
			}
		}
	}
}

func break_label() {
	const SIZE = 5
	arr := [SIZE][SIZE]int{}
L1:
	for i := 0; i < SIZE; i++ {
	L2:
		fmt.Printf("开始检查第%d行\n", i)

		if i%2 == 1 {
		L3:
			for j := 0; j < SIZE; j++ {
				fmt.Printf("开始检查第%d列\n", j)
				if arr[i][j]%3 == 0 {
					break L1 //直接退出最外层的fot循环
				} else if arr[i][j]%3 == 1 {
					goto L2 //continue和break针对的Label必须写在for前面，而goto可以针对任意位置的Label
				} else {
					break L3
				}
			}
		}
	}
}

func main() {
	basic_goto()
	fmt.Println("==============")
	if_goto()
	fmt.Println("==============")
	// for_goto()//会导致无限循环
	fmt.Println("==============")
	continue_label()
	fmt.Println("==============")
	break_label()
	fmt.Println("==============")
}

//go run process_control/goto/main.go
