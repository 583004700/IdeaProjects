package springmvc2.com.springmvc.crud.handlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EmployeeHandler2 {

    @RequestMapping(value = "/emp2", method = RequestMethod.GET)
    public String emp2() {
        return "redirect:/emps";
    }

}
