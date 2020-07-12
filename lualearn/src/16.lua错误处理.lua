--[[Lua ������
���������д������Ǳ�Ҫ�ģ������ǽ����ļ�����������ת�Ƽ�web service ���ù����ж�����ֲ���Ԥ�ڵĴ��������ע�ش�����Ϣ�Ĵ����ͻ������Ϣй¶�������޷����е������

�κγ��������У�����Ҫ���������������У�

�﷨����
���д���
�﷨����
�﷨����ͨ�������ڶԳ���������������������ʽ��ʹ�ò�������ġ�һ���򵥵�ʵ�����£�]]

--a == 2

--[[���д���
���д����ǳ����������ִ�У����ǻ����������Ϣ������ʵ�����ڲ���������󣬳���ִ��ʱ����]]

--function add(a,b)
--    return a+b
--end
--
--add(10)

--[[������
���ǿ���ʹ������������assert �� error ���������ʵ�����£�]]

--local function add(a,b)
--    assert(type(a) == "number", "a ����һ������")
--    assert(type(b) == "number", "b ����һ������")
--    return a+b
--end
--add(10)

--[[
error����
�﷨��ʽ��

error (message [, level])
���ܣ���ֹ����ִ�еĺ�����������message��������Ϊ������Ϣ(error������Զ�����᷵��)

ͨ������£�error�ḽ��һЩ����λ�õ���Ϣ��messageͷ����

Level����ָʾ��ô����λ��:

Level=1[Ĭ��]��Ϊ����errorλ��(�ļ�+�к�)
Level=2��ָ���ĸ�����error�ĺ����ĺ���
Level=0:����Ӵ���λ����Ϣ]]

--[[
pcall �� xpcall��debug
Lua�д�����󣬿���ʹ�ú���pcall��protected call������װ��Ҫִ�еĴ��롣

pcall����һ��������Ҫ���ݸ����ߵĲ�������ִ�У�ִ�н�����д����޴��󣻷���ֵtrue���߻�false, errorinfo��

�﷨��ʽ����

if pcall(function_name, ��.) then
    -- û�д���
else
    -- һЩ����
end
��ʵ����]]
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

--[[pcall��һ��"����ģʽ"�����õ�һ�����������pcall���Բ�����ִ���е��κδ���

ͨ���ڴ�����ʱ��ϣ����ø���ĵ�����Ϣ������ֻ�Ƿ��������λ�á���pcall����ʱ�����Ѿ������˵��×C�Ĳ������ݡ�

Lua�ṩ��xpcall������xpcall���յڶ�����������һ��������������������ʱ��Lua���ڵ��×Cչ����unwind��ǰ���ô������������ǾͿ��������������ʹ��debug������ȡ���ڴ���Ķ�����Ϣ�ˡ�

debug���ṩ������ͨ�õĴ�������:
debug.debug���ṩһ��Lua��ʾ�������û����۲�����ԭ��
debug.traceback�����ݵ��×C������һ����չ�Ĵ�����Ϣ
        >=xpcall(function(i) print(i) error('error..') end, function() print(debug.traceback()) end, 33) 33 stack traceback: stdin:1: in function [C]: in function 'error' stdin:1: in function [C]: in function 'xpcall' stdin:1: in main chunk [C]: in ? false nil
xpcall ʹ��ʵ�� 2:]]

function myfunction ()
    n = n / nil
end

function myerrorhandler(err)
    print("ERROR:", err)
end

status = xpcall(myfunction, myerrorhandler)
print(status)