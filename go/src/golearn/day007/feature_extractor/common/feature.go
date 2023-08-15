package common

import (
	"golearn/day007/feature_extractor/transform"
	"time"
)

type Location struct {
	Province string
	City     string
}

type User struct {
	Name    string
	Age     int
	Gender  byte
	Address *Location
}

type Product struct {
	Id          int
	Name        string
	Sales       int       //销量
	Feedback    float32   //好评率
	Seller      *User     //商家
	OnShelfTime time.Time //上架时间
	Tags        []string
}

type FeatureConfig struct {
	Id             int                   `json:"id"`
	Path           string                `json:"path"`
	Discretize     string                `json:"discretize"`
	Hash           string                `json:"hash"`
	DiscretizeFunc transform.Discretizer `json:"-"`
	HashFunc       transform.Transformer `json:"-"`
}

type FeatureConfigList []*FeatureConfig
