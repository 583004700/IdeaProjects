package example_test

import (
	"fmt"
	"os"
	"testing"

	"github.com/aliyun/aliyun-oss-go-sdk/oss"
)

var (
	// 全局client实例, 在包加载的时候初始化(init)
	client *oss.Client
)

var (
	AccessKey    = os.Getenv("ALI_AK")
	AccessSecret = os.Getenv("ALI_SK")
	OssEndpoint  = os.Getenv("ALI_OSS_ENDPOINT")
	BucketName   = os.Getenv("ALI_BUCKET_NAME")
)

// 测试阿里云OssSDK BucketList接口
func TestBucketList(t *testing.T) {
	lsRes, err := client.ListBuckets()
	if err != nil {
		t.Log(err)
	}

	for _, bucket := range lsRes.Buckets {
		fmt.Println("Buckets:", bucket.Name)
	}
}

// 测试阿里云OssSDK PutObjectFromFile接口
func TestUploadFile(t *testing.T) {
	bucket, err := client.Bucket(BucketName)
	if err != nil {
		t.Log(err)
	}

	// 上传文件到bucket中
	// 常见我们文件, 需要创建一个文件夹
	// 云商OssServer会根据你的key 路径结构, 自动帮你创建目录
	// objectKey 上传到bucket里面的对象的名称(包含路径)
	// mydir/test.go, oss server会自动创建一个mydir目录， mkdir -pv
	// 把测试当前这个文件 上传到 mydir下
	err = bucket.PutObjectFromFile("mydir/test.go", "oss_test.go")
	if err != nil {
		t.Log(err)
	}
}

// 初始化一个Oss Client, 等下给其他所有测试用例使用
func init() {
	c, err := oss.New(OssEndpoint, AccessKey, AccessSecret)
	fmt.Println(OssEndpoint)
	if err != nil {
		panic(err)
	}
	client = c
}
