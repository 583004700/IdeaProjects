package test

import (
	"encoding/json"
	"fmt"
	"golearn/day007/feature_extractor/common"
	"golearn/day007/feature_extractor/extractor"
	"log"
	"os"
	"testing"
	"time"
)

var (
	loc *time.Location
	fe  *extractor.FeatureExtractor
)

const (
	TIME_FMT = "2006-01-02 15:04:05"
	DATE_FMT = "20060102"
)

func init() {
	loc, _ = time.LoadLocation("Asia/Shanghai")
	fe = new(extractor.FeatureExtractor)
	if err := fe.Init("/Users/zhangchaoyang/go_project/golearn/day007/feature_extractor/conf/feature_extractor.json"); err != nil {
		log.Fatal(err)
	}
}

func TestSerializeConf(t *testing.T) {
	conf1 := common.FeatureConfig{
		Id:         1,
		Path:       "Seller.Name",
		Discretize: "uniq",
		Hash:       "murmur",
	}
	conf2 := common.FeatureConfig{
		Id:         2,
		Path:       "Seller.Age",
		Discretize: "bin 18,25,30,35,40,50,60",
		Hash:       "farm",
	}
	confList := common.FeatureConfigList{&conf1, &conf2}
	fout, err := os.OpenFile("/Users/zhangchaoyang/go_project/golearn/day007/feature_extractor/conf/test.json", os.O_CREATE|os.O_TRUNC|os.O_WRONLY, 0666)
	if err != nil {
		fmt.Println(err)
		t.Fail()
	} else {
		defer fout.Close()
		bs, err := json.MarshalIndent(confList, "", "  ")
		if err != nil {
			fmt.Println(err)
			t.Fail()
		} else {
			fout.Write(bs)
		}
	}
}

func TestFeatureExtractor(t *testing.T) {
	tm, err := time.ParseInLocation(TIME_FMT, "2015-03-27 10:00:00", loc)
	if err != nil {
		fmt.Println(err)
		t.Fail()
	}
	product := &common.Product{
		Id:       5346456,
		Name:     "iPhone",
		Sales:    74575372,
		Feedback: 0.94,
		Seller: &common.User{
			Name:   "Apple",
			Age:    34,
			Gender: 1,
			Address: &common.Location{
				Province: "北京",
				City:     "北京",
			},
		},
		OnShelfTime: tm,
		Tags:        []string{"电子产品", "手机"},
	}
	features := fe.Extract(product)
	fmt.Println("final result", features)

	product.Tags = nil
	features = fe.Extract(product)
	fmt.Println("final result", features)

	product.Id = -5
	features = fe.Extract(product)
	fmt.Println("final result", features)
}

func BenchmarkFeatureExtractor(b *testing.B) {
	tm, err := time.ParseInLocation(TIME_FMT, "2015-03-27 10:00:00", loc)
	if err != nil {
		fmt.Println(err)
		b.Fail()
	}
	product := &common.Product{
		Id:       5346456,
		Name:     "iPhone",
		Sales:    74575372,
		Feedback: 0.94,
		Seller: &common.User{
			Name:   "Apple",
			Age:    34,
			Gender: 1,
			Address: &common.Location{
				Province: "北京",
				City:     "北京",
			},
		},
		OnShelfTime: tm,
		Tags:        []string{"电子产品", "手机"},
	}
	for i := 0; i < b.N; i++ {
		fe.Extract(product)
	}
}

//go test -v feature_extractor/test/conf_ser_test.go -run=FeatureExtractor
//go test feature_extractor/test/conf_ser_test.go -bench=FeatureExtractor -run=^$ -benchtime=5s -benchmem -cpuprofile=data/cpu.prof -memprofile=data/mem.prof
//go tool pprof data/cpu.prof
//go tool pprof data/mem.prof
//go tool pprof -http=:8080 data/cpu.prof
//go tool pprof -http=:8080 data/mem.prof
