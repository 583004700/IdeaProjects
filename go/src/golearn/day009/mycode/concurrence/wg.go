package main

import (
	"fmt"
	"sync"
	"time"
)

var wg = sync.WaitGroup{}

func Add() {
	time.Sleep(3000 * time.Millisecond)
	fmt.Println("over")
	wg.Done()
}

func main1() {
	wg.Add(100)
	for i := 0; i < 100; i++ {
		go Add()
	}
	wg.Wait()
}
