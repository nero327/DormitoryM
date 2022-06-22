package com.neepa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.neepa.entity.Staff;
import com.neepa.vo.StaffVO;
import com.neepa.vo.StudentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StaffMapper extends BaseMapper<Staff> {
    @Select("select * from staff")
    List<Staff> queryStaffAll();
    @Select("SELECT s.*,d.name AS departmentName\n" +
            "FROM staff s\n" +
            "LEFT JOIN department d ON d.id = s.department_id\n")
    List<StaffVO> selectVoListByPage(IPage<StaffVO> staffVOPage);

    List<StudentVO> selectVoListByCondition(IPage<StudentVO> studentVOPage, @Param("id") Integer id,@Param("name") String name,@Param("sex") Integer sex,@Param("phone") String phone,@Param("departmentId") Integer departmentId);
}
