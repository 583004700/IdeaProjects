package main

/**
自己实现一个函数链式的sql构建器。只是一个小demo，还有诸多不完善的地方。
*/

import (
	"fmt"
	"strconv"
	"strings"
)

/*
*
先拿双向链表作个铺垫
*/
type Node struct {
	value int
	prev  *Node
	next  *Node
}

func (node *Node) toString() {
	fmt.Print(strconv.Itoa(node.value) + " ")
	tail := node
	if tail.next != nil {
		tail = tail.next
		tail.toString()
	}
	fmt.Println()
}

func (node *Node) ToString() {
	root := node
	for root.prev != nil {
		root = root.prev
	}
	root.toString()
}

func testDoubleLink() {
	n1 := &Node{value: 4}
	n2 := &Node{value: 9}
	n3 := &Node{value: 8}
	n4 := &Node{value: 1}
	n1.next = n2
	n2.prev = n1
	n2.next = n3
	n3.prev = n2
	n3.next = n4
	n4.prev = n3

	n2.toString() //9 8 1
	n2.ToString() //4 8 9 1
	n1.toString() //4 8 9 1
	n4.ToString() //4 8 9 1
	n4.toString() //1
}

// Builder 根据一个函数生成一小段sql
type Builder interface { //select、where、limit、orderby这些都是Builder
	toString() string
	getPrev() Builder
}

type LimitBuilder struct {
	sb   strings.Builder
	prev Builder //前面的Builder
}

func newLimitBuilder(offset, n int) *LimitBuilder {
	builder := &LimitBuilder{}
	//通过strings.Builder实现高效的字符串连接
	builder.sb.WriteString(" limit ")
	builder.sb.WriteString(strconv.Itoa(offset))
	builder.sb.WriteString(",")
	builder.sb.WriteString(strconv.Itoa(n))
	return builder
}

func (self *LimitBuilder) toString() string {
	return self.sb.String()
}

func (self *LimitBuilder) getPrev() Builder {
	return self.prev
}

func (self *LimitBuilder) ToString() string {
	var root Builder
	root = self
	for root.getPrev() != nil { //找到最前面的root Builder
		root = root.getPrev()
	}
	return root.toString()
}

type OrderByBuilder struct {
	sb    strings.Builder
	limit *LimitBuilder
	prev  Builder
}

func newOrderByBuilder(column string) *OrderByBuilder {
	builder := &OrderByBuilder{}
	builder.sb.WriteString(" order by ")
	builder.sb.WriteString(column)
	return builder
}

func (self *OrderByBuilder) getPrev() Builder {
	return self.prev
}

func (self *OrderByBuilder) ToString() string {
	var root Builder
	root = self
	for root.getPrev() != nil {
		root = root.getPrev()
	}
	return root.toString()
}

func (self *OrderByBuilder) Asc() *OrderByBuilder {
	self.sb.WriteString(" asc")
	return self
}

func (self *OrderByBuilder) Desc() *OrderByBuilder {
	self.sb.WriteString(" desc")
	return self
}

func (self *OrderByBuilder) toString() string {
	if self.limit != nil {
		self.sb.WriteString(self.limit.toString())
	}
	return self.sb.String()
}

// orderby后面可以接limit
func (self *OrderByBuilder) Limit(offset, n int) *LimitBuilder {
	limit := newLimitBuilder(offset, n)
	limit.prev = self
	self.limit = limit
	return limit
}

type WhereBuilder struct {
	sb      strings.Builder
	orderby *OrderByBuilder
	limit   *LimitBuilder
	prev    Builder
}

func newWhereBuilder(condition string) *WhereBuilder {
	builder := &WhereBuilder{}
	builder.sb.WriteString(" where ")
	builder.sb.WriteString(condition)
	return builder //sb: where score>30
}

func (self *WhereBuilder) getPrev() Builder {
	return self.prev
}

func (self *WhereBuilder) ToString() string {
	var root Builder
	root = self
	for root.getPrev() != nil {
		root = root.getPrev()
	}
	return root.toString()
}

// where后面可以接order by
func (self *WhereBuilder) OrderBy(column string) *OrderByBuilder {
	orderby := newOrderByBuilder(column)
	self.orderby = orderby
	orderby.prev = self
	return orderby
}

// where后面可以接limit
func (self *WhereBuilder) Limit(offset, n int) *LimitBuilder {
	limit := newLimitBuilder(offset, n)
	limit.prev = self
	self.limit = limit
	return limit
}

// And和Or都是where里的可选部分，它们的地位平等，都返回WhereBuilder
func (self *WhereBuilder) And(condition string) *WhereBuilder {
	self.sb.WriteString(" and ")
	self.sb.WriteString(condition)
	return self
}

// And和Or都是where里的可选部分，它们的地位平等，都返回WhereBuilder
func (self *WhereBuilder) Or(condition string) *WhereBuilder {
	self.sb.WriteString(" or ")
	self.sb.WriteString(condition)
	return self
}

func (self *WhereBuilder) toString() string {
	//递归调用后续Builder的ToString()
	if self.orderby != nil {
		self.sb.WriteString(self.orderby.toString())
	}
	if self.limit != nil {
		self.sb.WriteString(self.limit.toString())
	}
	return self.sb.String()
}

type SelectBuilder struct {
	sb    strings.Builder
	table string
	where *WhereBuilder
}

func NewSelectBuilder(table string) *SelectBuilder {
	builder := &SelectBuilder{
		table: table,
	}
	builder.sb.WriteString("select ")
	return builder
}

// 通过select查询哪几列
func (self *SelectBuilder) Column(columns string) *SelectBuilder {
	self.sb.WriteString(columns)
	self.sb.WriteString(" from ")
	self.sb.WriteString(self.table)
	return self
}

func (self *SelectBuilder) Where(condition string) *WhereBuilder {
	where := newWhereBuilder(condition)
	self.where = where
	where.prev = self
	return where
}

func (self *SelectBuilder) toString() string {
	if self.where != nil {
		self.sb.WriteString(self.where.toString())
	}
	return self.sb.String()
}

func (self *SelectBuilder) getPrev() Builder {
	return nil
}

func (self *SelectBuilder) ToString() string {
	var root Builder
	root = self
	for root.getPrev() != nil {
		root = root.getPrev()
	}
	return root.toString()
}

func main() {
	// testDoubleLink()
	//Where、OrderBy、Limit有没有都不影响调用ToString();Where里的And和Or有没有都不影响调用ToString()
	sql := NewSelectBuilder("student").Column("id,name,city").
		Where("id>0").
		And("city='郑州'").
		Or("city='北京'").
		OrderBy("score").Desc().
		Limit(0, 10).
		ToString()
	fmt.Println(sql)
}
