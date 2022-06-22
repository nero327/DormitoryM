package com.neepa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neepa.entity.Building;

import java.util.List;


public interface BuildingService extends IService<Building> {
    List<Building> queryListByPage(Integer page, Integer limit);
    String queryListByPageToJSON(Integer page, Integer limit);

}
