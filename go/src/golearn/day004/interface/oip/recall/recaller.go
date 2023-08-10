package recall

import "golearn/day004/interface/oip/common"

type Recaller interface {
	Recall(n int) []*common.Product //生成一批推荐候选集
	Name() string
}
