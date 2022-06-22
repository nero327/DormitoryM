package com.neepa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neepa.entity.Building;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BuildingMapper extends BaseMapper<Building> {
}
