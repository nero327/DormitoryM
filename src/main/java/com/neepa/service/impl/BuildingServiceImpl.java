package com.neepa.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neepa.mapper.BuildingMapper;
import com.neepa.entity.Building;
import com.neepa.service.BuildingService;
import com.neepa.utils.DataVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BuildingServiceImpl extends ServiceImpl<BuildingMapper, Building> implements BuildingService {
    @Autowired
    BuildingMapper buildingMapper;
    public List<Building> queryListByPage(Integer page, Integer limit) {
        if(page!=null && limit!=null){
            IPage<Building> buildingPage = new Page<>(page,limit);
            return buildingMapper.selectPage(buildingPage,null).getRecords();
        }
        return this.list();
    }

    public String queryListByPageToJSON(Integer page, Integer limit) {
        long count = this.count();
        if(page!=null && limit!=null){
            IPage<Building> buildingPage = new Page<>(page,limit);
            IPage<Building> iPage = this.page(buildingPage);
            List<Building> buildingList = iPage.getRecords();
            return new DataVOUtil<Building>().returnDataListJSON(buildingList,count);
        }
        return new DataVOUtil<Building>().returnDataListJSON(this.list(),count);
    }
}
