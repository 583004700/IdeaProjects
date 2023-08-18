package aws

import "fmt"

func NewAwsOssStore() *AwsOssStore {
	return &AwsOssStore{}
}

type AwsOssStore struct {
}

func (s *AwsOssStore) Upload(bucketName string, objectKey string, fileName string) error {
	fmt.Println("not impl aws oss storage")
	return nil
}
