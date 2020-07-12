package mybatis6.other;

import java.util.List;

public interface EmployeeMapper {
    List<Employee> getEmps(Employee employee);
    void addEmp(Employee emp);
}
