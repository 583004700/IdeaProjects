from pymysql import *
conn = connect(host="localhost", port=3306, user="root", password="root", database="jfinal_demo")
cs1 = conn.cursor()
count = cs1.execute("insert into student(id,name,sex,class,score) values(0,'zho',0,2,100)")
print(count)
cs1.close()
conn.commit()
# conn.rollback()