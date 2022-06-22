package com.neepa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.neepa.entity.Repair;
import com.neepa.vo.RepairVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface RepairMapper extends BaseMapper<Repair> {
    List<RepairVO> selectRepairVoList(IPage<RepairVO> repairVOPage,@Param("repair") RepairVO repairVO);

    List<HashMap<Integer,Integer>> selectCountByStatus(Integer id);
}
