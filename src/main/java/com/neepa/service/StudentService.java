package com.neepa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neepa.dto.StudentDTO;
import com.neepa.entity.Student;
import com.neepa.vo.StudentVO;

import java.util.List;

public interface StudentService extends IService<Student> {
    StudentVO queryStudentVOById(String id);
    List<StudentVO> queryListByPage(Integer page, Integer limit);
    String queryListByPageToJSON(Integer page, Integer limit);
    String saveForm(StudentDTO studentDTO);
    String updateForm(StudentDTO studentDTO);
    String removeStudentbyId(String id);

    String queryListByConditionToJSON(Integer page, Integer limit, String id, String name, Integer sex, String phone);
}
