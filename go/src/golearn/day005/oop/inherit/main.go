package main

import (
	"fmt"
)

type Flyer interface {
	fly() int
	tweet()
}

type Plane struct {
	color string
}

func (plane Plane) fly() int {
	return 500
}

type Car struct {
	speed int
}

func (car Car) run() {
	fmt.Printf("run at %d km/h\n", car.speed)
}

// Bird组合了Plane和Car的功能
type Bird struct {
	Plane //通过嵌入匿名结构体，变相实现继承的功能
	Car
}

// Bird实现了Flyer接口，而Plane没有
func (bird Bird) tweet() {
	fmt.Println("叽叽叽")
}

// 重写父类(Plane)的fly方法
func (bird Bird) fly() int {
	fmt.Printf("color is %s\n", bird.color)
	return bird.Plane.fly() //调用父类的方法。当成员变量是匿名时可以直接通过数据类型访问
}

func main() {
	var flyer Flyer
	bird := Bird{}
	bird.color = "white"
	bird.speed = 60

	flyer = bird
	fmt.Printf("飞行高度%d\n", flyer.fly())
	flyer.tweet()

	bird.run()
}

//go run oop/inherit/main.go
