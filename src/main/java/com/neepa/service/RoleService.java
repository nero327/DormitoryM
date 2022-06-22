package com.neepa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neepa.entity.Role;

public interface RoleService extends IService<Role> {
    String queryListByPageToJSON(Integer page, Integer limit, Role role);
}
