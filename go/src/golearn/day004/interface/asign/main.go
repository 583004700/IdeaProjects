package main

import "fmt"

type Transporter interface {
	whistle(int) int
}

type Car struct{}

func (car Car) whistle(n int) int { //方法接收者是值
	for i := 0; i < n; i++ {
		fmt.Printf("滴")
	}
	fmt.Println()
	return n
}

type Shiper struct{}

func (ship *Shiper) whistle(n int) int { //方法接收者用指针，则实现接口的是指针类型
	for i := 0; i < n; i++ {
		fmt.Printf("呜")
	}
	fmt.Println()
	return n
}

func scream(transporter Transporter) {
	transporter.whistle(3)
}

func scream2(transporter *Transporter) {
	(*transporter).whistle(3)
}

func main() {
	car := Car{}
	ship := Shiper{}

	var transporter Transporter
	transporter = car //对象赋值给接口时，会发生拷贝
	transporter.whistle(3)
	transporter = &ship
	transporter.whistle(3)
	transporter = &car //如果方法接收者是值，则给接口赋值的可以是值，也可以是指针（因为值实现的方法，指针同样也实现了）
	transporter.whistle(3)
	fmt.Println()

	scream(car)
	scream(&ship)
	scream(&car) //如果方法接收者是值，则给接口赋值的可以是值，也可以是指针（因为值实现的方法，指针同样也实现了）
	fmt.Println()

	transporter = car
	scream2(&transporter) //不能直接使用scream2(&car)，必须先把car转成Transporter
	transporter = &ship
	scream2(&transporter)
	fmt.Println()
}

//go run interface/asign/main.go
