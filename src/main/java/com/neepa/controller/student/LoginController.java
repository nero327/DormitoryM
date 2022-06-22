package com.neepa.controller.student;

import com.neepa.config.shiro.ShiroUsernamePasswordToken;
import com.neepa.entity.Student;
import com.neepa.utils.Constants;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
@Api
@Controller("StudentLoginController")
public class LoginController {
    @GetMapping("/login")
    public String login(){
        return "student/login";
    }
    @PostMapping("/login")
    public String login(String username, String password, Model model){
        Subject subject = SecurityUtils.getSubject();
        ShiroUsernamePasswordToken token = new ShiroUsernamePasswordToken(username,password,Constants.UserType.STUDENT.type);
        try{
            subject.login(token);
            Student student = (Student)subject.getPrincipal();
            Session session = subject.getSession();
            session.setAttribute(Constants.StudentIdSession,student.getId());
            session.setAttribute(Constants.StudentNameSession,student.getName());
            return "redirect:/";
        }catch (UnknownAccountException e){
            model.addAttribute("msg","用户名或密码错误");
            return "student/login";
        }catch (IncorrectCredentialsException e){
            model.addAttribute("msg","用户名或密码错误");
            return "student/login";
        }
    }
    @GetMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login";
    }
}
