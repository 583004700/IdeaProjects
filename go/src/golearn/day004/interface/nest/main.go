package main

import "fmt"

type Transporter interface {
	whistle(int) int
}

type Steamer interface {
	Transporter //接口嵌入。相当于Transporter接口定义的行为集合是Steamer的子集
	displacement() int
}

type Player interface {
	//甚至可以嵌入多个接口
	Transporter
	Steamer //由于Steamer包含了Transporter，实际上Player定义的行为集合跟Steamer是一样的
}

type Car struct{}

func (car Car) whistle(n int) int {
	for i := 0; i < n; i++ {
		fmt.Printf("滴 ")
	}
	fmt.Println()
	return n
}

type Shiper struct {
	Car    //struct嵌入。由于Car实现了whistle方法，等价于Shiper也实现了whistle方法
	tonage int
}

func (ship Shiper) displacement() int {
	return ship.tonage
}

func main() {
	car := Car{}
	var transporter Transporter
	transporter = car
	transporter.whistle(3)
	fmt.Println()

	ship := Shiper{tonage: 100}
	var steamer Steamer
	steamer = ship
	steamer.whistle(5)
	steamer.displacement()
}

//go run interface/nest/main.go
