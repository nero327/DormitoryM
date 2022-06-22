package com.neepa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermission {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String remarks;
    private Integer permissionId;
    private Integer roleId;
}
