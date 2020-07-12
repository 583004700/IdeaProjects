--[[Lua
ģ�����
ģ��������һ����װ�⣬��
Lua
5.1
��ʼ��Lua
�����˱�׼��ģ�������ƣ����԰�һЩ���õĴ������һ���ļ����
API
�ӿڵ���ʽ�������ط����ã������ڴ�������úͽ��ʹ�����϶ȡ�

Lua
��ģ�����ɱ�������������֪Ԫ����ɵ�
table����˴���һ��ģ��ܼ򵥣����Ǵ���һ��
table��Ȼ�����Ҫ�����ĳ����������������У���󷵻����
table
���С�����Ϊ�����Զ���ģ��
module.lua���ļ������ʽ���£�]]
--[[���Ͽ�֪��ģ��Ľṹ����һ�� table �Ľṹ����˿������������ table ���Ԫ����������������ģ����ĳ���������

����� func2 ����Ϊ�����ľֲ�����������ʾһ��˽�к���������ǲ��ܴ��ⲿ����ģ��������˽�к���������ͨ��ģ����Ĺ��к���������.]]

--[[    require ����
    Lua�ṩ��һ����Ϊrequire�ĺ�����������ģ�顣Ҫ����һ��ģ�飬ֻ��Ҫ�򵥵ص��þͿ����ˡ����磺

    require("<ģ����>")
    ����

    require "<ģ����>"
    ִ�� require ��᷵��һ����ģ�鳣��������ɵ� table�����һ��ᶨ��һ�������� table ��ȫ�ֱ�����]]

-- test_module.php �ļ�
-- module ģ��Ϊ�����ᵽ�� module.lua
require("module")

print(module.constant)

module.func3()

--���߸����ص�ģ�鶨��һ������������������ã�

-- test_module2.php �ļ�
-- module ģ��Ϊ�����ᵽ�� module.lua
-- �������� m
local m = require("module")

print(m.constant)

m.func3()

--[[
���ػ���
�����Զ����ģ�飬ģ���ļ����Ƿ����ĸ��ļ�Ŀ¼���У�����
require
�����Լ����ļ�·�����ز��ԣ����᳢�Դ�
Lua
�ļ���
C
������м���ģ�顣

require
��������
Lua
�ļ���·���Ǵ����ȫ�ֱ���
package.path
�У���
Lua
�����󣬻��Ի�������
LUA_PATH
��ֵ����ʼ����������������û���ҵ��û�����������ʹ��һ������ʱ�����Ĭ��·������ʼ����

��Ȼ�����û��
LUA_PATH
�������������Ҳ�����Զ������ã��ڵ�ǰ�û���Ŀ¼�´� .profile
�ļ���û���򴴽����� .bashrc
�ļ�Ҳ���ԣ�������� "~/lua/"
·������
LUA_PATH
���������

#LUA_PATH
export
LUA_PATH = "~/lua/?.lua;;"
�ļ�·���� ";"
�ŷָ�������
2
�� ";;"
��ʾ�¼ӵ�·���������ԭ����Ĭ��·����

���ţ����»�������������ʹ֮������Ч��

source ~/.profile
��ʱ���� package.path ��ֵ�ǣ�

/Users/dengjoe/lua/?.lua;./?.lua;/usr/ local /share/lua/5.1/?.lua;/usr/ local /share/lua/5.1/?/init.lua;/usr/ local/lib/lua/5.1/?.lua;/usr/ local /lib/lua/5.1/?/init.lua
��ô���� require("module") ʱ�ͻ᳢�Դ������ļ�Ŀ¼ȥ����Ŀ�ꡣ

/Users/dengjoe/lua/module.lua;
./module.lua
/usr/ local /share/lua/5.1/module.lua
/usr/ local /share/lua/5.1/module/init.lua
/usr/ local /lib/lua/5.1/module.lua
/usr/ local /lib/lua/5.1/module/init.lua
����ҹ�Ŀ���ļ��������� package.loadfile ������ģ�顣���򣬾ͻ�ȥ�� C ����⡣

�������ļ�·���Ǵ�ȫ�ֱ��� package.cpath ��ȡ���������������ͨ���������� LUA_CPATH ����ʼ��

�����Ĳ��Ը������һ����ֻ�������ڻ����������� so �� dll ���͵��ļ�������ҵõ�����ô require �ͻ�ͨ�� package.loadlib ����������

C ��
Lua��C�Ǻ����׽�ϵģ�ʹ��CΪLuaд����

��Lua��д����ͬ��C����ʹ����ǰ�������ȼ��ز����ӣ��ڴ����ϵͳ�������׵�ʵ�ַ�ʽ��ͨ����̬���ӿ���ơ�

Lua��һ����loadlib�ĺ������ṩ�����еĶ�̬���ӵĹ��ܡ������������������:��ľ���·���ͳ�ʼ�����������Ե��͵ĵ��õ���������:

local path = "/usr/local/lua/lib/libluasocket.so"
local f = loadlib(path, "luaopen_socket")
loadlib��������ָ���ĿⲢ�����ӵ�Lua��Ȼ���������򿪿⣨Ҳ����˵û�е��ó�ʼ������������֮�����س�ʼ��������ΪLua��һ���������������ǾͿ���ֱ����Lua�е�������

������ض�̬����߲��ҳ�ʼ������ʱ����loadlib������nil�ʹ�����Ϣ�����ǿ����޸�ǰ��һ�δ��룬ʹ�������Ȼ����ó�ʼ��������

local path = "/usr/local/lua/lib/libluasocket.so"
-- ���� path = "C:\\windows\\luasocket.dll"������ Window ƽ̨��
local f = assert(loadlib(path, "luaopen_socket"))
f()  -- �����򿪿�
һ��������������������Ƶķ��������һ����ǰ���������Ƶ�stub�ļ�����װ�����ƿ��ʱ�����������ĳ��Ŀ¼��ֻ��Ҫ�޸�stub�ļ���Ӧ�����ƿ��ʵ��·�����ɡ�

��stub�ļ����ڵ�Ŀ¼���뵽LUA_PATH�������趨��Ϳ���ʹ��require��������C���ˡ�]]

print(package.path)