-- �ļ���Ϊ module.lua
-- ����һ����Ϊ module ��ģ��
module = {}

-- ����һ������
module.constant = "����һ������"

-- ����һ������
function module.func1()
    io.write("����һ�����к�����\n")
end

local function func2()
    print("����һ��˽�к�����")
end

function module.func3()
    func2()
end

return module