package mybatis5.cache;

import mybatis3.mapper.Department;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

//在包扫描的情况下可以用alias注解起别名
@Alias("emp")
public class Employee implements Serializable{
    private Integer id;
    private String lastName;
    private String email;
    private String gender;

    private Department department;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
