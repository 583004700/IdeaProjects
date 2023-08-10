package main

import (
	"fmt"
)

// 匿名函数
var sum = func(a, b int) int {
	return a + b
}

// 匿名结构体
var stu struct {
	name string
	age  int
}

func add(a, b int) (c int) {
	c = a + b
	return
}

func function_arg1(f func(a, b int) int, b int) int { //f参数是一种函数类型（函数类型看上去比较冗长）
	a := 2 * b
	return f(a, b)
}

type foo func(a, b int) int //foo是一种函数类型

func function_arg2(f foo, b int) int { //参数类型看上去乘洁多了
	a := 2 * b
	return f(a, b)
}

type User struct {
	Name  string
	bye   foo                      //bye的类型是foo，而foo代表一种函数类型
	hello func(name string) string //使用匿名函数来声明struct字段的类型
}

func main() {
	stu.age = 18
	fmt.Printf("sum %d\n", sum(3, 6)) //把sum当成函数使用

	x, y := 3, 6
	fmt.Println(function_arg1(add, x))
	fmt.Println(function_arg2(add, x))
	fns := [](foo){add, add} //slice的元素是foo类型
	println(fns[0](x, y))
	fmt.Println()

	u := User{
		Name: "宋江",
		bye:  add,
		hello: func(name string) string {
			return "hello " + name
		},
	}
	u.hello("林冲")
	fmt.Println()

	ch1 := make(chan foo, 5)                  //channel里的元素可以是函数类型
	ch2 := make(chan func(string) string, 10) //用匿名函数
	ch1 <- add
	ch2 <- func(name string) string {
		return "hello " + name
	}
}

// go run function/anonymous/main.go
