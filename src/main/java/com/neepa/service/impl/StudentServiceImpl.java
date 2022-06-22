package com.neepa.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neepa.dto.StudentDTO;
import com.neepa.mapper.DormitoryMapper;
import com.neepa.mapper.StudentMapper;
import com.neepa.entity.Dormitory;
import com.neepa.entity.Student;
import com.neepa.service.StudentService;
import com.neepa.utils.DataVOUtil;
import com.neepa.vo.StudentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    DormitoryMapper dormitoryMapper;
    @Autowired
    DormitoryServiceImpl dormitoryService;
    @Override
    public StudentVO queryStudentVOById(String id){
        if(studentMapper.selectById(id).getDormitoryId()==null)
            return studentMapper.selectStudentById(id);
        return studentMapper.selectStudentVoById(id);
    }
    @Override
    public List<StudentVO> queryListByPage(Integer page, Integer limit) {
        IPage<StudentVO> studentVOPage = null;
        if(page!=null && limit!=null){
            studentVOPage = new Page<>(page,limit);
        }
        return studentMapper.selectStudentVoListByPage(studentVOPage);
    }

    @Override
    public String queryListByPageToJSON(Integer page, Integer limit) {
        IPage<StudentVO> studentVOPage = null;
        if(page!=null && limit!=null){
            studentVOPage = new Page<>(page,limit);
        }
        return new DataVOUtil<StudentVO>().returnDataListJSON(studentMapper.selectStudentVoListByPage(studentVOPage),this.count());
    }

    @Override
    public String queryListByConditionToJSON(Integer page, Integer limit, String id, String name, Integer sex, String phone) {
        IPage<StudentVO> studentVOPage = null;
        if(page!=null && limit!=null) {
            studentVOPage = new Page<>(page, limit);
        }
        return new DataVOUtil<StudentVO>().returnDataListJSON(studentMapper.selectVoListByCondition(studentVOPage,id,name,sex,phone));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveForm(StudentDTO studentDTO) {
        Dormitory dormitory = dormitoryMapper.queryDormitoryByInfo(studentDTO.getBuildingId(),studentDTO.getFloor(),studentDTO.getNumber());
        Student student;
        if(dormitory!=null){
            if(!dormitoryService.updateDormitoryAvailableDECR(dormitory.getId()))return new DataVOUtil<StudentDTO>().returnErrorMsgJSON("宿舍可住人数不足");
            student = new Student(studentDTO.getId(), studentDTO.getName(), studentDTO.getPassword(), (short) (studentDTO.getSex() == "男" ? 1 : 0), studentDTO.getPhone(), dormitory.getId());
        }else{
            student = new Student(studentDTO.getId(), studentDTO.getName(), studentDTO.getPassword(), (short) (studentDTO.getSex() == "男" ? 1 : 0), studentDTO.getPhone(), null);
        }
        if(!this.save(student))return new DataVOUtil<StudentDTO>().returnErrorMsgJSON("新增失败");
        return new DataVOUtil<StudentDTO>().successJSON();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String updateForm(StudentDTO studentDTO) {
        //通过from表单信息找宿舍
        Student newStudent = null;
        Student student = this.getById(studentDTO.getId());
        Dormitory oldDormitory = dormitoryMapper.selectById(student.getDormitoryId());
        Dormitory newDormitory = dormitoryMapper.queryDormitoryByInfo(studentDTO.getBuildingId(),studentDTO.getFloor(),studentDTO.getNumber());
        if(newDormitory==null)return new DataVOUtil<StudentDTO>().returnErrorMsgJSON("新宿舍信息出错，更新失败");
        if(oldDormitory==null){
            if(!dormitoryService.updateDormitoryAvailableDECR(newDormitory.getId()))return new DataVOUtil<StudentDTO>().returnErrorMsgJSON("新宿舍可住人数不足");
            newStudent = new Student(studentDTO.getId(), studentDTO.getName(), studentDTO.getPassword(), (short) (studentDTO.getSex() == "1" ? 1 : 0), studentDTO.getPhone(), newDormitory.getId());
            if(!this.updateById(newStudent))return new DataVOUtil<StudentDTO>().returnErrorMsgJSON("学生信息更新失败");
            return new DataVOUtil<StudentDTO>().successJSON();
        }
        if(newDormitory.getId()!=oldDormitory.getId()){
            if(!dormitoryService.updateDormitoryAvailableDECR(newDormitory.getId()))return new DataVOUtil<StudentDTO>().returnErrorMsgJSON("新宿舍可住人数不足");
            if(!dormitoryService.updateDormitoryAvailableINCR(oldDormitory.getId()))return new DataVOUtil<StudentDTO>().returnErrorMsgJSON("旧宿舍可住人数更新失败");
            newStudent = new Student(studentDTO.getId(), studentDTO.getName(), studentDTO.getPassword(), (short) (studentDTO.getSex() == "1" ? 1 : 0), studentDTO.getPhone(), newDormitory.getId());
            if(!this.updateById(newStudent))return new DataVOUtil<StudentDTO>().returnErrorMsgJSON("学生信息更新失败");
            return new DataVOUtil<StudentDTO>().successJSON();
        }
        newStudent = new Student(studentDTO.getId(), studentDTO.getName(), studentDTO.getPassword(), (short) (studentDTO.getSex() == "1" ? 1 : 0), studentDTO.getPhone(), null);
        if(!this.updateById(newStudent))return new DataVOUtil<StudentDTO>().returnErrorMsgJSON("学生信息更新失败");
        return new DataVOUtil<StudentDTO>().successJSON();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String removeStudentbyId(String id){
        Student student = studentMapper.selectById(id);
        if(!dormitoryService.updateDormitoryAvailableINCR(student.getDormitoryId()))return new DataVOUtil<StudentDTO>().returnErrorMsgJSON("宿舍可住人数更新失败");
        if(!this.removeById(id))return new DataVOUtil<StudentDTO>().errorJSON();
        return new DataVOUtil<StudentDTO>().successJSON();
    }


}
