package com.neepa.config.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
public class ShiroExceptionHandler {
    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    public String handleShiroException(Exception ex) {
        return "无权限";
    }

    @ExceptionHandler(AuthorizationException.class)
    public String AuthorizationException(Exception ex, HttpServletRequest httpServletRequest) {
        if(httpServletRequest.getRequestURI().contains("admin")){
            return "redirect:/admin/login";
        }else{
            return "redirect:/login";
        }
    }
}
