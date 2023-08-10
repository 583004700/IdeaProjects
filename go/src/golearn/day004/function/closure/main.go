package main

import "fmt"

// 闭包（Closure）是引用了自由变量的函数。自由变量将和函数一同存在，即使已经离开了创造它的环境。
func sub() func() {
	i := 10
	fmt.Printf("%p\n", &i)
	b := func() {
		fmt.Printf("i addr %p\n", &i) //闭包复制的是原对象的指针
		i--                           //b函数内部引用了变量i
		fmt.Println(i)
	}
	return b //返回了b函数，变量i和b函数将一起存在，即使已经离开函数sub()
}

// 外部引用函数参数局部变量
func add(base int) func(int) int {
	return func(i int) int {
		fmt.Printf("base addr %p\n", &base)
		base += i
		return base
	}
}

func main() {
	b := sub()
	b()
	b()
	fmt.Println()

	tmp1 := add(10)
	fmt.Println(tmp1(1), tmp1(2)) //11,13
	// 此时tmp1和tmp2不是一个实体了
	tmp2 := add(100)
	fmt.Println(tmp2(1), tmp2(2)) //101,103
}

//go run function/closure/main.go
