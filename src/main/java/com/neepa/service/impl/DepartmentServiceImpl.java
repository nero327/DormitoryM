package com.neepa.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neepa.mapper.DepartmentMapper;
import com.neepa.entity.Department;
import com.neepa.service.DepartmentService;
import com.neepa.utils.DataVOUtil;
import com.neepa.vo.DepartmentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {
    @Autowired
    DepartmentMapper departmentMapper;
    @Override
    public String queryListByPageToJSON(Integer page, Integer limit) {
        long count = this.count();
        if(page!=null && limit!=null){
            IPage<Department> departmentPage = new Page<>(page,limit);
            IPage<Department> iPage = this.page(departmentPage);
            List<Department> departmentList = iPage.getRecords();
            return new DataVOUtil<Department>().returnDataListJSON(departmentList,count);
        }
        return new DataVOUtil<Department>().returnDataListJSON(this.list(),count);
    }

    @Override
    public String queryVOListByPageToJSON(Integer page, Integer limit) {
        IPage<DepartmentVO> departmentVOPage=null;
        if (page != null && limit != null) {
            departmentVOPage = new Page<>(page, limit);
        }
        List<DepartmentVO> departmentVOList = departmentMapper.selectVoListByPage(departmentVOPage);
        return new DataVOUtil<DepartmentVO>().returnDataListJSON(departmentVOList, departmentVOPage.getTotal());
    }
}
