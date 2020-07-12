--[[Lua �ַ���
�ַ�����(String)�������֡���ĸ���»�����ɵ�һ���ַ���

Lua �������ַ�������ʹ���������ַ�ʽ����ʾ��

�����ż��һ���ַ���
˫���ż��һ���ַ���]]
--[[��
--]]
--���һ���ַ���


--�������ַ�ʽ���ַ���ʵ�����£�

string1 = "Lua"
print("\"�ַ��� 1 ��\"", string1)
string2 = 'w3cschool.cn'
print("�ַ��� 2 ��", string2)

string3 = [["Lua �̳�"]]
print("�ַ��� 3 ��", string3)

--[[ת���ַ����ڱ�ʾ����ֱ����ʾ���ַ���������˼����س������ȡ������ַ���ת��˫���ſ���ʹ�� "\""��

���е�ת���ַ�������Ӧ�����壺

ת���ַ�
����
ASCII��ֵ��ʮ���ƣ�
\a
����(BEL)
007
\b
�˸�(BS) ������ǰλ���Ƶ�ǰһ��
008
\f
��ҳ(FF)������ǰλ���Ƶ���ҳ��ͷ
012
\n
����(LF) ������ǰλ���Ƶ���һ�п�ͷ
010
\r
�س�(CR) ������ǰλ���Ƶ����п�ͷ
013
\t
ˮƽ�Ʊ�(HT) ��������һ��TABλ�ã�
009
\v
��ֱ�Ʊ�(VT)
011
\\
����һ����б���ַ�''\'
092
\'
����һ�������ţ�Ʋ�ţ��ַ�
039
\"
����һ��˫�����ַ�
034
���ַ�(NULL)
000
\ddd
1��3λ�˽�����������������ַ�
��λ�˽���
\xhh
1��2λʮ������������������ַ�
��λʮ������]]

--[[
�ַ�������
Lua �ṩ�˺ܶ�ķ�����֧���ַ����Ĳ�����

���	���� & ��;
1	string.upper(argument):
�ַ���ȫ��תΪ��д��ĸ��
2	string.lower(argument):
�ַ���ȫ��תΪСд��ĸ��
3	string.gsub(mainString,findString,replaceString,num)
���ַ������滻,mainStringΪҪ�滻���ַ����� findString Ϊ���滻���ַ���replaceString Ҫ�滻���ַ���num �滻���������Ժ��ԣ���ȫ���滻�����磺
        > string.gsub("aaaa","a","z",3);
zzza 3
4	string.find (str, substr, [init, [end]]--[[
)
��һ��ָ����Ŀ���ַ���������ָ��������(����������Ϊ����),���������λ�á��������򷵻� nil��
> string.find("Hello Lua user", "Lua", 1)
7 9
5	string.reverse(arg)
�ַ�����ת
> string.reverse("Lua")
auL
6	string.format(...)
����һ������printf�ĸ�ʽ���ַ���
> string.format("the value is:%d",4)
the value is:4
7	string.char(arg) �� string.byte(arg[,int])
char ����������ת���ַ������ӣ� byte ת���ַ�Ϊ����ֵ(����ָ��ĳ���ַ���Ĭ�ϵ�һ���ַ�)��
> string.char(97,98,99,100)
abcd


string.byte("ABCD",4)
68
string.byte("ABCD")
65


8	string.len(arg)
�����ַ������ȡ�
string.len("abc")
3
9	string.rep(string, n))
�����ַ���string��n������
> string.rep("abcd",2)
abcdabcd
10	..
���������ַ���
> print("www.w3cschool"..".cn")
www.w3cschool.cn]]

--[[�ַ�����Сдת��
����ʵ����ʾ����ζ��ַ�����Сд����ת����]]

string1 = "Lua";
print(string.upper(string1))
print(string.lower(string1))

--[[�ַ��������뷴ת
����ʵ����ʾ����ζ��ַ������в����뷴ת������]]

string = "Lua Tutorial"
-- �����ַ���
print(string.find(string,"Tutorial"))
reversedString = string.reverse(string)
print("���ַ���Ϊ",reversedString)

--[[�ַ�����ʽ��
����ʵ����ʾ����ζ��ַ������и�ʽ��������]]

string1 = "Lua"
string2 = "Tutorial"
number1 = 10
number2 = 20
-- �����ַ�����ʽ��
print(string.format("������ʽ�� %s %s",string1,string2))
-- ���ڸ�ʽ��
date = 2; month = 1; year = 2014
print(string.format("���ڸ�ʽ�� %02d/%02d/%03d", date, month, year))
-- ʮ���Ƹ�ʽ��
print(string.format("%.4f",1/3))

--[[�ַ��������໥ת��
����ʵ����ʾ���ַ��������໥ת����]]

-- �ַ�ת��
-- ת����һ���ַ�
print(string.byte("Lua"))
-- ת���������ַ�
print(string.byte("Lua",3))
-- ת��ĩβ��һ���ַ�
print(string.byte("Lua",-1))
-- �ڶ����ַ�
print(string.byte("Lua",2))
-- ת��ĩβ�ڶ����ַ�
print(string.byte("Lua",-2))

-- ���� ASCII ��ת��Ϊ�ַ�
print(string.char(97))

--[[
�������ú���
����ʵ����ʾ�������ַ���������������ַ������ȣ��ַ������ӣ��ַ������Ƶȣ�]]
string1 = "www."
string2 = "w3cschool"
string3 = ".cn"
-- ʹ�� .. �����ַ�������
print("�����ַ���",string1..string2..string3)

-- �ַ�������
print("�ַ������� ",string.len(string2))

-- �ַ������� 2 ��
repeatedString = string.rep(string2,2)
print(repeatedString)