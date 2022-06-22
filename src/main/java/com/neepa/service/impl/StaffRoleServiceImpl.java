package com.neepa.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neepa.entity.Permission;
import com.neepa.mapper.StaffRoleMapper;
import com.neepa.entity.Role;
import com.neepa.entity.StaffRole;
import com.neepa.service.StaffRoleService;
import com.neepa.utils.DataVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class StaffRoleServiceImpl extends ServiceImpl<StaffRoleMapper, StaffRole> implements StaffRoleService {
    @Autowired
    StaffRoleMapper staffRoleMapper;
    @Override
    public List<Role> queryRoleByStaffId(Integer id) {
        return getBaseMapper().queryRoleByStaffId(id);
    }

    @Override
    public List<String> queryStringRoleByStaffId(Integer id) {
        return getBaseMapper().queryStringRoleByStaffId(id);
    }

    @Override
    public String queryRoleListByPageToJSON(Integer page, Integer limit, StaffRole staffRole) {
        IPage<Role> IPage=null;
        if (page != null && limit != null)
            IPage = new Page<>(page, limit);
        List<Role> roleList = staffRoleMapper.selectRoleList(IPage, staffRole);
        return new DataVOUtil<Role>().returnDataListJSON(roleList, IPage.getTotal());
    }

    @Override
    public boolean saveBatchUni(List<StaffRole> srList) {
        for (StaffRole item : srList) {
            StaffRole one = query().setEntity(item).one();
            if (one==null){
                int insert = staffRoleMapper.insert(item);
                if(insert<1) return false;
            }
        }
        return true;
    }
}
