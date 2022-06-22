package com.neepa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neepa.entity.Permission;
import com.neepa.entity.RolePermission;

import java.util.List;


public interface RolePermissionService extends IService<RolePermission> {
    List<Permission> queryPermissionByRoleId(Integer id);
    List<String> queryStringPermissionByRoleId(Integer id);

    String queryPermListByPageToJSON(Integer page, Integer limit, RolePermission rolePermission);

    boolean saveBatchUni(List<RolePermission> rpList);
}
