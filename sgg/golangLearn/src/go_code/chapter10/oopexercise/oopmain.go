package main

import "fmt"

type Person struct {
	id int
	name string
}

func (person *Person) say(){
	fmt.Println(person.id,"---",person.name)
}

func main() {
	var p = Person{1,"张三"}
	p.say()
}
