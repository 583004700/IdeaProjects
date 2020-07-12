package binchunk

const (
	//lua的签名
	LUA_SIGNATURE = "\x1bLua"
	//lua版本
	LUAC_VERSION = 0x53
	//lua格式，官方实现是0
	LUAC_FORMAT = 0
	//lua_data,为了进一步检测文件是否已损坏 1993是lua发布的年份
	LUAC_DATA = "\x19\x93\r\n\x1a\n"
	//cint类型所占的长度
	CINT_SIZE = 4
	//size_t类型所占的长度
	CSIZET_SIZE = 8
	//lua虚拟机指令所占的长度
	INSTRUCTION_SIZE = 4
	//lua整数所占的长度
	LUA_INTEGER_SIZE = 8
	//lua浮点数所占的长度
	LUA_NUMBER_SIZE = 8
	//用来判断大小端方式是否匹配
	LUAC_INT = 0x5678
	//检测浮点数格式
	LUAC_NUM = 370.5
)

const (
	//nil 不存储
	TAG_NIL = 0x00
	//boolean 字节（0，1）
	TAG_BOOLEAN = 0x01
	//number  lua浮点数
	TAG_NUMBER = 0x03
	//integer lua整数
	TAG_INTEGER = 0x13
	//string  短字符串
	TAG_SHORT_STR = 0x04
	//string  长字符串
	TAG_LONG_STR = 0x14
)

type binaryChunk struct {
	header                  //头部
	sizeUpvalues byte       //主函数upvalue数量
	mainFunc     *Prototype //主函数原型
}

type header struct {
	signature       [4]byte
	version         byte
	format          byte
	luacData        [6]byte
	cintSize        byte
	sizetSize       byte
	instructionSize byte
	luaIntegerSize  byte
	luaNumberSize   byte
	luacInt         int64
	luacNum         float64
}

// function prototype
type Prototype struct {
	//文件的来源，@开头表示是从源文件编译而来，后面跟文件名，为了避免重复，只有main函数才有值
	Source string // debug
	//起始行号
	LineDefined uint32
	//终止行号
	//如果是普通函数，起止行号都大于0，如果是主函数，起止行号都等于0
	LastLineDefined uint32
	//固定参数个数，相对于变长参数而言的，lua生成的主函数固定参数个数为0
	NumParams byte
	//是否有变长参数，1有 0无 mian函数有变长参数
	IsVararg byte
	//寄存器数量，lua编译器会计算好运行函数需要的寄存器数量
	MaxStackSize byte
	//指令表，一个指令占4个字节
	Code []uint32
	//常量表，每个常量都是1字节tag开头，用来标识后续存储的是哪种类型的常量
	Constants []interface{}
	//Upvalue表,每个元素占用两个字节
	Upvalues []Upvalue
	//子函数原型表
	Protos []*Prototype
	//行号表,行号与指令表中的指令一一对应,分别记录每条指令在源代码中对应的行号
	LineInfo []uint32 // debug
	//局部变量表,用于记录局部变量名
	LocVars []LocVar // debug
	//upvalue名列表
	UpvalueNames []string // debug
}

type Upvalue struct {
	Instack byte
	Idx     byte
}

type LocVar struct {
	//局部变量名
	VarName string
	//起止索引
	StartPC uint32
	EndPC   uint32
}

func Undump(data []byte) *Prototype {
	reader := &reader{data}
	reader.checkHeader()
	reader.readByte() // size_upvalues
	return reader.readProto("")
}
