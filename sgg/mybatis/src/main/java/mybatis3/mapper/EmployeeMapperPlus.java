package mybatis3.mapper;

import java.util.List;

public interface EmployeeMapperPlus {
    Employee getEmpById(Integer id);

    Employee getEmpAndDept(Integer id);

    Employee getEmpByIdStep(Integer id);

    List<Employee> getEmpStep();

    /**
     * 通过部门ID查询所有员工，外键查询
     * @param deptId
     * @return
     */
    List<Employee> getEmpsByDeptId(Integer deptId);

    Employee myEmpDis(Integer id);
}
