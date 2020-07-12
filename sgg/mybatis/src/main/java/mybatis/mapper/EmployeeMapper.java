package mybatis.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EmployeeMapper {

    Map<String,Object> getEmpReturnMap(Integer id);

    //告诉mybatis使用哪个字段作为属性
    @MapKey("id")
    Map<String,Employee> getEmpReturnMap2();

    int addEmp(Employee employee);

    int addEmpO(Emp emp);

    int updateEmp(Employee employee);

    int delEmp(Employee employee);

//    @Select("select * from tbl_employee")
    List<Employee> list();

    Employee getEmpById(Integer id);

    Employee getEmpByIdAndLastName(@Param("id") Integer id, @Param("lastName") String lastName);
}
