package com.neepa.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neepa.entity.Permission;
import com.neepa.mapper.RoleMapper;
import com.neepa.entity.Role;
import com.neepa.service.RoleService;
import com.neepa.utils.DataVOUtil;
import com.neepa.vo.RolePermissionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    RoleMapper roleMapper;
    @Override
    public String queryListByPageToJSON(Integer page, Integer limit, Role role) {
        IPage<Role> roleIPage=null;
        if (page != null && limit != null)
            roleIPage = new Page<>(page, limit);
        List<Role> roleList = roleMapper.selectRoleList(roleIPage, role);
        return new DataVOUtil<Role>().returnDataListJSON(roleList, roleIPage.getTotal());
    }
}
