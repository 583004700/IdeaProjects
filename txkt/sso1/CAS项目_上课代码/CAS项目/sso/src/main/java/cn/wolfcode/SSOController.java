package cn.wolfcode;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wolfcode-lanxw
 */
@Controller
public class SSOController {
    @RequestMapping("/hello")
    @ResponseBody
    public String helloPage(){
        return "Hello SSO";
    }
}
