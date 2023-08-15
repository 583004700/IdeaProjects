package main

import (
	"container/list"
	"fmt"
	"strconv"
)

type LRUCache struct {
	cache map[int]string
	lst   list.List
	Cap   int //缓存容量的上限
}

func NewLRUCache(cap int) *LRUCache {
	lru := new(LRUCache)
	lru.Cap = cap
	lru.cache = make(map[int]string, cap)
	lru.lst = list.List{}
	return lru
}

func (lru *LRUCache) Add(key int, value string) {
	if len(lru.cache) < lru.Cap { //还没有到达缓存容量上限
		//直接把key value放到缓存中去
		lru.cache[key] = value
		lru.lst.PushFront(key)
	} else { //刚刚到达缓存容量上限
		//先从缓存中淘汰一个元素
		back := lru.lst.Back()
		delete(lru.cache, back.Value.(int)) //interface {} is nil, not int
		lru.lst.Remove(back)
		//然后再把key value放到缓存中去
		lru.cache[key] = value
		lru.lst.PushFront(key)
	}
}

func (lru *LRUCache) find(key int) *list.Element {
	if lru.lst.Len() == 0 {
		return nil
	}
	head := lru.lst.Front()
	for {
		if head == nil {
			break
		}
		if head.Value.(int) == key {
			return head
		} else {
			head = head.Next()
		}
	}
	return nil
}

func (lru *LRUCache) Get(key int) (string, bool) {
	value, exists := lru.cache[key]
	ele := lru.find(key)
	if ele != nil {
		lru.lst.MoveToFront(ele)
	}
	return value, exists
}

func testLRU() {
	lru := NewLRUCache(10)
	for i := 0; i < 10; i++ {
		lru.Add(i, strconv.Itoa(i)) //9 8 7 6 5 4 3 2 1 0
	}

	for i := 0; i < 10; i += 2 {
		lru.Get(i) //8 6 4 2 0 9 7 5 3 1
	}

	for i := 10; i < 15; i++ {
		lru.Add(i, strconv.Itoa(i)) //14 13 12 11 10 8 6 4 2 0
	}

	for i := 0; i < 10; i++ {
		_, exists := lru.Get(i)
		fmt.Printf("key %d exists %t\n", i, exists) //9 7 5 3 1不存在，8 6 4 2 0存在
	}
}
