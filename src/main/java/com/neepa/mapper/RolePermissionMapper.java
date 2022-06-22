package com.neepa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.neepa.entity.Permission;
import com.neepa.entity.RolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    @Select("select p.* from role_permission rp,permission p where role_id = #{id} and p.id=rp.permission_id")
    List<Permission> queryPermissionByRoleId(Integer id);
    @Select("select p.name from role_permission rp,permission p where role_id = #{id} and p.id=rp.permission_id")
    List<String> queryStringPermissionByRoleId(Integer id);

    List<Permission> selectPermissionList(IPage<Permission> iPage,@Param("rolePerm") RolePermission rolePerm);
}
