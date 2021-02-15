package springmvc1.com.springmvc.handlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springmvc1.com.springmvc.entitys.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

@Controller
@SessionAttributes(value={"user"},types = {String.class})
public class HelloWorld {

    @RequestMapping("/testRedirect")
    public String testRedirect(){
        System.out.println("testRedirect");
        return "redirect:/index.jsp";
    }

    @RequestMapping("/testView")
    public ModelAndView testView(@RequestParam Map params,int age){
        System.out.println(params);
        System.out.println(age);

        ModelAndView modelAndView = new ModelAndView("redirect:/testMap");
        modelAndView.addObject("age",18);

        //return "helloView";
        return modelAndView;
    }

    /**
     * @ModelAttribute 标记的方法会在每个方法执行之前被调用
     * @param id
     * @param map
     */
    //@ModelAttribute
    public void getUser(@RequestParam(value="id",required = false) Integer id,Map<String,Object> map){
        if(id != null){
            System.out.println("ModelAttribute method");
            //模拟从数据库中获取对象
            User user = new User(1,"Tom","123456","tom@qq.com",12);
            System.out.println("从数据库中获取一个对象："+user);
            map.put("user",user);
        }
    }

    @RequestMapping("/testModelAttribute")
    public String testModelAttribute(@ModelAttribute("user") User user){
        //user如果没有值，则会从@ModelAttribute标记的方法中获取,类型为User小写的key，也可以使用@ModelAttribute指明取哪个key
        System.out.println("修改："+user);
        return "success";
    }

    /**
     * @SessionAttributes 会把对象放入session中，request中也有
     * @param map
     * @return
     */
    @RequestMapping("/testSessionAttribute")
    public String testSessionAttribute(Map<String,Object> map){
        User user = new User(1,"Tom","123456","aaa@bbb.com",18);
        map.put("user",user);
        map.put("school","wen");
        return "success";
    }

    @RequestMapping("/testMap")
    public String testMap(Map<String,Object> map){
        System.out.println(map.getClass().getName());
        map.put("names", Arrays.asList("Tom","Jerry","Mike"));
        return "success";
    }

    /**
     * 可以包含视图和模型信息
     * @return
     */
    @RequestMapping("/testModelAndView")
    public ModelAndView testModelAndView(){
        String viewName = "success";
        ModelAndView modelAndView = new ModelAndView(viewName);
        modelAndView.addObject("time",new Date());
        return modelAndView;
    }

    /**
     * 参数也可以是write等其它的对象
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/testServletAPI")
    public String testServletAPI(HttpServletRequest request, HttpServletResponse response){
        System.out.println("testServletAPI: "+request);
        System.out.println("testServletAPI: "+response);
        return "success";
    }

    @RequestMapping("/testPojo")
    public String testPojo(User user){
        System.out.println("testPojo: "+user);
        return "success";
    }

    @RequestMapping("/testCookieValue")
    public String testCookieValue(@CookieValue("JSESSIONID") String sessionId){
        System.out.println("testCookieValue: "+sessionId);
        return "success";
    }

    @RequestMapping("/testRequestHeader")
    public String testRequestHeader(@RequestHeader(value = "Accept-Language") String al){
        System.out.println("testRequestHeader, "+al);
        return "success";
    }

    @RequestMapping(value = "/testRequestParam")
    public String testRequestParam(@RequestParam(value = "username") String un,@RequestParam(value="age",required = false,defaultValue = "18") int age){
        System.out.println("testRequestParam, username: "+un+",age: "+age);
        return "success";
    }

    @RequestMapping(value="/testRest/{id}",method = RequestMethod.PUT)
    public String testRestPut(@PathVariable Integer id){
        System.out.println("Test PUT "+id);
        return "success";
    }

    @RequestMapping(value="/testRest/{id}",method = RequestMethod.DELETE)
    public String testRestDelete(@PathVariable Integer id){
        System.out.println("Test DELETE "+id);
        return "success";
    }

    @RequestMapping(value="/testRest",method = RequestMethod.POST)
    public String testRest(){
        System.out.println("Test POST ");
        return "success";
    }

    @RequestMapping(value="/testRest/{id}",method = RequestMethod.GET)
    public String testRest(@PathVariable Integer id){
        System.out.println("Test Get " + id);
        return "success";
    }

    @RequestMapping("/testPathVariable/{id}")
    public String testPathVariable(@PathVariable(value="id") Integer id){
        System.out.println("testPathVariable"+id);
        return "success";
    }

    /**
     * ant风格的路径
     * @return
     */
    @RequestMapping("/testAntPath/*/abc")
    public String testAntPath(){
        System.out.println("testAntPath");
        return "success";
    }

    @RequestMapping(value = "/helloworld")
    public String hello(){
        System.out.println("hello world");
        return "success";
    }

    @RequestMapping(value = "/testMethod",method = RequestMethod.POST)
    public String testMethod(){
        System.out.println("testMethod");
        return "success";
    }

    /**
     * 请求必须包含参数username，如果包含了age，则值不等于10
     * 请求头Accept-Language必须是zh-CN,zh;q=0.8
     * @return
     */
    @RequestMapping(value = "/testParamsAndHeaders", params = {"username","age!=10"},headers = "Accept-Language=zh-CN,zh;q=0.8")
    public String testParamsAndHeaders(){
        System.out.println("testParamsAndHeaders");
        return "success";
    }
}
