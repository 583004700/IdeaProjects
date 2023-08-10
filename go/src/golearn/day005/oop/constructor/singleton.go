package main

import "sync"

var (
	sUser *User
	uOnce sync.Once
)

func GetUserInstance() *User {
	uOnce.Do(func() { //确保即使在并发的情况下，下面的3行代码在整个go进程里只会被执行一次
		if sUser == nil {
			sUser = NewDefaultUser()
		}
	})
	return sUser //sUser是个全局变量，每次调用GetUserInstance()返回的都是它
}
