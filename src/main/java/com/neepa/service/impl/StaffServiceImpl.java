package com.neepa.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neepa.mapper.StaffMapper;
import com.neepa.entity.Staff;
import com.neepa.service.StaffService;
import com.neepa.utils.DataVOUtil;
import com.neepa.vo.StaffVO;
import com.neepa.vo.StudentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class StaffServiceImpl extends ServiceImpl<StaffMapper, Staff> implements StaffService {
    @Autowired
    StaffMapper staffMapper;

    @Override
    public String queryVOListByPageToJSON(Integer page, Integer limit) {
        IPage<StaffVO> staffVOPage=null;
        if (page != null && limit != null) {
            staffVOPage = new Page<>(page, limit);
        }
        List<StaffVO> staffVOList = staffMapper.selectVoListByPage(staffVOPage);
        return new DataVOUtil<StaffVO>().returnDataListJSON(staffVOList, staffVOPage.getTotal());
    }

    @Override
    public String queryVOListByConditionToJSON(Integer page, Integer limit, Integer id, String name, Integer sex, String phone, Integer departmentId) {
        IPage<StudentVO> studentVOPage = null;
        if(page!=null && limit!=null) {
            studentVOPage = new Page<>(page, limit);
        }
        return new DataVOUtil<StudentVO>().returnDataListJSON(staffMapper.selectVoListByCondition(studentVOPage,id,name,sex,phone,departmentId));
    }
}
