--[[
Lua table(��)
table �� Lua ��һ�����ݽṹ�����������Ǵ�����ͬ���������ͣ��磺���֡��ֵ�ȡ�

Lua table ʹ�ù��������飬��������������͵�ֵ��������������������ֵ������ nil��

Lua table �ǲ��̶���С�ģ�����Ը����Լ���Ҫ�������ݡ�

LuaҲ��ͨ��table�����ģ�飨module��������package���Ͷ���Object���ġ� ����string.format��ʾʹ��"format"������table string��]]

--[[table(��)�Ĺ���
�������Ǵ����ͳ�ʼ����ı��ʽ������Lua���еĹ���ǿ��Ķ�������򵥵Ĺ��캯����{}����������һ���ձ�����ֱ�ӳ�ʼ������:]]

-- ��ʼ����
mytable = {}

-- ָ��ֵ
mytable[1] = "Lua"

-- �Ƴ�����
mytable = nil
-- lua �������ջ��ͷ��ڴ�
--[[������Ϊ table a ������Ԫ�أ�Ȼ�� a ��ֵ�� b���� a �� b ��ָ��ͬһ���ڴ档��� a ����Ϊ nil ���� b ͬ���ܷ��� table ��Ԫ�ء����û��ָ���ı���ָ��a��Lua���������ջ��ƻ��������Ӧ���ڴ档

����ʵ����ʾ�����ϵ����������]]

-- �򵥵� table
mytable = {}
print("mytable �������� ", type(mytable))

mytable[1] = "Lua"
mytable["wow"] = "�޸�ǰ"
print("mytable ����Ϊ 1 ��Ԫ���� ", mytable[1])
print("mytable ����Ϊ wow ��Ԫ���� ", mytable["wow"])

-- alternatetable��mytable����ָͬһ�� table
alternatetable = mytable

print("alternatetable ����Ϊ 1 ��Ԫ���� ", alternatetable[1])
print("mytable ����Ϊ wow ��Ԫ���� ", alternatetable["wow"])

alternatetable["wow"] = "�޸ĺ�"

print("mytable ����Ϊ wow ��Ԫ���� ", mytable["wow"])

-- �ͷű���
alternatetable = nil
print("alternatetable �� ", alternatetable)

-- mytable ��Ȼ���Է���
print("mytable ����Ϊ wow ��Ԫ���� ", mytable["wow"])

mytable = nil
print("mytable �� ", mytable)

--Table ����
--�����г��� Table �������õķ�����
--
--���	���� & ��;
--1	table.concat (table [, step [, start [, end]]]):
--concat��concatenate(����, ����)����д. table.concat()�����г�������ָ��table�����鲿�ִ�startλ�õ�endλ�õ�����Ԫ��, Ԫ�ؼ���ָ���ķָ���(sep)������
--
--2	table.insert (table, [pos,] value):
--��table�����鲿��ָ��λ��(pos)����ֵΪvalue��һ��Ԫ��. pos������ѡ, Ĭ��Ϊ���鲿��ĩβ.
--
--3	table.maxn (table)
--ָ��table����������keyֵ������keyֵ. ���������keyֵΪ������Ԫ��, �򷵻�0��(Lua5.2֮��÷����Ѿ���������,����ʹ�����Զ��庯��ʵ��)
--
--4	table.remove (table [, pos])
--����table���鲿��λ��posλ�õ�Ԫ��. ����Ԫ�ػᱻǰ��. pos������ѡ, Ĭ��Ϊtable����, �������һ��Ԫ��ɾ��
--
--5	table.sort (table [, comp])
--�Ը�����table������������
--
--�����������������⼸��������ʵ����
--[[Table ����
���ǿ���ʹ�� concat() �������������� table:]]

fruits = { "banana", "orange", "apple" }
-- ���� table ���Ӻ���ַ���
print("���Ӻ���ַ��� ", table.concat(fruits))

-- ָ�������ַ�
print("���Ӻ���ַ��� ", table.concat(fruits, ", "))

-- ָ������������ table
print("���Ӻ���ַ��� ", table.concat(fruits, ", ", 2, 3))
--[[������Ƴ�
����ʵ����ʾ�� table �Ĳ�����Ƴ�����:]]

fruits = { "banana", "orange", "apple" }

-- ��ĩβ����
table.insert(fruits, "mango")
print("����Ϊ 4 ��Ԫ��Ϊ ", fruits[4])

-- ������Ϊ 2 �ļ�������
table.insert(fruits, 2, "grapes")
print("����Ϊ 2 ��Ԫ��Ϊ ", fruits[2])

print("���һ��Ԫ��Ϊ ", fruits[5])
table.remove(fruits)
print("�Ƴ������һ��Ԫ��Ϊ ", fruits[5])

--[[Table ����
����ʵ����ʾ�� sort() ������ʹ�ã����ڶ� Table ��������]]

fruits = { "banana", "orange", "apple", "grapes" }
print("����ǰ")
for k, v in ipairs(fruits) do
    print(k, v)
end

table.sort(fruits)
print("�����")
for k, v in ipairs(fruits) do
    print(k, v)
end

--[[Table ���ֵ
table.maxn �� Lua5.2 ֮��÷����Ѿ��������ˣ����Ƕ����� table_maxn ������ʵ�֡�

����ʵ����ʾ����λ�ȡ table �е����ֵ��]]

function table_maxn(t)
    local mn = 0
    for k, v in pairs(t) do
        if mn < k then
            mn = k
        end
    end
    return mn
end
tbl = { [1] = "a", [2] = "b", [3] = "c", [26] = "z" }
print("tbl ���� ", #tbl)
print("tbl ���ֵ ", table_maxn(tbl))