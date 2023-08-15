package main

import (
	"fmt"
	"reflect"
	"unsafe"
)

type Car struct {
	CarName string
}

type User struct {
	name   string // 16B
	Age    int    `place:"abc"`                // 8B
	Sex    byte   `json:"gender" xml:"xinbie"` // 1B    8B
	*Car          // 8B
	height float32
}

func (u User) say() {

}

func (u User) Say2() {

}

func (u *User) Say3() {

}

func getType() {
	typeI := reflect.TypeOf(1)
	s := "hello"
	typeS := reflect.TypeOf(&s)
	fmt.Println(typeI)
	fmt.Println(typeS)

	fmt.Println(typeI.Kind())
	fmt.Println(typeS.Kind())
	u1 := User{}
	typeUser := reflect.TypeOf(u1)
	fmt.Println(typeUser.Kind())

	u2 := &User{}
	typeUser2 := reflect.TypeOf(u2)
	fmt.Println(typeUser2.Kind())
	fmt.Println(typeUser2.Elem())
	fmt.Println("----------------------")
	fmt.Println(typeUser.PkgPath())
	fmt.Println(typeUser.Name())
	fmt.Println(typeUser.Size())
}

var u1 User

func updateU(u User) {
	u.CarName = "大汽车"
}

func getField() {
	u1 = User{name: "张三", Age: 2, Sex: 2, Car: &Car{CarName: "小汽车"}}
	typeUser := reflect.TypeOf(u1)
	fieldNum := typeUser.NumField()
	for i := 0; i < fieldNum; i++ {
		field := typeUser.Field(i)
		fmt.Printf("%s,%t,%d,%s,%t,%s,%s\n", field.Name, field.Anonymous, field.Offset,
			field.Type, field.IsExported(),
			field.Tag.Get("json"), field.Tag.Get("place"))
	}
	updateU(u1)
}

func memoAlign() {
	type A struct {
		sex    bool   // 1B		offset 0
		weight uint16 // 2B		offset 2,前两个合在一起占8B，如果这个属性放在最下面，则不能合在一起
		age    int64  // 8B		offset 8
	}
	u := A{}
	typeUser := reflect.TypeOf(u)
	fmt.Println(typeUser.Size())
	fmt.Println(unsafe.Alignof(u))
	fieldNum := typeUser.NumField()
	for i := 0; i < fieldNum; i++ {
		field := typeUser.Field(i)
		fmt.Printf("%s,%t,%d,%s,%t,%s,%s\n", field.Name, field.Anonymous, field.Offset,
			field.Type, field.IsExported(),
			field.Tag.Get("json"), field.Tag.Get("place"))
	}
}

func getMethod() {
	// 如果是带指针的方法，也要传入对象的指针
	//typeUser := reflect.TypeOf(&User{})
	typeUser := reflect.TypeOf(User{})
	valueUser := reflect.ValueOf(User{})
	methodNum := typeUser.NumMethod()
	for i := 0; i < methodNum; i++ {
		method := typeUser.Method(i)
		fmt.Printf("name: %s type: %s, exported %t\n", method.Name, method.Type, method.IsExported())
	}
	m, _ := typeUser.MethodByName("Say2")
	fmt.Println(m.Name)
	fmt.Println(valueUser.CanAddr())
}

func getFunc() {
	typeFunc := reflect.TypeOf(updateU)
	fmt.Println(typeFunc.Kind() == reflect.Func)
	argInNum := typeFunc.NumIn()
	argOutNum := typeFunc.NumOut()
	for i := 0; i < argInNum; i++ {
		argType := typeFunc.In(i)
		fmt.Printf("第%d个输入参数的类型是%s\n", i, argType)
	}
	for i := 0; i < argOutNum; i++ {
		argType := typeFunc.Out(i)
		fmt.Printf("第%d个出参数的类型是%s\n", i, argType)
	}
}

func main() {
	//getType()
	//getField()
	//memoAlign()
	//getMethod()
	getFunc()
}
