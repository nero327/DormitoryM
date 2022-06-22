package com.neepa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neepa.mapper.DormitoryMapper;
import com.neepa.mapper.StudentMapper;
import com.neepa.entity.Dormitory;
import com.neepa.entity.Student;
import com.neepa.service.DormitoryService;
import com.neepa.utils.DataVOUtil;
import com.neepa.vo.DormitoryVO;
import com.neepa.vo.StudentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DormitoryServiceImpl extends ServiceImpl<DormitoryMapper, Dormitory> implements DormitoryService {
    @Autowired
    DormitoryMapper dormitoryMapper;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    StudentServiceImpl studentService;

    @Override
    public List<DormitoryVO> queryListByPage(Integer page, Integer limit) {
        IPage<DormitoryVO> dormitoryVOPage=null;
        if (page != null && limit != null) {
            dormitoryVOPage = new Page<>(page, limit);
        }
        return dormitoryMapper.selectVoListByPage(dormitoryVOPage);
    }

    @Override
    public String queryListByPageToJSON(Integer page, Integer limit) {
        IPage<DormitoryVO> dormitoryVOPage=null;
        if (page != null && limit != null) {
            dormitoryVOPage = new Page<>(page, limit);
        }
        List<DormitoryVO> dormitoryVOList = dormitoryMapper.selectVoListByPage(dormitoryVOPage);
        return new DataVOUtil<DormitoryVO>().returnDataListJSON(dormitoryVOList,dormitoryVOPage.getTotal());
    }

    @Override
    public List<Dormitory> getAvailableByInfo(Integer buildingId, Integer floor) {
        QueryWrapper<Dormitory> wrapper = new QueryWrapper<>();
        wrapper.eq("building_id",buildingId).eq("floor",floor).gt("available",0);
        return dormitoryMapper.selectList(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateDormitoryAvailableDECR(Integer id) {
        Dormitory dormitory = dormitoryMapper.selectById(id);
        if (dormitory == null) return false;
        //判断宿舍是否有空位
        if (dormitory.getAvailable()<=0) return false;
        if (dormitoryMapper.update(dormitory, new UpdateWrapper<Dormitory>().eq("id",dormitory.getId()).set("available", dormitory.getAvailable() - 1)) == 0) return false;
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateDormitoryAvailableINCR(Integer id) {
        Dormitory dormitory = dormitoryMapper.selectById(id);
        if (dormitory == null) return false;
        if (dormitory.getAvailable() >= dormitory.getMax()) return false;
        if (dormitoryMapper.update(dormitory, new UpdateWrapper<Dormitory>().eq("id",dormitory.getId()).set("available", dormitory.getAvailable() + 1)) == 0) return false;
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(Integer buildingId, Integer floor) {
        QueryWrapper<Dormitory> wrapper = new QueryWrapper<>();
        wrapper.eq("building_id",buildingId).eq("floor",floor);
        List<Dormitory> list = this.list(wrapper);
        if(list==null)return true;
        List<Integer> idList = new ArrayList<Integer>();
        for(Dormitory d:list){
            idList.add(d.getId());
        }
        boolean remove = this.removeByIds(idList);
        if(!remove) return remove;
        UpdateWrapper<Student> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("dormitory_id",idList).set("dormitory_id",null);
        boolean update = studentService.update(updateWrapper);
        if(!update) return update;
        return true;
    }

    @Override
    public List<StudentVO> queryStudentListById(Integer id) {
        return dormitoryMapper.selectStudentListById(id);
    }

    @Override
    public String queryListByConditionToJSON(Integer page, Integer limit, Integer buildingId, Integer floor, Integer number) {
        IPage<DormitoryVO> dormitoryVOPage=null;
        if (page != null && limit != null) {
            dormitoryVOPage = new Page<>(page, limit);
        }
        return new DataVOUtil<StudentVO>().returnDataListJSON(dormitoryMapper.selectVoListByCondition(dormitoryVOPage,buildingId,floor,number));
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateDormitoryToStudent(Student student, Dormitory oldDormitory, Dormitory newDormitory) {
        //判断输入宿舍存在
        if(newDormitory==null)return false;
        if(oldDormitory==null)return false;
        //判断输入宿舍于原宿舍是否相同 不同则修改
        if(newDormitory.getId()==oldDormitory.getId())return true;

        //判断宿舍可用
        if(newDormitory.getAvailable()<1)return false;
        if(oldDormitory.getAvailable()>oldDormitory.getMax()||oldDormitory.getAvailable()<0)return false;
        //对原在宿舍可用人数+1
        //对新宿舍可用人数-1
        Integer oldAvailable = oldDormitory.getAvailable()+1;
        Integer newAvailable = newDormitory.getAvailable()-1;
        //更新新旧宿舍信息
        if(dormitoryMapper.update(oldDormitory,new UpdateWrapper<Dormitory>().eq("id",oldDormitory.getId()).set("available",oldAvailable))==0)return false;
        if(dormitoryMapper.update(newDormitory,new UpdateWrapper<Dormitory>().eq("id",newDormitory.getId()).set("available",newAvailable))==0)return false;
        //更新学生宿舍信息
        if(studentMapper.update(student, new UpdateWrapper<Student>().eq("id",student.getId()).set("dormitory_id", newDormitory.getId())) == 0)return false;
        return true;
    }


}
