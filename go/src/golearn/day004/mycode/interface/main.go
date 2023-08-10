package main

import "fmt"

type Animal interface {
	say()
	eat(food string)
}

type Dog struct {
	name string
}

func (dog *Dog) say() {

}

func (dog *Dog) eat(food string) {
	fmt.Println(dog.name + "正在吃" + food)
}

type Cat struct {
	name string
}

func (cat Cat) say() {

}

func (cat Cat) eat(food string) {
	cat.name = "小猫"
	fmt.Println(cat.name + "正在吃" + food)
}

// Tomcat 相当于继承了Animal
type Tomcat interface {
	Animal
	server(ip string, port int)
}

func test(animal Animal) {
	animal.eat("饭")
}

func main() {
	dog := Dog{"小黄狗"}
	// Dog 只有指针实现了Animal接口，所以只能传指针
	test(&dog)

	cat := Cat{"小黑猫"}
	// Dog 实现了Animal接口，指针也会实现 Animal,所以可以传对象和指针
	test(cat)
	fmt.Println(cat.name)
	test(&cat)
	// new 方法返回的是对象的指针
	dog2 := new(Dog)
	test(dog2)
}
