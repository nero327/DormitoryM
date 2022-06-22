package com.neepa.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neepa.mapper.PermissionMapper;
import com.neepa.entity.Permission;
import com.neepa.service.PermissionService;
import com.neepa.utils.DataVOUtil;
import com.neepa.entity.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Autowired
    PermissionMapper permissionMapper;
    @Override
    public String queryListByPageToJSON(Integer page, Integer limit, Permission permission) {
        IPage<Permission> permissionPage=null;
        if (page != null && limit != null)
            permissionPage = new Page<>(page, limit);
        List<Permission> permissionList = permissionMapper.selectPermissionList(permissionPage, permission);
        return new DataVOUtil<Permission>().returnDataListJSON(permissionList, permissionPage.getTotal());
    }
}
