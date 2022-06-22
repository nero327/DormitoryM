package com.neepa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String remarks;
    private Date createTime;
    private Date updateTime;

}
