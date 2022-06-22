package com.neepa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neepa.entity.Department;

public interface DepartmentService extends IService<Department> {
    String queryListByPageToJSON(Integer page, Integer limit);
    String queryVOListByPageToJSON(Integer page, Integer limit);
}
