--Lua while 循环
--[[
Lua 编程语言中 while 循环语句在判断条件为 true 时会重复执行循环体语句。
语法
Lua 编程语言中 while 循环语法：
while(condition)
do
   statements
end]]
a = 10
while (a < 20)
do
    print("a 的值为:", a)
    a = a + 1
end

--[[
Lua 循环 Lua 循环

Lua 编程语言中 for 循环语句可以重复执行指定语句，重复次数可在 for 语句中控制。

Lua 编程语言中 for语句有两大类：：

数值for循环
泛型for循环
]]
--[[数值for循环
Lua 编程语言中数值for循环语法格式:

for var=exp1,exp2,exp3 do
    <执行体>
end
var从exp1变化到exp2，每次变化以exp3为步长递增var，并执行一次"执行体"。exp3是可选的，如果不指定，默认为1。
]]
--for i=1,f(x) do
--    print(i)
--end

for i = 10, 1, -1 do
    print(i)
end

--[[for的三个表达式在循环开始前一次性求值，以后不再进行求值。比如上面的f(x)只会在循环开始前执行一次，其结果用在后面的循环中。

验证如下:]]

function f(x)
    print("function")
    return x * 2
end
for i = 1, f(5) do
    print(i)
end

--[[泛型for循环
泛型for循环通过一个迭代器函数来遍历所有值，类似java中的foreach语句。

Lua 编程语言中泛型for循环语法格式:
--打印数组a的所有值
for i,v in ipairs(a)
	do print(v)
end
i是数组索引值，v是对应索引的数组元素值。ipairs是Lua提供的一个迭代器函数，用来迭代数组。]]
days = { "Suanday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" }
for i, v in ipairs(days) do
    print(v)
end

--[[Lua repeat...until 循环
Lua 循环 Lua 循环

Lua 编程语言中 repeat...until 循环语句不同于 for 和 while循环，for 和 while循环d的条件语句在当前循环执行开始时判断，而 repeat...until 循环的条件语句在当前循环结束后判断。

语法
Lua 编程语言中 repeat...until 循环语法格式:

repeat
    statements
    while( condition )
        repeat...until 是条件后行,所以repeat...until 的循环体里面至少要运行一次。

statements(循环体语句) 可以是一条或多条语句，condition(条件) 可以是任意表达式，在 condition(条件) 为 true 时执行循环体语句。

在condition(条件)为 false 时会跳过当前循环并开始脚本执行紧接着的语句。

Lua repeat...until 循环流程图如下：]]
--[ 变量定义 --]
a = 10
--[ 执行循环 --]
repeat
    print("a的值为:", a)
    a = a + 1
until (a > 15)

--[[Lua break 语句
Lua 循环 Lua 循环

Lua 编程语言 break 语句插入在循环体中，用于退出当前循环或语句，并开始脚本执行紧接着的语句。

如果你使用循环嵌套，break语句将停止最内层循环的执行，并开始执行的外层的循环语句。

语法
Lua 编程语言中 break 语句语法格式:

break]]

--[ 定义变量 --]
a = 10

--[ while 循环 --]
while (a < 20)
do
    print("a 的值为:", a)
    a = a + 1
    if (a > 15)
    then
        --[ 使用 break 语句终止循环 --]
        break
    end
end