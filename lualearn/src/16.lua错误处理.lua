--[[Lua 错误处理
程序运行中错误处理是必要的，在我们进行文件操作，数据转移及web service 调用过程中都会出现不可预期的错误。如果不注重错误信息的处理，就会造成信息泄露，程序无法运行等情况。

任何程序语言中，都需要错误处理。错误类型有：

语法错误
运行错误
语法错误
语法错误通常是由于对程序的组件（如运算符、表达式）使用不当引起的。一个简单的实例如下：]]

--a == 2

--[[运行错误
运行错误是程序可以正常执行，但是会输出报错信息。如下实例由于参数输入错误，程序执行时报错：]]

--function add(a,b)
--    return a+b
--end
--
--add(10)

--[[错误处理
我们可以使用两个函数：assert 和 error 来处理错误。实例如下：]]

--local function add(a,b)
--    assert(type(a) == "number", "a 不是一个数字")
--    assert(type(b) == "number", "b 不是一个数字")
--    return a+b
--end
--add(10)

--[[
error函数
语法格式：

error (message [, level])
功能：终止正在执行的函数，并返回message的内容作为错误信息(error函数永远都不会返回)

通常情况下，error会附加一些错误位置的信息到message头部。

Level参数指示获得错误的位置:

Level=1[默认]：为调用error位置(文件+行号)
Level=2：指出哪个调用error的函数的函数
Level=0:不添加错误位置信息]]

--[[
pcall 和 xpcall、debug
Lua中处理错误，可以使用函数pcall（protected call）来包装需要执行的代码。

pcall接收一个函数和要传递个后者的参数，并执行，执行结果：有错误、无错误；返回值true或者或false, errorinfo。

语法格式如下

if pcall(function_name, ….) then
    -- 没有错误
else
    -- 一些错误
end
简单实例：]]
pcall(function(i)
    print(i)
end, 33)

pcall(function(i)
    print(i)
    error('error..')
end, 33)

function f()
    return false, 2
end
if f() then
    print '1'
else
    print '0'
end

--[[pcall以一种"保护模式"来调用第一个参数，因此pcall可以捕获函数执行中的任何错误。

通常在错误发生时，希望落得更多的调试信息，而不只是发生错误的位置。但pcall返回时，它已经销毁了调用桟的部分内容。

Lua提供了xpcall函数，xpcall接收第二个参数——一个错误处理函数，当错误发生时，Lua会在调用桟展看（unwind）前调用错误处理函数，于是就可以在这个函数中使用debug库来获取关于错误的额外信息了。

debug库提供了两个通用的错误处理函数:
debug.debug：提供一个Lua提示符，让用户来价差错误的原因
debug.traceback：根据调用桟来构建一个扩展的错误消息
        >=xpcall(function(i) print(i) error('error..') end, function() print(debug.traceback()) end, 33) 33 stack traceback: stdin:1: in function [C]: in function 'error' stdin:1: in function [C]: in function 'xpcall' stdin:1: in main chunk [C]: in ? false nil
xpcall 使用实例 2:]]

function myfunction ()
    n = n / nil
end

function myerrorhandler(err)
    print("ERROR:", err)
end

status = xpcall(myfunction, myerrorhandler)
print(status)