package com.neepa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neepa.entity.Repair;
import com.neepa.vo.RepairVO;

public interface RepairService extends IService<Repair> {
    String queryVOListByPageToJSON(Integer page, Integer limit, RepairVO repairVO);

    String queryCountByStatus(Integer staffId);
}
