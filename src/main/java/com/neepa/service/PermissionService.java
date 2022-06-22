package com.neepa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neepa.entity.Permission;

public interface PermissionService extends IService<Permission> {
    String queryListByPageToJSON(Integer page, Integer limit, Permission permission);
}
