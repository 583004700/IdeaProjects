package tx

func NewTxOssStore() *TxOssStore {
	return &TxOssStore{}
}

// TxOssStore{A: "xx"} map nil
// TxOssStore.Add() 操作B的属性,
// 通过构造函数删除的TxOssStore, 很多初始化的逻辑，都集中于构造函数 NewTxOssStore
// TxOssStore{A: "xx"} --> TxOssStore{A: "xx", B: map[string]string{}}
type TxOssStore struct {
	A string
	B map[string]string
}

func (s *TxOssStore) Upload(bucketName string, objectKey string, fileName string) error {
	return nil
}
