--[[Lua 字符串
字符串或串(String)是由数字、字母、下划线组成的一串字符。

Lua 语言中字符串可以使用以下三种方式来表示：

单引号间的一串字符。
双引号间的一串字符。]]
--[[和
--]]
--间的一串字符。


--以上三种方式的字符串实例如下：

string1 = "Lua"
print("\"字符串 1 是\"", string1)
string2 = 'w3cschool.cn'
print("字符串 2 是", string2)

string3 = [["Lua 教程"]]
print("字符串 3 是", string3)

--[[转义字符用于表示不能直接显示的字符，比如后退键，回车键，等。如在字符串转换双引号可以使用 "\""。

所有的转义字符和所对应的意义：

转义字符
意义
ASCII码值（十进制）
\a
响铃(BEL)
007
\b
退格(BS) ，将当前位置移到前一列
008
\f
换页(FF)，将当前位置移到下页开头
012
\n
换行(LF) ，将当前位置移到下一行开头
010
\r
回车(CR) ，将当前位置移到本行开头
013
\t
水平制表(HT) （跳到下一个TAB位置）
009
\v
垂直制表(VT)
011
\\
代表一个反斜线字符''\'
092
\'
代表一个单引号（撇号）字符
039
\"
代表一个双引号字符
034
空字符(NULL)
000
\ddd
1到3位八进制数所代表的任意字符
三位八进制
\xhh
1到2位十六进制所代表的任意字符
二位十六进制]]

--[[
字符串操作
Lua 提供了很多的方法来支持字符串的操作：

序号	方法 & 用途
1	string.upper(argument):
字符串全部转为大写字母。
2	string.lower(argument):
字符串全部转为小写字母。
3	string.gsub(mainString,findString,replaceString,num)
在字符串中替换,mainString为要替换的字符串， findString 为被替换的字符，replaceString 要替换的字符，num 替换次数（可以忽略，则全部替换），如：
        > string.gsub("aaaa","a","z",3);
zzza 3
4	string.find (str, substr, [init, [end]]--[[
)
在一个指定的目标字符串中搜索指定的内容(第三个参数为索引),返回其具体位置。不存在则返回 nil。
> string.find("Hello Lua user", "Lua", 1)
7 9
5	string.reverse(arg)
字符串反转
> string.reverse("Lua")
auL
6	string.format(...)
返回一个类似printf的格式化字符串
> string.format("the value is:%d",4)
the value is:4
7	string.char(arg) 和 string.byte(arg[,int])
char 将整型数字转成字符并连接， byte 转换字符为整数值(可以指定某个字符，默认第一个字符)。
> string.char(97,98,99,100)
abcd


string.byte("ABCD",4)
68
string.byte("ABCD")
65


8	string.len(arg)
计算字符串长度。
string.len("abc")
3
9	string.rep(string, n))
返回字符串string的n个拷贝
> string.rep("abcd",2)
abcdabcd
10	..
链接两个字符串
> print("www.w3cschool"..".cn")
www.w3cschool.cn]]

--[[字符串大小写转换
以下实例演示了如何对字符串大小写进行转换：]]

string1 = "Lua";
print(string.upper(string1))
print(string.lower(string1))

--[[字符串查找与反转
以下实例演示了如何对字符串进行查找与反转操作：]]

string = "Lua Tutorial"
-- 查找字符串
print(string.find(string,"Tutorial"))
reversedString = string.reverse(string)
print("新字符串为",reversedString)

--[[字符串格式化
以下实例演示了如何对字符串进行格式化操作：]]

string1 = "Lua"
string2 = "Tutorial"
number1 = 10
number2 = 20
-- 基本字符串格式化
print(string.format("基本格式化 %s %s",string1,string2))
-- 日期格式化
date = 2; month = 1; year = 2014
print(string.format("日期格式化 %02d/%02d/%03d", date, month, year))
-- 十进制格式化
print(string.format("%.4f",1/3))

--[[字符与整数相互转换
以下实例演示了字符与整数相互转换：]]

-- 字符转换
-- 转换第一个字符
print(string.byte("Lua"))
-- 转换第三个字符
print(string.byte("Lua",3))
-- 转换末尾第一个字符
print(string.byte("Lua",-1))
-- 第二个字符
print(string.byte("Lua",2))
-- 转换末尾第二个字符
print(string.byte("Lua",-2))

-- 整数 ASCII 码转换为字符
print(string.char(97))

--[[
其他常用函数
以下实例演示了其他字符串操作，如计算字符串长度，字符串连接，字符串复制等：]]
string1 = "www."
string2 = "w3cschool"
string3 = ".cn"
-- 使用 .. 进行字符串连接
print("连接字符串",string1..string2..string3)

-- 字符串长度
print("字符串长度 ",string.len(string2))

-- 字符串复制 2 次
repeatedString = string.rep(string2,2)
print(repeatedString)