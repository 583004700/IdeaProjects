package main

import (
	"fmt"
	"math"
	"math/rand"
	"regexp"
	"strconv"

	"gonum.org/v1/gonum/stat"
)

// 一些数学常量
func constant() {
	fmt.Println(math.E)                      //自然对数的底，2.718281828459045
	fmt.Println(math.Pi)                     //圆周率，3.141592653589793
	fmt.Println(math.Phi)                    //黄金分割，长/短，1.618033988749895
	fmt.Println(math.MaxInt)                 //9223372036854775807
	fmt.Println(uint64(math.MaxUint))        //得先把MaxUint转成uint64才能输出，18446744073709551615
	fmt.Println(math.MaxFloat64)             //1.7976931348623157e+308
	fmt.Println(math.SmallestNonzeroFloat64) //最小的非0且正的浮点数，5e-324
}

// NaN
func parseFloat(str string) float64 {
	reg, _ := regexp.Compile("^[0-9]{0,}\\.{0,1}[0-9]{0,}$")
	if reg.Match([]byte(str)) {
		f, _ := strconv.ParseFloat(str, 64)
		fmt.Println(f)
		return f
	} else {
		return math.NaN() //Not a Number
	}
}

// 常用函数
func general() {
	fmt.Println(math.Ceil(1.1))     //向上取整，2
	fmt.Println(math.Floor(-1.9))   //向下取整，-2
	fmt.Println(math.Trunc(-1.9))   //取整数部分，-1
	fmt.Println(math.Floor(1.9))    //向下取整，1
	fmt.Println(math.Trunc(1.9))    //取整数部分，1
	fmt.Println(math.Modf(2.5))     //返回整数部分和小数部分，2  0.5
	fmt.Println(math.Abs(-2.6))     //绝对值，2.6
	fmt.Println(math.Max(4, 8))     //取二者的较大者，8
	fmt.Println(math.Min(4, 8))     //取二者的较小者，4
	fmt.Println(math.Dim(4, 7))     //x-y if x-y>0 else 0，0
	fmt.Println(math.Dim(7, 4))     //3
	fmt.Println(math.Mod(6.5, 3.5)) //x-Trunc(x/y)*y结果的正负号和x相同，3
	fmt.Println(math.Sqrt(9))       //开平方，3
	fmt.Println(math.Cbrt(9))       //开三次方，2.08008
}

// 三角函数
func trigonometric() {
	fmt.Println(math.Sin(1))
	fmt.Println(math.Cos(1))
	fmt.Println(math.Tan(1))
	fmt.Println(math.Tanh(1))
}

// 对数和指数
func pow() {
	fmt.Println(math.Log(5))     //自然对数，1.60943
	fmt.Println(math.Log1p(4))   //等价于Log(1+p)，确保结果为正数，1.60943
	fmt.Println(math.Log10(100)) //以10为底数，取对数，2
	fmt.Println(math.Log2(8))    //以2为底数，取对数，3
	fmt.Println(math.Pow(3, 2))  //x^y，9
	fmt.Println(math.Pow10(2))   //10^x，100
	fmt.Println(math.Exp(2))     //e^x，7.389
}

func random() {
	//创建一个Rand
	source := rand.NewSource(1) //seed相同的情况下，随机数生成器产生的数列是相同的
	rander := rand.New(source)
	for i := 0; i < 10; i++ {
		fmt.Printf("%d ", rander.Intn(100))
	}
	fmt.Println()
	source.Seed(1) //必须重置一下Seed
	rander2 := rand.New(source)
	for i := 0; i < 10; i++ {
		fmt.Printf("%d ", rander2.Intn(100))
	}
	fmt.Println()

	//使用全局Rand
	rand.Seed(1)                //如果对两次运行没有一致性要求，可以不设seed
	fmt.Println(rand.Int())     //随机生成一个整数
	fmt.Println(rand.Float32()) //随机生成一个浮点数
	fmt.Println(rand.Intn(100)) //100以内的随机整数，[0,100)
	fmt.Println(rand.Perm(100)) //把[0,100)上的整数随机打乱
	arr := []int{1, 2, 3, 4, 5, 6, 7, 8, 9}
	rand.Shuffle(len(arr), func(i, j int) { //随机打乱一个给定的slice
		arr[i], arr[j] = arr[j], arr[i]
	})
	fmt.Println(arr)
}

// gonum是用纯go语言(带一些汇编)开发的数值算法库（非标准库），包含统计、矩阵、数值优化
func gonum() {
	arr := []float64{1, 2, 3, 4, 5}
	brr := []float64{6, 7, 8, 9, 10}
	fmt.Println(stat.Mean(arr, nil))            //均值
	fmt.Println(stat.Variance(arr, nil))        //方差
	fmt.Println(stat.Covariance(arr, brr, nil)) //协方差
	fmt.Println(stat.CrossEntropy(arr, brr))    //交叉熵
}

func main() {
	constant()
	fmt.Println()
	f := parseFloat("435..34")
	if math.IsNaN(f) {
		fmt.Println("not a number")
	}
	fmt.Println()
	general()
	fmt.Println()
	trigonometric()
	fmt.Println()
	pow()
	fmt.Println()
	random()
	fmt.Println()
	gonum()

}

//go run lib/math/main.go
