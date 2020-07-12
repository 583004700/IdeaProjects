--[[
Lua Ԫ��(Metatable)
�� Lua table �����ǿ��Է��ʶ�Ӧ��key���õ�valueֵ������ȴ�޷������� table ���в�����

��� Lua �ṩ��Ԫ��(Metatable)���������Ǹı�table����Ϊ��ÿ����Ϊ�����˶�Ӧ��Ԫ������

���磬ʹ��Ԫ�����ǿ��Զ���Lua��μ�������table����Ӳ���a+b��

��Lua��ͼ��������������ʱ���ȼ������֮һ�Ƿ���Ԫ��֮�����Ƿ���һ����"__add"���ֶΣ����ҵ�������ö�Ӧ��ֵ��"__add"�ȼ�ʱ�ֶΣ����Ӧ��ֵ��������һ����������table������"Ԫ����"��

����������Ҫ�ĺ���������Ԫ��

setmetatable(table,metatable): ��ָ��table����Ԫ��(metatable)�����Ԫ��(metatable)�д���__metatable��ֵ��setmetatable��ʧ�� ��
getmetatable(table): ���ض����Ԫ��(metatable)��]]

--����ʵ����ʾ����ζ�ָ���ı�����Ԫ��

mytable = {}                          -- ��ͨ��
mymetatable = {}                      -- Ԫ��
setmetatable(mytable, mymetatable)     -- �� mymetatable ��Ϊ mytable ��Ԫ��
--���ϴ���Ҳ����ֱ��д��һ�У�
mytable = setmetatable({}, {})

--����Ϊ���ض���Ԫ��
getmetatable(mytable)                 -- ��ط���mymetatable

--[[
__index
Ԫ����
����
metatable
��õļ���

����ͨ����������
table
��ʱ����������û��ֵ����ôLua�ͻ�Ѱ�Ҹ�table��metatable���ٶ���metatable���е�__index
�������__index����һ�����Lua���ڱ���в�����Ӧ�ļ���
���ǿ�����ʹ��
lua
������뽻��ģʽ�鿴��]]
other = { foo = 3, f = function(v) print(v) end }
t = setmetatable({}, { __index = other })
print(t.foo)
print(t.bar)
print(t.f("fffff"))

--[[���__index����һ�������Ļ���Lua�ͻ�����Ǹ�������table�ͼ�����Ϊ�������ݸ�������

__index Ԫ�����鿴����Ԫ���Ƿ���ڣ���������ڣ����ؽ��Ϊ nil������������� __index ���ؽ����]]

mytable = setmetatable({ key1 = "value1" }, {
    __index = function(mytable, key)
        if key == "key2" then
            return "metatablevalue"
        else
            return nil
        end
    end
})

print(mytable.key1, mytable.key2)

--[[ʵ��������

mytable ��ֵΪ {key1 = "value1"}��

mytable ������Ԫ��Ԫ����Ϊ __index��

��mytable���в��� key1������ҵ������ظ�Ԫ�أ��Ҳ����������

��mytable���в��� key2������ҵ������ظ�Ԫ�أ��Ҳ����������

�ж�Ԫ����û��__index���������__index������һ������������øú�����

Ԫ�����в鿴�Ƿ��� "key2" ���Ĳ�����mytable.key2�����ã���������� "key2" �������� "metatablevalue"�����򷵻� mytable ��Ӧ�ļ�ֵ��
���ǿ��Խ����ϴ����д�ɣ�]]

mytable = setmetatable({ key1 = "value1" }, { __index = { key2 = "metatablevalue" } })
print(mytable.key1, mytable.key2)

--[[__newindex Ԫ����
__newindex Ԫ���������Ա���£�__index�������Ա���� ��

��������һ��ȱ�ٵ�������ֵ���������ͻ����__newindex Ԫ��������������������������������и�ֵ������

����ʵ����ʾ�� __newindex Ԫ������Ӧ�ã�]]

mymetatable = {}
mytable = setmetatable({ key1 = "value1" }, { __newindex = mymetatable })

print(mytable.key1)

mytable.newkey = "��ֵ2"
print(mytable.newkey, mymetatable.newkey)

mytable.key1 = "��ֵ1"
print(mytable.key1, mymetatable.newkey1)

--[[����ʵ���б�������Ԫ���� __newindex���ڶ�����������newkey����ֵʱ��mytable.newkey = "��ֵ2"���������Ԫ�������������и�ֵ����������Ѵ��ڵ���������key1���������и�ֵ����������Ԫ���� __newindex��

����ʵ��ʹ���� rawset ���������±�]]

mytable = setmetatable({ key1 = "value1" }, {
    __newindex = function(mytable, key, value)
        rawset(mytable, key, "\"" .. value .. "\"")

    end
})

mytable.key1 = "new value"
mytable.key2 = 4

print(mytable.key1, mytable.key2)

--[[Ϊ����Ӳ�����
����ʵ����ʾ��������Ӳ�����]]

-- ����������ֵ��table.maxn��Lua5.2���ϰ汾�����޷�ʹ��
-- �Զ������������ֵ���� table_maxn
function table_maxn(t)
    local mn = 0
    for k, v in pairs(t) do
        if mn < k then
            mn = k
        end
    end
    return mn
end

-- ������Ӳ���
mytable = setmetatable({ 1, 2, 3 }, {
    __add = function(mytable, newtable)
        for i = 1, table_maxn(newtable) do
            table.insert(mytable, table_maxn(mytable) + 1, newtable[i])
        end
        return mytable
    end
})

secondtable = { 4, 5, 6 }

mytable = mytable + secondtable
for k, v in ipairs(mytable) do
    print(k, v)
end

--[[
__add ��������Ԫ���У���������Ӳ����� ���ж�Ӧ�Ĳ����б����£�

ģʽ	����
__add	��Ӧ������� '+'.
__sub	��Ӧ������� '-'.
__mul	��Ӧ������� '*'.
__div	��Ӧ������� '/'.
__mod	��Ӧ������� '%'.
__unm	��Ӧ������� '-'.
__concat	��Ӧ������� '..'.
__eq	��Ӧ������� '=='.
__lt	��Ӧ������� '<'.
__le	��Ӧ������� '<='.]]

--[[__call Ԫ����
__call Ԫ������ Lua ����һ��ֵʱ���á�����ʵ����ʾ�˼������Ԫ�صĺͣ�]]

-- ����������ֵ��table.maxn��Lua5.2���ϰ汾�����޷�ʹ��
-- �Զ������������ֵ���� table_maxn
function table_maxn(t)
    local mn = 0
    for k, v in pairs(t) do
        if mn < k then
            mn = k
        end
    end
    return mn
end

-- ����Ԫ����__call
mytable = setmetatable({10}, {
    __call = function(mytable, newtable)
        sum = 0
        for i = 1, table_maxn(mytable) do
            sum = sum + mytable[i]
        end
        for i = 1, table_maxn(newtable) do
            sum = sum + newtable[i]
        end
        return sum
    end
})

newtable = {10,20,30}
print(mytable(newtable))

--[[__tostring Ԫ����
__tostring Ԫ���������޸ı�������Ϊ������ʵ�������Զ����˱��������ݣ�]]

mytable = setmetatable({ 10, 20, 30 }, {
    __tostring = function(mytable)
        sum = 0
        for k, v in pairs(mytable) do
            sum = sum + v
        end
        return "������Ԫ�صĺ�Ϊ " .. sum
    end
})
print(mytable)
