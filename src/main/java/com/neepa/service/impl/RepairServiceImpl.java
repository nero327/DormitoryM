package com.neepa.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neepa.mapper.RepairMapper;
import com.neepa.entity.Repair;
import com.neepa.service.RepairService;
import com.neepa.utils.DataVOUtil;
import com.neepa.vo.RepairVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RepairServiceImpl extends ServiceImpl<RepairMapper, Repair> implements RepairService {
    @Autowired
    RepairMapper repairMapper;
    @Override
    public String queryVOListByPageToJSON(Integer page, Integer limit, RepairVO repairVO) {
        try{
            IPage<RepairVO> repairVOPage=null;
            if (page != null && limit != null)
                repairVOPage = new Page<>(page, limit);
            List<RepairVO> repairVOList = repairMapper.selectRepairVoList(repairVOPage,repairVO);
            return new DataVOUtil<RepairVO>().returnDataListJSON(repairVOList, repairVOPage.getTotal());
        }catch (NullPointerException e){
            return new DataVOUtil<RepairVO>().returnDataListJSON(null);
        }
    }

    @Override
    public String queryCountByStatus(Integer id) {
        if (id==null)return new DataVOUtil<HashMap<Integer, Integer>>().errorJSON();
        List<HashMap<Integer, Integer>> mapList = repairMapper.selectCountByStatus(id);
        return new DataVOUtil<HashMap<Integer, Integer>>().returnDataListJSON(mapList);
    }
}
