/*
defer典型的应用场景是释放资源，比如关闭文件句柄，释放数据库连接等
*/
package main

import (
	"fmt"
)

func basic() {
	fmt.Println("A")
	defer fmt.Println(1) //defer用于注册一个延迟调用（在函数返回之前调用）
	fmt.Println("B")
	defer fmt.Println(2) //如果同一个函数里有多个defer，则后注册的先执行
	fmt.Println("C")
	defer fmt.Println(3)
	fmt.Println("D")
}

func defer_exe_time() (i int) {
	i = 9
	defer func() { //defer后可以跟一个func
		fmt.Printf("first i=%d\n", i) //打印5，而非9。充分理解“defer在函数返回前执行”的含义，不是在“return语句前执行defer”
	}()
	defer func(i int) {
		fmt.Printf("second i=%d\n", i) //打印9
	}(i)
	defer fmt.Printf("third i=%d\n", i) //defer后不是跟func，而直接跟一条执行语句，则相关变量在注册defer时被拷贝或计算
	return 5
}

func defer_panic() {
	defer fmt.Println(1)
	n := 0
	// defer fmt.Println(1 / n) //在注册defer时就要计算1/n，发生panic，第3个defer根本就没有注册。发生panic时首先会去执行已注册成功的defer，然后打印错误调用堆栈，最后exit(2)
	defer func() {
		fmt.Println(1 / n)   //defer func 内部发生panic，main协程不会exit，其他defer还可以正常执行
		defer fmt.Println(2) //上面那行代码发生发panic，所以本行的defer没有注册成功
	}()
	defer fmt.Println(3)
}

func main() {
	basic()
	fmt.Println()
	defer_exe_time()
	fmt.Println()
	defer_panic()
}

//go run function/defer/main.go
