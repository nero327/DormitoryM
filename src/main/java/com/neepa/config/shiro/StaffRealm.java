package com.neepa.config.shiro;

import com.neepa.entity.Permission;
import com.neepa.entity.Role;
import com.neepa.entity.Staff;
import com.neepa.service.impl.*;
import com.neepa.utils.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class StaffRealm extends AuthorizingRealm {
    @Autowired
    StaffRoleServiceImpl staffRoleService;
    @Autowired
    RolePermissionServiceImpl rolePermissionService;
    @Autowired
    StaffServiceImpl staffService;
    @Autowired
    RoleServiceImpl roleService;
    @Autowired
    PermissionServiceImpl permissionService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole(Constants.StaffRole);
        Subject subject = SecurityUtils.getSubject();
        Staff staff = (Staff) subject.getPrincipal();
        List<Role> roles = staffRoleService.queryRoleByStaffId(staff.getId());
        boolean isSuper=false;
        for(Role role:roles){
            if ("ADMIN".equals(role.getName())){
                isSuper=true;
                break;
            }
            info.addRole(role.getName());
            List<String> permissions = rolePermissionService.queryStringPermissionByRoleId(role.getId());
            info.addStringPermissions(permissions);
        }
        if(isSuper){
            List<Role> roleList = roleService.list();
            for (Role role : roleList) {
                info.addRole(role.getName());
            }
            List<Permission> permList = permissionService.list();
            for (Permission perm : permList) {
                info.addStringPermission(perm.getName());
            }
        }
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken userToken = (UsernamePasswordToken)token;
        try{
            int id = Integer.parseInt(userToken.getUsername());
            Staff staff = staffService.getById(id);
            if(staff==null){
                return null;
            }
            return new SimpleAuthenticationInfo(staff,staff.getPassword(),"StaffRealm");
        }catch (NumberFormatException e){
            return null;
        }
    }
}
