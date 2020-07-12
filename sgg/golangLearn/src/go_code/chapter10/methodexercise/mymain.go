package main

import "fmt"

type Person struct {
	id   int
	name string
}

//不能改变id的值
func (p Person) setId(id int) {
	p.id = id
}

func (p *Person) setId1(id int) {
	p.id = id
}

func main() {
	var per = Person{}
	per.name = "张三"
	per.setId(1)
	fmt.Println(per.id)
	per.setId1(2)
	fmt.Println(per.id)
}
