# 从模块中导入某个工具
from hm_面向对象.hm7_模块和包.hm_模块1 import Dog
from hm_面向对象.hm7_模块和包.hm_模块1 import sayHello
from hm_面向对象.hm7_模块和包.hm_模块1 import num
# 从模块中导入所有工具，使用时不用 模块名.工具 的方式调用，不推荐使用，因为有重名的方法会被覆盖
from hm_面向对象.hm7_模块和包.hm_模块1 import *

dog = Dog()
print(dog)
sayHello()
print(num)
