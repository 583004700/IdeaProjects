# pip3 install pymysql
from pymysql import *
conn = connect(host="localhost", port=3306, user="root", password="root", database="jfinal_demo")
cs1 = conn.cursor()
count = cs1.execute("select id,name from student where id >=%s", [5])
print("查询到%s条数据" % count)

for i in range(count):
    result = cs1.fetchone()
    print(result)

cs1.close()
conn.close()
