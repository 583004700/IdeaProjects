package aliyun_test

import (
	"os"
	"testing"

	"gitee.com/go-course/cloud-station-g7/store"
	"gitee.com/go-course/cloud-station-g7/store/aliyun"
	"github.com/stretchr/testify/assert"
)

var (
	uploader store.Uploader
)

var (
	AccessKey    = os.Getenv("ALI_AK")
	AccessSecret = os.Getenv("ALI_SK")
	OssEndpoint  = os.Getenv("ALI_OSS_ENDPOINT")
	BucketName   = os.Getenv("ALI_BUCKET_NAME")
)

// Aliyun Oss Store Upload测试用例
func TestUpload(t *testing.T) {
	// 使用 assert 编写测试用例的断言语句
	// 通过New 获取一个断言实例
	should := assert.New(t)

	err := uploader.Upload(BucketName, "test.txt", "store_test.go")
	// 如果你不用断言库, if err == nil {}
	// 封装: assert, 他把 二个对象的比较(断言) 封装成了方法
	// should.Equal(BucketName, "devcloud-station")
	// BucketName 需要被断言的对象, 期望的值: devcloud-station， 比较动作: Equal
	if should.NoError(err) {
		// 没有Error 开启下一个步骤
		t.Log("upload ok")
	}
}

func TestUploadError(t *testing.T) {
	// 使用 assert 编写测试用例的断言语句
	// 通过New 获取一个断言实例
	should := assert.New(t)

	err := uploader.Upload(BucketName, "test.txt", "store_testxxx.go")
	should.Error(err, "open store_testxxx.go: The system cannot find the file specified.")
}

// 通过init 编写 uploader 实例化逻辑
func init() {
	ali, err := aliyun.NewDefaultAliOssStore()
	if err != nil {
		panic(err)
	}
	uploader = ali
}
