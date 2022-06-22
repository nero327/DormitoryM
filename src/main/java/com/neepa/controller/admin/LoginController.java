package com.neepa.controller.admin;

import com.neepa.config.shiro.ShiroUsernamePasswordToken;
import com.neepa.entity.Staff;
import com.neepa.service.impl.StaffServiceImpl;
import com.neepa.utils.Constants;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Api
@Controller("AdminLoginController")
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    StaffServiceImpl staffService;

    @GetMapping("/login")
    public String login(){
        return "admin/login";
    }
    @PostMapping("/login")
    public String login(String username, String password, Model model){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new ShiroUsernamePasswordToken(username,password,Constants.UserType.STAFF.type);
        try{
            subject.login(token);
            Staff staff = (Staff)subject.getPrincipal();
            Session session = subject.getSession();
            session.setAttribute(Constants.StaffNameSession,staff.getName());
            session.setAttribute(Constants.StaffIdSession,staff.getId());
            return "redirect:/admin";
        }catch (UnknownAccountException e){
            model.addAttribute("msg","用户名或密码错误");
            return "admin/login";
        }catch (IncorrectCredentialsException e){
            model.addAttribute("msg","用户名或密码错误");
            return "admin/login";
        }
    }
    @GetMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/admin/login";
    }
}
