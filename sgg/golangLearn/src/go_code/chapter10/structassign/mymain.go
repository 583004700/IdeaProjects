package main

import "fmt"

type Person struct {
	id   int
	name string
}

func (person *Person) say() {
	fmt.Printf("id:%v  姓名:%v", person.id, person.name)
}

func main() {
	person := Person{
		name: "张三",
		id:   1,
	}
	person.say()
}
