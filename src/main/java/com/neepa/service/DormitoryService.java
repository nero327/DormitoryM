package com.neepa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neepa.entity.Dormitory;
import com.neepa.vo.DormitoryVO;
import com.neepa.vo.StudentVO;

import java.util.List;

public interface DormitoryService extends IService<Dormitory> {
    List<DormitoryVO> queryListByPage(Integer page, Integer limit);
    String queryListByPageToJSON(Integer page, Integer limit);
    List<Dormitory> getAvailableByInfo(Integer buildingId, Integer floor);

    boolean updateDormitoryAvailableDECR(Integer id);
    boolean updateDormitoryAvailableINCR(Integer id);
    boolean deleteBatch(Integer buildingId,Integer floor);

    List<StudentVO> queryStudentListById(Integer id);

    String queryListByConditionToJSON(Integer page, Integer limit, Integer buildingId, Integer floor, Integer number);
}
