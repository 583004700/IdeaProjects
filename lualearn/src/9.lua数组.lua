--[[
Lua ����
���飬������ͬ�������͵�Ԫ�ذ�һ��˳�����еļ��ϣ�������һά����Ͷ�ά���顣

Lua �����������ֵ����ʹ��������ʾ������Ĵ�С���ǹ̶��ġ�
]]
--[[һά����
һά��������򵥵����飬���߼��ṹ�����Ա�һά���������forѭ���������е�Ԫ�أ�����ʵ����]]
array = { "Lua", "Tutorial" }

for i = 0, 2 do
    print(array[i])
end

--[[�������������ģ����ǿ���ʹ��������������������Ԫ�أ����֪��������û��ֵ�򷵻�nil��

�� Lua ����ֵ���� 1 Ϊ��ʼ������Ҳ����ָ�� 0 ��ʼ��

���������ǻ������Ը���Ϊ��������ֵ��]]

array = {}

for i= -2, 2 do
    array[i] = i *2
end

for i = -2,2 do
    print(array[i])
end

--[[
��ά����
��ά���鼴�����а��������һά�������������Ӧһ�����顣

������һ���������е����ж�ά���飺
]]
-- ��ʼ������
array = {}
for i=1,3 do
    array[i] = {}
    for j=1,3 do
        array[i][j] = i*j
    end
end

-- ��������
for i=1,3 do
    for j=1,3 do
        print(array[i][j])
    end
end

--��ͬ�������������������ж�ά���飺
-- ��ʼ������
array = {}
maxRows = 3
maxColumns = 3
for row=1,maxRows do
    for col=1,maxColumns do
        array[row*maxColumns +col] = row*col
    end
end

-- ��������
for row=1,maxRows do
    for col=1,maxColumns do
        print(array[row*maxColumns +col])
    end
end