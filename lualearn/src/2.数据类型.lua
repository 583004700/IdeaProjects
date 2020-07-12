--nil �����򵥣�ֻ��ֵnil���ڸ��࣬��ʾһ����Чֵ�����������ʽ���൱��false����
--boolean ��������ֵ��false��true��
--number	��ʾ˫�������͵�ʵ������
--string	�ַ�����һ��˫���Ż���������ʾ
--function	�� C �� Lua ��д�ĺ���
--userdata	��ʾ����洢�ڱ����е�C���ݽṹ
--thread	��ʾִ�еĶ�����·������ִ��Эͬ����

--[[table	Lua �еı�table����ʵ��һ��"��������"��associative arrays��
��������������������ֻ������ַ������� Lua �table �Ĵ�����ͨ��"������ʽ"����ɣ�
��򵥹�����ʽ��{}����������һ���ձ�]]

print(type("Hello world"))      --> string
print(type(10.4 * 3))             --> number
print(type(print))              --> function
print(type(type))               --> function
print(type(true))               --> boolean
print(type(nil))                --> nil
print(type(type(X)))            --> string

--[[nil���գ�
nil ���ͱ�ʾһ��û���κ���Чֵ����ֻ��һ��ֵ nil�������ӡһ��û�и�ֵ�ı�����������һ�� nil ֵ��]]

print(type(a))

--[[����ȫ�ֱ����� table��nil ����һ��"ɾ��"���ã���ȫ�ֱ������� table ����ı�����һ�� nil ֵ����ͬ�ڰ�����ɾ����ִ����������֪��]]
tab1 = { key1 = "val1", key2 = "val2", "val3" }
for k, v in pairs(tab1) do
    print(k .. " - " .. v)
end

tab1.key1 = nil
for k, v in pairs(tab1) do
    print(k .. " - " .. v)
end

--[[
boolean��������
boolean ����ֻ��������ѡֵ��true���棩 �� false���٣���Lua �� false �� nil ������"��"�������Ķ�Ϊ"��":]]
print(type(true))
print(type(false))
print(type(nil))

if type(false) or type(nil) then
    print("false and nil are false!")
else
    print("other is true!")
end

--[[number�����֣�
Lua Ĭ��ֻ��һ�� number ���� -- double��˫���ȣ����ͣ�Ĭ�����Ϳ����޸� luaconf.h ��Ķ��壩�����¼���д������������ number ���ͣ�]]
print(type(2))
print(type(2.2))
print(type(0.2))
print(type(2e+1))
print(type(0.2e-1))
print(type(7.8263692594256e-06))

--[[
string���ַ�����
�ַ�����һ��˫���Ż���������ʾ��]]
string1 = "this is string1"
string2 = 'this is string2'
--Ҳ������ 2 �������� "[[]]" ����ʾ"һ��"�ַ�����
html = [[
<html>
<head></head>
<body>
    <a href="//www.w3cschool.cn/">w3cschoolW3Cschool�̳�</a>
</body>
</html>
]]
print(html)

--�ڶ�һ�������ַ����Ͻ�����������ʱ��Lua �᳢�Խ���������ַ���ת��һ������:
print("2" + 6)

print("2" + "6")

print("2 + 6")

print("-2e2" * "6")

--print("error" + 1)
--���ϴ�����"error" + 1ִ�б����ˣ��ַ�������ʹ�õ��� .. ���磺
print("a" .. 'b')
print(157 .. 428)
--ʹ�� # �������ַ����ĳ��ȣ������ַ���ǰ�棬����ʵ����
len = "www.w3cschool.cn"
print(#len)
print(#"www.w3cschool.cn")

--[[table����
�� Lua �table �Ĵ�����ͨ��"������ʽ"����ɣ���򵥹�����ʽ��{}����������һ���ձ�Ҳ�����ڱ������һЩ���ݣ�ֱ�ӳ�ʼ����:]]
-- ����һ���յ� table
local tbl1 = {}

-- ֱ�ӳ�ʼ��
local tbl2 = { "apple", "pear", "orange", "grape" }
--Lua �еı�table����ʵ��һ��"��������"��associative arrays����������������������ֻ������ַ�����
-- table_test.lua �ű��ļ�
a = {}
a["key"] = "value"
key = 10
a[key] = 22
a[key] = a[key] + 11
for k, v in pairs(a) do
    print(k .. " : " .. v)
end
--��ͬ���������Ե������ 0 ��Ϊ����ĳ�ʼ�������� Lua ����Ĭ�ϳ�ʼ����һ���� 1 ��ʼ��
local tbl = { "apple", "pear", "orange", "grape" }
for key, val in pairs(tbl) do
    print("Key", key)
end
--table ����̶����ȴ�С�������������ʱ table ���Ȼ��Զ�������û��ʼ�� table ���� nil��
a3 = {}
for i = 1, 10 do
    a3[i] = i
end
a3["key"] = "val"
print(a3["key"])
print(a3["none"])
--[[
function��������
�� Lua �У������Ǳ�������"��һ��ֵ��First-Class Value��"���������Դ��ڱ�����:]]
function factorial1(n)
    if n == 0 then
        return 1
    else
        return n * factorial1(n - 1)
    end
end
print(factorial1(5))
factorial2 = factorial1
print(factorial2(5))
--function ����������������anonymous function���ķ�ʽͨ����������:
function anonymous(tab, fun)
    for k, v in pairs(tab) do
        print(fun(k, v))
    end
end
tab = { key1 = "val1", key2 = "val2" }
anonymous(tab, function(key, val)
    return key .. " = " .. val
end)
--[[thread���̣߳�
�� Lua �����Ҫ���߳���Эͬ����coroutine���������̣߳�thread����࣬ӵ���Լ�������ջ���ֲ�������ָ��ָ�룬���Ը�����Эͬ������ȫ�ֱ����������󲿷ֶ�����

�̸߳�Э�̵������߳̿���ͬʱ������У���Э������ʱ��ֻ������һ�������Ҵ�������״̬��Э��ֻ�б�����suspend��ʱ�Ż���ͣ��]]

--[[userdata���Զ������ͣ�
userdata ��һ���û��Զ������ݣ����ڱ�ʾһ����Ӧ�ó���� C/C++ ���Կ������������ͣ����Խ����� C/C++ �������������͵����ݣ�ͨ���� struct �� ָ�룩�洢�� Lua �����е��á�]]

