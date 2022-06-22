package com.neepa.controller.admin;

import com.neepa.utils.Constants;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Api
@Controller("AdminIndexController")
@RequestMapping("/admin")
@RequiresRoles({Constants.StaffRole})
public class IndexController {
    @GetMapping({"","/","/index"})
    public String index(){
        return "admin/index";
    }
    @GetMapping("/welcome")
    public String welcome(){
        return "admin/welcome";
    }
}
