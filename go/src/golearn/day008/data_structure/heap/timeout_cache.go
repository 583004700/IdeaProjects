package main

import (
	"container/heap"
	"fmt"
	"time"
)

type HeapNode struct {
	value    int //对应到map里的key
	deadline int //到期时间戳，精确到秒
}

type Heap []*HeapNode

func (heap Heap) Len() int {
	return len(heap)
}
func (heap Heap) Less(i, j int) bool {
	return heap[i].deadline < heap[j].deadline
}
func (heap Heap) Swap(i, j int) {
	heap[i], heap[j] = heap[j], heap[i]
}
func (heap *Heap) Push(x interface{}) {
	node := x.(*HeapNode)
	*heap = append(*heap, node)
}
func (heap *Heap) Pop() (x interface{}) {
	n := len(*heap)
	last := (*heap)[n-1]
	//删除最后一个元素
	*heap = (*heap)[0 : n-1]
	return last //返回最后一个元素
}

type TimeoutCache struct {
	cache map[int]interface{}
	hp    Heap
}

func NewTimeoutCache(cap int) *TimeoutCache {
	tc := new(TimeoutCache)
	tc.cache = make(map[int]interface{}, cap)
	tc.hp = make(Heap, 0, 10)
	heap.Init(&tc.hp) //包装升级，从一个常规的slice升级为堆
	return tc
}

func (tc *TimeoutCache) Add(key int, value interface{}, life int) {
	//直接把key value放入map
	tc.cache[key] = value
	//计算出deadline，然后把key和deadline放入堆
	deadline := int(time.Now().Unix()) + life
	node := &HeapNode{value: key, deadline: deadline}
	heap.Push(&tc.hp, node)
}

func (tc TimeoutCache) Get(key int) (interface{}, bool) {
	value, exists := tc.cache[key]
	return value, exists
}

func (tc *TimeoutCache) taotai() {
	for {
		if tc.hp.Len() == 0 {
			time.Sleep(100 * time.Millisecond)
			continue
		}
		now := int(time.Now().Unix())
		top := tc.hp[0]
		if top.deadline < now {
			heap.Pop(&tc.hp)
			delete(tc.cache, top.value)
		} else { //堆顶还没有到期
			time.Sleep(100 * time.Millisecond)
		}
	}
}

func testTimeoutCache() {
	tc := NewTimeoutCache(10)
	go tc.taotai() //在子协程里面去执行，不影响主协程继续往后走

	tc.Add(1, "1", 1)
	tc.Add(2, "2", 3)
	tc.Add(3, "3", 4)

	time.Sleep(2 * time.Second)

	for _, key := range []int{1, 2, 3} {
		_, exists := tc.Get(key)
		fmt.Printf("key %d exists %t\n", key, exists) //1不存在，2 3还存在
	}
}
