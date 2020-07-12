--[[
Lua ����
��Lua�У������Ƕ����ͱ��ʽ���г������Ҫ�������ȿ�����������һЩ����Ĺ�����Ҳ������������һЩֵ��

Lua �ṩ�������ڽ�����������Ժܷ�����ڳ����е������ǣ���print()�������Խ�����Ĳ�����ӡ�ڿ���̨�ϡ�

Lua ������Ҫ��������;��

1.���ָ����������������º�����Ϊ�������ʹ�ã�
2.���㲢����ֵ����������º�����Ϊ��ֵ���ı��ʽʹ�á�
��������
Lua ������Ժ��������ʽ���£�

optional_function_scope function function_name( argument1, argument2, argument3..., argumentn)
function_body
return result_params_comma_separated
end
������

optional_function_scope
: �ò����ǿ�ѡ���ƶ�������ȫ�ֺ������Ǿֲ�������δ���øò���ĩβΪȫ�ֺ������������Ҫ���ú���Ϊ�ֲ�������Ҫʹ�ùؼ��� local��
function_name:
ָ���������ơ�
argument1, argument2, argument3..., argumentn:
������������������Զ��Ÿ���������Ҳ���Բ���������
function_body:
�����壬��������Ҫִ�еĴ������顣
result_params_comma_separated:
��������ֵ��Lua���Ժ������Է��ض��ֵ��ÿ��ֵ�Զ��Ÿ�����]]

--����ʵ�������˺��� max()������Ϊ num1, num2�����ڱȽ���ֵ�Ĵ�С�����������ֵ��

--[[ ������������ֵ�����ֵ --]]
function max(num1, num2)

    if (num1 > num2) then
        result = num1;
    else
        result = num2;
    end

    return result;
end
-- ���ú���
print("��ֵ�Ƚ����ֵΪ ", max(10, 4))
print("��ֵ�Ƚ����ֵΪ ", max(5, 6))

--Lua �����ǿ��Խ�������Ϊ�������ݸ�����������ʵ����
myprint = function(param)
    print("���Ǵ�ӡ���� -   ##", param, "##")
end

function add(num1, num2, functionPrint)
    result = num1 + num2
    -- ���ô��ݵĺ�������
    functionPrint(result)
end
myprint(10)
-- myprint ������Ϊ��������
add(2, 5, myprint)

--[[�෵��ֵ
Lua�������Է��ض�����ֵ������string.find���䷵��ƥ�䴮"��ʼ�ͽ������±�"�����������ƥ�䴮����nil����]]

s, e = string.find("www.w3cschool.cn", "w3cschool")
print(s, e)

--Lua�����У���return���г�Ҫ���ص�ֵ���б��ɷ��ض�ֵ���磺

function maximum (a)
    local mi = 1             -- ���ֵ����
    local m = a[mi]          -- ���ֵ
    for i, val in ipairs(a) do
        if val > m then
            mi = i
            m = val
        end
    end
    return m, mi
end

print(maximum({ 8, 10, 23, 12, 5 }))

--[[�ɱ����
Lua�������Խ��ܿɱ���Ŀ�Ĳ�������C���������ں��������б���ʹ�����㣨...) ��ʾ�����пɱ�Ĳ�����

Lua�������Ĳ�������һ����arg�ı��У�#arg ��ʾ��������ĸ�����

���磬���Ǽ��㼸������ƽ��ֵ��]]

function average(...)
    result = 0
    local arg = { ... }
    for i, v in ipairs(arg) do
        result = result + v
    end
    print("�ܹ����� " .. #arg .. " ����")
    return result / #arg
end

print("ƽ��ֵΪ", average(10, 5, 3, 4, 5, 6))