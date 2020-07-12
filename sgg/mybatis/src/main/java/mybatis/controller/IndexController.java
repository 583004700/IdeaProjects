package mybatis.controller;

import mybatis.mapper.Employee;
import mybatis.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/index")
public class IndexController {
    @Autowired
    IndexService indexService;
    @RequestMapping("/list")
    @ResponseBody
    public List<Employee> list(){
        return indexService.list();
    }

    @ResponseBody
    @RequestMapping("/addEmp")
    public String addEmp(){
        try {
            indexService.addEmp();
            return "保存成功";
        }catch (Exception e){
            e.printStackTrace();
            return "保存失败";
        }
    }
}
