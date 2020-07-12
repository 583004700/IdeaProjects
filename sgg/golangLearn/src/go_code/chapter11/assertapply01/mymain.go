package main

import "fmt"

type MyUsb interface {
	start()
	stop()
}

type MyPhone struct {
	name string
}

func (phone *MyPhone) start() {
	fmt.Println(phone.name + "接入usb")
}

func (phone *MyPhone) stop() {
	fmt.Println(phone.name + "移除usb")
}

func (phone *MyPhone) t() {
	fmt.Println("t")
}

type MyComputer struct {
}

func (computer *MyComputer) start(usb MyUsb) {
	fmt.Println("电脑开始工作")
	usb.start()
}

func (computer *MyComputer) stop(usb MyUsb) {
	fmt.Println("电脑停止工作")
	usb.stop()
}

func main() {
	var phone = &MyPhone{"苹果手机"}
	var computer = MyComputer{}
	computer.start(phone)
}
