--[[
Lua Эͬ����(coroutine)
ʲô��Эͬ(coroutine)��
Lua Эͬ����(coroutine)���̱߳Ƚ����ƣ�ӵ�ж����Ķ�ջ�������ľֲ�������������ָ��ָ�룬ͬʱ��������Эͬ������ȫ�ֱ����������󲿷ֶ�����

Эͬ�Ƿǳ�ǿ��Ĺ��ܣ�����������Ҳ�ܸ��ӡ�

�̺߳�Эͬ��������
�߳���Эͬ�������Ҫ�������ڣ�һ�����ж���̵߳ĳ������ͬʱ���м����̣߳���Эͬ����ȴ��Ҫ�˴�Э�������С�

����һָ��ʱ��ֻ��һ��Эͬ���������У���������������е�Эͬ����ֻ������ȷ�ı�Ҫ������ʱ��Żᱻ����

Эͬ�����е�����ͬ���Ķ��̣߳��ڵȴ�ͬһ���߳����ļ����߳��е�����Эͬ��]]

--[[
�����﷨
����	����
coroutine.create()	����coroutine������coroutine�� ������һ������������resume���ʹ�õ�ʱ��ͻ��Ѻ�������
coroutine.resume()	����coroutine����create���ʹ��
coroutine.yield()	����coroutine����coroutine����Ϊ����״̬�������resume���ʹ�����кܶ����õ�Ч��
coroutine.status()	�鿴coroutine��״̬
ע��coroutine��״̬�����֣�dead��suspend��running������ʲôʱ����������״̬��ο�����ĳ���
coroutine.wrap����	����coroutine������һ��������һ�����������������ͽ���coroutine����create�����ظ�
coroutine.running()	���������ܵ�coroutine��һ��coroutine����һ���̣߳���ʹ��running��ʱ�򣬾��Ƿ���һ��corouting���̺߳�]]

--����ʵ����ʾ�����ϸ����������÷���
-- coroutine_test.lua �ļ�
co = coroutine.create(
        function(i)
            print(i);
        end
)

coroutine.resume(co, 1)   -- 1
print(coroutine.status(co))  -- dead

print("----------")

co = coroutine.wrap(
        function(i)
            print(i);
        end
)

co(1)

print("----------")

co2 = coroutine.create(
        function()
            for i = 1, 10 do
                print(i)
                if i == 3 then
                    print(coroutine.status(co2))  --running
                    print(coroutine.running()) --thread:XXXXXX
                end
                coroutine.yield()
            end
        end
)

coroutine.resume(co2) --1
coroutine.resume(co2) --2
coroutine.resume(co2) --3

print(coroutine.status(co2))   -- suspended
print(coroutine.running())   --nil

print("----------")

--[[coroutine.running�Ϳ��Կ�����,coroutine�ڵײ�ʵ�־���һ���̡߳�

��createһ��coroutine��ʱ����������߳���ע����һ���¼���

��ʹ��resume�����¼���ʱ��create��coroutine�����ͱ�ִ���ˣ�������yield��ʱ��ʹ������ǰ�̣߳��Ⱥ��ٴ�resume�����¼���

���������Ƿ���һ������ϸ��ʵ����]]

function foo (a)
    print("foo �������", a)
    return coroutine.yield(2 * a) -- ����  2*a ��ֵ
end

co = coroutine.create(function(a, b)
    print("��һ��Эͬ����ִ�����", a, b) -- co-body 1 10
    local r = foo(a + 1)

    print("�ڶ���Эͬ����ִ�����", r)
    local r, s = coroutine.yield(a + b, a - b)  -- a��b��ֵΪ��һ�ε���Эͬ����ʱ����

    print("������Эͬ����ִ�����", r, s)
    return b, "����Эͬ����"                   -- b��ֵΪ�ڶ��ε���Эͬ����ʱ����
end)

print("main", coroutine.resume(co, 1, 10)) -- true, 4
print("--�ָ���----")
print("main", coroutine.resume(co, "r")) -- true 11 -9
print("---�ָ���---")
print("main", coroutine.resume(co, "x", "y")) -- true 10 end
print("---�ָ���---")
print("main", coroutine.resume(co, "x", "y")) -- cannot resume dead coroutine
print("---�ָ���---")

--[[
����ʵ���������£�

����resume����Эͬ������,resume�����ɹ�����true�����򷵻�false��
Эͬ�������У�
���е�yield��䣻
yield����Эͬ���򣬵�һ��resume���أ���ע�⣺�˴�yield���أ�������resume�Ĳ�����
�ڶ���resume���ٴλ���Эͬ���򣻣�ע�⣺�˴�resume�Ĳ����У����˵�һ��������ʣ�µĲ�������Ϊyield�Ĳ�����
yield���أ�
Эͬ����������У�
���ʹ�õ�Эͬ�������������ɺ�������� resumev�����������cannot resume dead coroutine
resume��yield�����ǿ��֮�����ڣ�resume���������У������ⲿ״̬�����ݣ����뵽Эͬ�����ڲ�����yield���ڲ���״̬�����ݣ����ص������С�]]

--[[������-����������
�����Ҿ�ʹ��Lua��Эͬ���������������-��������һ�������⡣]]
local newProductor

function productor()
    local i = 0
    while true do
        i = i + 1
        send(i)     -- ����������Ʒ���͸�������
    end
end

function consumer()
    while true do
        local i = receive()     -- ������������õ���Ʒ
        print(i)
    end
end

function receive()
    local status, value = coroutine.resume(newProductor)
    return value
end

function send(x)
    coroutine.yield(x)     -- x��ʾ��Ҫ���͵�ֵ��ֵ�����Ժ󣬾͹����Эͬ����
end

-- ��������
newProductor = coroutine.create(productor)
consumer()