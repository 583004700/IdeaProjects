package aliyun

import (
	"fmt"
	"os"

	"gitee.com/go-course/cloud-station-g7/store"
	"github.com/aliyun/aliyun-oss-go-sdk/oss"
)

var (
	// 对象是否实现了接口的约束
	// a string = "abc"
	// _ store.Uploader 我不需要这个变量的值, 我只是做变量类型的判断
	// &AliOssStore{} 这个对象 必须满足 store.Uploader
	// _ store.Uploader = &AliOssStore{} 声明了一个空对象, 只是需要一个地址
	// nil 空指针, nil有没哟类型: 有类型
	// a *AliOssStore = nil   nil是一个AliOssStore 的指针
	// 如何把nil 转化成一个 指定类型的变量
	//    a int = 16
	//    b int64 = int64(a)
	//    (int64类型)(值)
	//	  (*AliOssStore)(nil)
	_ store.Uploader = (*AliOssStore)(nil)
)

type Options struct {
	Endpoint     string
	AccessKey    string
	AccessSecret string
}

func (o *Options) Validate() error {
	if o.Endpoint == "" || o.AccessKey == "" || o.AccessSecret == "" {
		return fmt.Errorf("endpoint, access_key access_secret has one empty")
	}

	return nil
}

func NewDefaultAliOssStore() (*AliOssStore, error) {
	return NewAliOssStore(&Options{
		Endpoint:     os.Getenv("ALI_OSS_ENDPOINT"),
		AccessKey:    os.Getenv("ALI_AK"),
		AccessSecret: os.Getenv("ALI_SK"),
	})
}

// AliOssStore对象的构造函数
func NewAliOssStore(opts *Options) (*AliOssStore, error) {
	// 校验参数
	if err := opts.Validate(); err != nil {
		return nil, err
	}

	c, err := oss.New(opts.Endpoint, opts.AccessKey, opts.AccessSecret)
	if err != nil {
		return nil, err
	}
	return &AliOssStore{
		client:   c,
		listener: NewDefaultProgressListener(),
	}, nil
}

type AliOssStore struct {
	// 阿里云 OSS client, 私有变量, 不运行外部
	client *oss.Client
	// 依赖listener的实现
	listener oss.ProgressListener
}

func (s *AliOssStore) Upload(bucketName string, objectKey string, fileName string) error {
	// 2. 获取bucket对象
	bucket, err := s.client.Bucket(bucketName)
	if err != nil {
		return err
	}

	// 3. 上传文件到该bucket
	// ObjectKey 去掉路径合并到文件名称里面就ok
	if err := bucket.PutObjectFromFile(objectKey, fileName, oss.Progress(s.listener)); err != nil {
		return err
	}

	// 4. 打印下载链接
	downloadURL, err := bucket.SignURL(objectKey, oss.HTTPGet, 60*60*24)
	if err != nil {
		return err
	}
	fmt.Printf("文件下载URL: %s \n", downloadURL)
	fmt.Println("请在1天之内下载.")
	return nil
}
