--[[
Lua ������
��������iterator����һ�ֶ������ܹ�����������׼ģ��������еĲ��ֻ�ȫ��Ԫ�أ�ÿ��������������������е�ȷ���ĵ�ַ

��Lua�е�������һ��֧��ָ�����͵Ľṹ�������Ա������ϵ�ÿһ��Ԫ�ء�]]
--[[
    ���� for ������
    ���� for ���Լ��ڲ��������������ʵ��������������ֵ������������״̬���������Ʊ�����

    ���� for �������ṩ�˼��ϵ� key/value �ԣ��﷨��ʽ���£�

    for k, v in pairs(t) do
        print(k, v)
    end
    ��������У�k, vΪ�����б�pairs(t)Ϊ���ʽ�б�]]

--�鿴����ʵ��:

array = { "Lua", "Tutorial" }

for key, value in ipairs(array)
do
    print(key, value)
end

--[[
����ʵ��������ʹ���� Lua Ĭ���ṩ�ĵ������� ipairs��

�������ǿ�������for��ִ�й��̣�

���ȣ���ʼ��������in������ʽ��ֵ�����ʽӦ�÷��ط���for��Ҫ������ֵ������������״̬���������Ʊ��������ֵ��ֵһ����������ʽ���صĽ�����������������Զ���nil���㣬������ֻᱻ���ԡ�
�ڶ�����״̬�����Ϳ��Ʊ�����Ϊ�������õ���������ע�⣺����for�ṹ��˵��״̬����û���ô��������ڳ�ʼ��ʱ��ȡ����ֵ�����ݸ�������������
�������������������ص�ֵ���������б�
���ģ�������صĵ�һ��ֵΪnilѭ������������ִ��ѭ���塣
���壬�ص��ڶ����ٴε��õ�������
����Lua�����ǳ���ʹ�ú�����������������ÿ�ε��øú����ͷ��ؼ��ϵ���һ��Ԫ�ء�Lua �ĵ��������������������ͣ�

��״̬�ĵ�����
��״̬�ĵ�����]]

--[[��״̬�ĵ�����
��״̬�ĵ�������ָ�������κ�״̬�ĵ������������ѭ�������ǿ���������״̬���������ⴴ���հ����Ѷ���Ĵ��ۡ�

ÿһ�ε�����������������������������״̬�����Ϳ��Ʊ�������ֵ��Ϊ���������ã�һ����״̬�ĵ�����ֻ����������ֵ���Ի�ȡ��һ��Ԫ�ء�

������״̬�������ĵ��͵ļ򵥵�������ipairs�������������ÿһ��Ԫ�ء�]]

--����ʵ������ʹ����һ���򵥵ĺ�����ʵ�ֵ�������ʵ�� ���� n ��ƽ����
function square(iteratorMaxCount, currentNumber)
    if currentNumber < iteratorMaxCount
    then
        currentNumber = currentNumber + 1
        return currentNumber, currentNumber * currentNumber
    end
end

for i, n in square, 3, 0
do
    print(i, n)
end

--[[
������״̬�����������ı�ѭ�������в���ı��״̬�������͵�ǰ�������±꣨���Ʊ�������ipairs�͵����������ܼ򵥣�������Lua�п�������ʵ�֣�

function iter (a, i)
    i = i + 1
    local v = a[i]
    if v then
        return i, v
    end
end

function ipairs (a)
    return iter, a, 0
end
��Lua����ipairs(a)��ʼѭ��ʱ������ȡ����ֵ����������iter��״̬����a�����Ʊ�����ʼֵ0��Ȼ��Lua����iter(a,0)����1,a[1]������a[1]=nil�����ڶ��ε�������iter(a,1)����2,a[2]����ֱ����һ��nilԪ�ء�]]

function iter (a, i)
    i = i + 1
    local v = a[i]
    if v then
        return i, v
    end
end

function myIpairs (a)
    return iter, a, 0
end

for k, v in myIpairs({ "a", "b", "c" })
do
    print(k, v)
end

--[[
��״̬�ĵ�����
�ܶ�����£���������Ҫ������״̬��Ϣ�����Ǽ򵥵�״̬�����Ϳ��Ʊ�������򵥵ķ�����ʹ�ñհ�������һ�ַ������ǽ����е�״̬��Ϣ��װ��table�ڣ���table��Ϊ��������״̬��������Ϊ��������¿��Խ����е���Ϣ�����table�ڣ����Ե�������ͨ������Ҫ�ڶ���������
]]

--����ʵ�����Ǵ������Լ��ĵ�������

array = { "Lua", "Tutorial" }

function elementIterator (collection)
    local index = 0
    local count = #collection
    -- �հ�����
    return function()
        index = index + 1
        if index <= count
        then
            --  ���ص������ĵ�ǰԪ��
            return collection[index]
        end
    end
end

for element in elementIterator(array)
do
    print(element)
end