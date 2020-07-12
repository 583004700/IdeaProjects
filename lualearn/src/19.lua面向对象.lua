--[[
Lua �������
��������̣�Object Oriented Programming��OOP����һ�ַǳ����еļ������̼ܹ���

���¼��ֱ�����Զ�֧����������̣�

C++
Java
Objective-C
Smalltalk
C#
Ruby
�����������
1�� ��װ��ָ�ܹ���һ��ʵ�����Ϣ�����ܡ���Ӧ��װ��һ�������Ķ����е����ԡ�
2�� �̳У��̳еķ��������ڲ��Ķ�ԭ����Ļ����϶���������䣬����ʹ��ԭ���ܵ��Ա��棬���¹���Ҳ������չ���������ڼ����ظ����룬�������Ŀ���Ч�ʡ�
3�� ��̬��ͬһ���������ڲ�ͬ�Ķ��󣬿����в�ͬ�Ľ��ͣ�������ͬ��ִ�н����������ʱ������ͨ��ָ������ָ�룬������ʵ���������еķ�����
4�����󣺳���(Abstraction)�Ǽ򻯸��ӵ���ʵ�����;����������Ϊ���������ҵ���ǡ�����ඨ�壬���ҿ�������ǡ���ļ̳м���������⡣
Lua ���������
����֪�������������Ժͷ�����ɡ�LUA��������Ľṹ��table��������Ҫ��table��������������ԡ�

lua�е�function����������ʾ��������ôLUA�е������ͨ��table + functionģ�������

���ڼ̳У�����ͨ��metetableģ����������Ƽ��ã�ֻģ��������Ķ���󲿷�ʱ�乻���ˣ���

Lua�еı�����ĳ����������һ�ֶ��������һ������Ҳ��״̬����Ա��������Ҳ��������ֵ�����ı��ԣ��ر���ӵ��������ֵͬ�Ķ���table������������ͬ�Ķ���һ�������ڲ�ͬ��ʱ��Ҳ�����в�ͬ��ֵ������ʼ����һ��������������ƣ������������������ʲô���������Ĵ���û�й�ϵ�����������ǵĳ�Ա��������Ҳ�У�
]]

Account = { balance = 0 }
function Account.withdraw (v)
    Account.balance = Account.balance - v
end
--[[������崴����һ���µĺ��������ұ�����Account�����withdraw���ڣ��������ǿ����������ã�

Account.withdraw(100.00)
һ����ʵ��
���¼򵥵���������������ԣ� area, length �� breadth��printArea�������ڴ�ӡ��������]]

-- Meta class
Rectangle = { area = 0, length = 0, breadth = 0 }

-- ������ķ��� new
function Rectangle:new (o, length, breadth)
    o = o or {}
    setmetatable(o, self)
    self.__index = self
    self.length = length or 0
    self.breadth = breadth or 0
    self.area = length * breadth;
    return o
end

-- ������ķ��� printArea
function Rectangle:printArea ()
    print("�������Ϊ ", self.area)
end

--[[��������
����������λ���ʵ�������ڴ�Ĺ��̡�ÿ���඼�������Լ����ڴ沢���������ݡ�

r = Rectangle:new(nil,10,20)
��������
���ǿ���ʹ�õ��(.)������������ԣ�]]

--print(r.length)
--[[���ʳ�Ա����
���ǿ���ʹ��ð��(:)������������ԣ�

r:printArea()
�ڴ��ڶ����ʼ��ʱ���䡣

����ʵ��
����������ʾ�� Lua ������������ʵ����]]

-- Meta class
Shape = { area = 0 }

-- �����෽�� new
function Shape:new (o, side)
    o = o or {}
    setmetatable(o, self)
    self.__index = self
    side = side or 0
    self.area = side * side;
    return o
end

-- �����෽�� printArea
function Shape:printArea ()
    print("���Ϊ ", self.area)
end

-- ��������
myshape = Shape:new(nil, 10)

myshape:printArea()

--[[Lua �̳�
�̳���ָһ������ֱ��ʹ����һ��������Ժͷ�������������չ����������Ժͷ�����

������ʾ��һ���򵥵ļ̳�ʵ����]]

-- Meta class
Shape = {area = 0}
-- �����෽�� new
function Shape:new (o,side)
    o = o or {}
    setmetatable(o, self)
    self.__index = self
    side = side or 0
    self.area = side*side;
    return o
end
-- �����෽�� printArea
function Shape:printArea ()
    print("���Ϊ ",self.area)
end
--[[��������ʵ����Square ����̳��� Shape ��:

Square = Shape:new()
-- Derived class method new
function Square:new (o,side)
    o = o or Shape:new(o,side)
    setmetatable(o, self)
    self.__index = self
    return o
end
����ʵ��
����ʵ�����Ǽ̳���һ���򵥵��࣬����չ������ķ������������б����˼̳���ĳ�Ա�����ͷ�����]]

-- Meta class
Shape = {area = 0}
-- �����෽�� new
function Shape:new (o,side)
    o = o or {}
    setmetatable(o, self)
    self.__index = self
    side = side or 0
    self.area = side*side;
    return o
end
-- �����෽�� printArea
function Shape:printArea ()
    print("���Ϊ ",self.area)
end

-- ��������
myshape = Shape:new(nil,10)
myshape:printArea()

Square = Shape:new()
-- �����෽�� new
function Square:new (o,side)
    o = o or Shape:new(o,side)
    setmetatable(o, self)
    self.__index = self
    return o
end

-- �����෽�� printArea
function Square:printArea ()
    print("���������Ϊ ",self.area)
end

-- ��������
mysquare = Square:new(nil,10)
mysquare:printArea()

Rectangle = Shape:new()
-- �����෽�� new
function Rectangle:new (o,length,breadth)
    o = o or Shape:new(o)
    setmetatable(o, self)
    self.__index = self
    self.area = length * breadth
    return o
end

-- �����෽�� printArea
function Rectangle:printArea ()
    print("�������Ϊ ",self.area)
end

-- ��������
myrectangle = Rectangle:new(nil,10,20)
myrectangle:printArea()

--������д
--Lua �����ǿ�����д������ĺ��������������ж����Լ���ʵ�ַ�ʽ��

-- �����෽�� printArea
function Square:printArea ()
    print("��������� ",self.area)
end