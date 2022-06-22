package com.neepa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.neepa.entity.Department;
import com.neepa.vo.DepartmentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DepartmentMapper extends BaseMapper<Department> {
    @Select("select d.*,(select count(*) from staff s where s.department_id=d.id) as count from department d")
    List<DepartmentVO> selectVoListByPage(IPage<DepartmentVO> departmentVOPage);
}
