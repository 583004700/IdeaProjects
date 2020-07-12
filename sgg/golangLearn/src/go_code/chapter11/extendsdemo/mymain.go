package main

import "fmt"

type Person struct {
	id   int
	name string
}

func (p *Person) printInfo() {
	fmt.Println(p.id, "---", p.name)
}

type Person1 struct {
	Person
	age int
}

func (pe *Person1) printInfo1() {
	pe.printInfo()
	fmt.Println(pe.age)
}

func main() {
	var p = Person1{}
	p.name = "张三"
	p.age = 20
	p.id = 2
	p.printInfo1()

}
