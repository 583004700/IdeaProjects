--Lua while ѭ��
--[[
Lua ��������� while ѭ��������ж�����Ϊ true ʱ���ظ�ִ��ѭ������䡣
�﷨
Lua ��������� while ѭ���﷨��
while(condition)
do
   statements
end]]
a = 10
while (a < 20)
do
    print("a ��ֵΪ:", a)
    a = a + 1
end

--[[
Lua ѭ�� Lua ѭ��

Lua ��������� for ѭ���������ظ�ִ��ָ����䣬�ظ��������� for ����п��ơ�

Lua ��������� for����������ࣺ��

��ֵforѭ��
����forѭ��
]]
--[[��ֵforѭ��
Lua �����������ֵforѭ���﷨��ʽ:

for var=exp1,exp2,exp3 do
    <ִ����>
end
var��exp1�仯��exp2��ÿ�α仯��exp3Ϊ��������var����ִ��һ��"ִ����"��exp3�ǿ�ѡ�ģ������ָ����Ĭ��Ϊ1��
]]
--for i=1,f(x) do
--    print(i)
--end

for i = 10, 1, -1 do
    print(i)
end

--[[for���������ʽ��ѭ����ʼǰһ������ֵ���Ժ��ٽ�����ֵ�����������f(x)ֻ����ѭ����ʼǰִ��һ�Σ��������ں����ѭ���С�

��֤����:]]

function f(x)
    print("function")
    return x * 2
end
for i = 1, f(5) do
    print(i)
end

--[[����forѭ��
����forѭ��ͨ��һ����������������������ֵ������java�е�foreach��䡣

Lua ��������з���forѭ���﷨��ʽ:
--��ӡ����a������ֵ
for i,v in ipairs(a)
	do print(v)
end
i����������ֵ��v�Ƕ�Ӧ����������Ԫ��ֵ��ipairs��Lua�ṩ��һ�������������������������顣]]
days = { "Suanday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" }
for i, v in ipairs(days) do
    print(v)
end

--[[Lua repeat...until ѭ��
Lua ѭ�� Lua ѭ��

Lua ��������� repeat...until ѭ����䲻ͬ�� for �� whileѭ����for �� whileѭ��d����������ڵ�ǰѭ��ִ�п�ʼʱ�жϣ��� repeat...until ѭ������������ڵ�ǰѭ���������жϡ�

�﷨
Lua ��������� repeat...until ѭ���﷨��ʽ:

repeat
    statements
    while( condition )
        repeat...until ����������,����repeat...until ��ѭ������������Ҫ����һ�Ρ�

statements(ѭ�������) ������һ���������䣬condition(����) ������������ʽ���� condition(����) Ϊ true ʱִ��ѭ������䡣

��condition(����)Ϊ false ʱ��������ǰѭ������ʼ�ű�ִ�н����ŵ���䡣

Lua repeat...until ѭ������ͼ���£�]]
--[ �������� --]
a = 10
--[ ִ��ѭ�� --]
repeat
    print("a��ֵΪ:", a)
    a = a + 1
until (a > 15)

--[[Lua break ���
Lua ѭ�� Lua ѭ��

Lua ������� break ��������ѭ�����У������˳���ǰѭ������䣬����ʼ�ű�ִ�н����ŵ���䡣

�����ʹ��ѭ��Ƕ�ף�break��佫ֹͣ���ڲ�ѭ����ִ�У�����ʼִ�е�����ѭ����䡣

�﷨
Lua ��������� break ����﷨��ʽ:

break]]

--[ ������� --]
a = 10

--[ while ѭ�� --]
while (a < 20)
do
    print("a ��ֵΪ:", a)
    a = a + 1
    if (a > 15)
    then
        --[ ʹ�� break �����ֹѭ�� --]
        break
    end
end