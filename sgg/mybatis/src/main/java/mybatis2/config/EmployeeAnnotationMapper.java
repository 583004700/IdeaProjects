package mybatis2.config;

import org.apache.ibatis.annotations.Select;

public interface EmployeeAnnotationMapper {
    @Select("select * from tbl_employee where id = #{id}")
    Employee testAnnotation(Integer id);
}
