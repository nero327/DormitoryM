package com.neepa.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neepa.entity.Role;
import com.neepa.entity.StaffRole;
import com.neepa.mapper.RolePermissionMapper;
import com.neepa.entity.Permission;
import com.neepa.entity.RolePermission;
import com.neepa.service.RolePermissionService;
import com.neepa.utils.DataVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission>  implements RolePermissionService {
    @Autowired
    RolePermissionMapper rolePermissionMapper;
    @Override
    public List<Permission> queryPermissionByRoleId(Integer id) {
        return getBaseMapper().queryPermissionByRoleId(id);
    }

    @Override
    public List<String> queryStringPermissionByRoleId(Integer id) {
        return getBaseMapper().queryStringPermissionByRoleId(id);
    }

    @Override
    public String queryPermListByPageToJSON(Integer page, Integer limit, RolePermission rolePerm) {
        IPage<Permission> IPage=null;
        if (page != null && limit != null)
            IPage = new Page<>(page, limit);
        List<Permission> permissionList = rolePermissionMapper.selectPermissionList(IPage, rolePerm);
        return new DataVOUtil<Permission>().returnDataListJSON(permissionList, IPage.getTotal());
    }

    @Override
    public boolean saveBatchUni(List<RolePermission> rpList) {
        for (RolePermission item : rpList) {
            RolePermission one = query().setEntity(item).one();
            if (one==null){
                int insert = rolePermissionMapper.insert(item);
                if(insert<1) return false;
            }
        }
        return true;
    }
}
