package com.neepa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neepa.entity.Role;
import com.neepa.entity.StaffRole;

import java.util.List;

public interface StaffRoleService extends IService<StaffRole> {
    List<Role> queryRoleByStaffId(Integer id);
    List<String> queryStringRoleByStaffId(Integer id);

    String queryRoleListByPageToJSON(Integer page, Integer limit, StaffRole sr);

    boolean saveBatchUni(List<StaffRole> srList);
}
