package mybatis.service;

import mybatis.mapper.Employee;
import mybatis.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IndexService {
    @Autowired
    EmployeeMapper employeeMapper;

    public List<Employee> list(){
        return employeeMapper.list();
    }

    @Transactional
    public void addEmp(){
        Employee e = new Employee();
        e.setLastName("事务");
        employeeMapper.addEmp(e);
        int i = 1/0;
    }
}
