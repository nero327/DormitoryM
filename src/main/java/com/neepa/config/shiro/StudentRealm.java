package com.neepa.config.shiro;

import com.neepa.entity.Student;
import com.neepa.service.StudentService;
import com.neepa.utils.Constants;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class StudentRealm extends AuthorizingRealm {
    @Autowired
    StudentService studentService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole(Constants.StudentRole);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken userToken = (UsernamePasswordToken)token;
        try{
            String id = userToken.getUsername();
            Student student = studentService.getById(id);
            if(student==null){
                return null;
            }
            return new SimpleAuthenticationInfo(student,student.getPassword(),"StudentRealm");
        }catch (NumberFormatException e){
            return null;
        }
    }
}
