package mybatis3.mapper;

import java.util.List;

public interface DepartmentMapper {
    Department getDeptById(Integer id);

    Department getDeptByIdPlus(Integer id);

    List<Department> getDeptListPlus();

    Department getDeptByIdStep(Integer id);
}
