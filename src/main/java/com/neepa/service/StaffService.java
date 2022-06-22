package com.neepa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neepa.entity.Staff;

public interface StaffService extends IService<Staff> {
    String queryVOListByPageToJSON(Integer page, Integer limit);

    String queryVOListByConditionToJSON(Integer page, Integer limit, Integer id, String name, Integer sex, String phone, Integer departmentId);
}
