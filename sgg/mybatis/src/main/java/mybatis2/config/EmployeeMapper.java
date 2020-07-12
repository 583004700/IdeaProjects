package mybatis2.config;

import org.apache.ibatis.annotations.Param;

public interface EmployeeMapper {
    Employee getEmpById(@Param("id") Integer id,@Param("lastName") Integer lastName);
}
