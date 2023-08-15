package main

import (
	"fmt"
	"net/http"

	"github.com/julienschmidt/httprouter"
)

func main() {
	router := httprouter.New()
	router.ServeFiles("/file/*filepath", http.Dir("./http/static/site_a"))
	err := http.ListenAndServe(":5656", router)
	if err != nil {
		fmt.Println(err)
	}
}

//go run http/validation/jsonp/site_a/main.go
//浏览器中访问  http://localhost:5656/file/cross_site.html
