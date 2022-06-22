package com.neepa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.neepa.entity.Role;
import com.neepa.entity.StaffRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StaffRoleMapper extends BaseMapper<StaffRole> {
    @Select("select r.* from staff_role sr, role r where staff_id = #{id} and r.id=sr.role_id")
    List<Role> queryRoleByStaffId(Integer id);
    @Select("select r.name from staff_role sr, role r where sr.staff_id=#{id} and r.id=sr.role_id")
    List<String> queryStringRoleByStaffId(Integer id);

    List<Role> selectRoleList(IPage<Role> iPage,@Param("staffRole") StaffRole staffRole);
}
