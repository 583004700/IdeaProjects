package main

import (
	"errors"
	"fmt"
)

// 洗衣机
type Washer struct {
	State  bool
	Powder int
}

// 衣服
type Closes struct {
	Clean bool
}

func (washer *Washer) prepare(closes []*Closes) error {
	if washer.State == true || washer.Powder <= 0 || len(closes) <= 0 {
		return errors.New("请确保在关机的状态下加入适量衣物和洗衣粉")
	}
	return nil
}

func (washer *Washer) wash(closes []*Closes) error {
	if err := washer.prepare(closes); err != nil {
		return err
	}

	fmt.Println("开机")
	washer.State = true

	//检查是否有脏衣服
	clean := true
	for _, ele := range closes {
		if ele.Clean == false {
			clean = false
			break
		}
	}
	if clean {
		washer.State = false
		return errors.New("所有衣服都是干净的，不需要洗")
	}

	//开始洗衣服
	fmt.Println("注水")
	fmt.Println("滚动")
	fmt.Println("关机")
	washer.State = false
	for _, ele := range closes {
		ele.Clean = true
	}
	return nil
}

func (washer *Washer) check(err error, closes []*Closes) {
	if err != nil {
		fmt.Printf("洗衣失败:%v\n", err)
	} else {
		fmt.Printf("洗干净了%d件衣服\n", len(closes))
		if washer.State == true {
			fmt.Println("你忘关洗衣机了")
		}
	}
}

func main() {
	WashProcedure(10, 5)
	fmt.Println()
	WashProcedure(10, 0)
	fmt.Println()

	washer := &Washer{State: false, Powder: 10}
	// washer.Powder = 0
	closes := []*Closes{{Clean: false}, {Clean: true}}
	err := washer.wash(closes)
	washer.check(err, closes)
}

//go run oop/oop/*.go
