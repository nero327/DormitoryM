package com.neepa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.neepa.entity.Permission;
import com.neepa.vo.RepairVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PermissionMapper extends BaseMapper<Permission> {
    Permission queryPermissionById(Integer id);

    List<Permission> selectPermissionList(IPage<Permission> permissionPage,@Param("perm") Permission permission);
}
