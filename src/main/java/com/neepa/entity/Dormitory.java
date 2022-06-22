package com.neepa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dormitory implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer floor;
    private Integer number;
    private Integer available;
    private Integer max;
    private Integer buildingId;

}
