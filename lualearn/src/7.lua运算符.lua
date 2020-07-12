--[[
Lua �����
�������һ������ķ��ţ����ڸ��߽�����ִ���ض�����ѧ���߼����㡣Lua�ṩ�����¼�����������ͣ�

    ���������
    ��ϵ�����
    �߼������
    ���������]]

--[[
���������
�±��г��� Lua �����еĳ���������������趨 A ��ֵΪ10��B ��ֵΪ 20��

������	����	ʵ��
        +	�ӷ�	A + B ������ 30
        -	����	A - B ������ -10
        *	�˷�	A * B ������ 200
        /	����	B / A w������ 2
        %	ȡ��	B % A ������ 0
        ^	����	A^2 ������ 100
        -	����	-A ������v -10]]

--���ǿ���ͨ������ʵ��������͸������������������Ӧ�ã�

a = 21
b = 10
c = a + b
print("Line 1 - c ��ֵΪ ", c)
c = a - b
print("Line 2 - c ��ֵΪ ", c)
c = a * b
print("Line 3 - c ��ֵΪ ", c)
c = a / b
print("Line 4 - c ��ֵΪ ", c)
c = a % b
print("Line 5 - c ��ֵΪ ", c)
c = a ^ 2
print("Line 6 - c ��ֵΪ ", c)
c = -a
print("Line 7 - c ��ֵΪ ", c)

--[[
��ϵ�����
�±��г��� Lua �����еĳ��ù�ϵ��������趨 A ��ֵΪ10��B ��ֵΪ 20��

������	����	ʵ��
        ==	���ڣ��������ֵ�Ƿ���ȣ���ȷ��� true�����򷵻� false	(A == B) Ϊ false��
        ~=	�����ڣ��������ֵ�Ƿ���ȣ���ȷ��� false�����򷵻� true<	(A ~= B) Ϊ true��
        >	���ڣ������ߵ�ֵ�����ұߵ�ֵ������ true�����򷵻� false	(A > B) Ϊ false��
        <	С�ڣ������ߵ�ֵ�����ұߵ�ֵ������ false�����򷵻� true	(A < B) Ϊ true��
        >=	���ڵ��ڣ������ߵ�ֵ���ڵ����ұߵ�ֵ������ true�����򷵻� false	(A >= B) is not true.
        <=	С�ڵ��ڣ� �����ߵ�ֵС�ڵ����ұߵ�ֵ������ true�����򷵻� false	(A <= B) is true.]]

--���ǿ���ͨ������ʵ��������͸��������ϵ�������Ӧ�ã�

a = 21
b = 10

if (a == b)
then
    print("Line 1 - a ���� b")
else
    print("Line 1 - a ������ b")
end

if (a ~= b)
then
    print("Line 2 - a ������ b")
else
    print("Line 2 - a ���� b")
end

if (a < b)
then
    print("Line 3 - a С�� b")
else
    print("Line 3 - a ���ڵ��� b")
end

if (a > b)
then
    print("Line 4 - a ���� b")
else
    print("Line 5 - a С�ڵ��� b")
end

-- �޸� a �� b ��ֵ
a = 5
b = 20
if (a <= b)
then
    print("Line 5 - a С�ڵ���  b")
end

if (b >= a)
then
    print("Line 6 - b ���ڵ��� a")
end

--[[
�߼������

�±��г��� Lua �����еĳ����߼���������趨 A ��ֵΪ true��B ��ֵΪ false��

������	����	ʵ��
        and	�߼���������� ������ߵĲ�����Ϊ true ������Ϊ true��	(A and B) Ϊ false��
        or	�߼���������� ������ߵĲ�����һһ��Ϊ true ������Ϊ true��	(A or B) Ϊ true��
not	�߼��ǲ����������߼��������෴���������Ϊ true���߼���Ϊ false��	not(A and B) Ϊ true��]]

--���ǿ���ͨ������ʵ��������͸��������߼��������Ӧ�ã�
a = true
b = true

if (a and b)
then
    print("a and b - ����Ϊ true")
end

if (a or b)
then
    print("a or b - ����Ϊ true")
end

print("---------�ָ���---------")

-- �޸� a �� b ��ֵ
a = false
b = true

if (a and b)
then
    print("a and b - ����Ϊ true")
else
    print("a and b - ����Ϊ false")
end

if (not (a and b))
then
    print("not( a and b) - ����Ϊ true")
else
    print("not( a and b) - ����Ϊ false")
end

--[[
���������
�±��г��� Lua �����е������������������ַ������ȵ��������

������	����	ʵ��
        ..	���������ַ���	a..b ������ a Ϊ "Hello " �� b Ϊ "World", ������Ϊ "Hello World"��
#	һԪ������������ַ������ĳ��ȡ�	#"Hello" ���� 5]]

--���ǿ���ͨ������ʵ��������͸������������������������ַ������ȵ��������Ӧ�ã�

a = "Hello "
b = "World"

print("�����ַ��� a �� b ", a .. b)

print("b �ַ������� ", #b)

print("�ַ��� Test ���� ", #"Test")

print("w3cschool���߽̳���ַ���� ", #"www.w3cschool.cn")

--[[
��������ȼ�
�Ӹߵ��͵�˳��
        ^
        not    - (unary)
        *      /
+      -
..
<      >      <=     >=     ~=     ==
and
or
����^��..�����еĶ�Ԫ��������������ӵġ�

a+i < b/2+1          <-->       (a+i) < ((b/2)+1)
5+x^2*8              <-->       5+((x^2)*8)
a < y and y <= z     <-->       (a < y) and (y <= z)
-x^2                 <-->       -(x^2)
x^y^z                <-->       x^(y^z)]]

--���ǿ���ͨ������ʵ��������͸�����˽� Lua ��������������ȼ���

a = 20
b = 10
c = 15
d = 5

e = (a + b) * c / d;-- ( 30 * 15 ) / 5
print("(a + b) * c / d ����ֵΪ  :", e)

e = ((a + b) * c) / d; -- (30 * 15 ) / 5
print("((a + b) * c) / d ����ֵΪ :", e)

e = (a + b) * (c / d);-- (30) * (15/5)
print("(a + b) * (c / d) ����ֵΪ :", e)

e = a + (b * c) / d;  -- 20 + (150/5)
print("a + (b * c) / d ����ֵΪ   :", e)
