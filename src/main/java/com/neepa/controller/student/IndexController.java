package com.neepa.controller.student;

import com.neepa.dto.StudentDTO;
import com.neepa.entity.Staff;
import com.neepa.entity.Student;
import com.neepa.service.StudentService;
import com.neepa.service.impl.StudentServiceImpl;
import com.neepa.utils.Constants;
import com.neepa.utils.DataVOUtil;
import com.neepa.vo.StudentVO;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Api
@Controller("StudentIndexController")
@RequiresRoles(value = Constants.StudentRole)
public class IndexController {
    @Autowired
    StudentServiceImpl studentService;
    @GetMapping({"","/"})
    public String index(){
        return "student/index";
    }
    @GetMapping("/welcome")
    public String welcome(){
        return "student/welcome";
    }
    @GetMapping("/self")
    public String self(Model model){
        Subject subject = SecurityUtils.getSubject();
        String id = (String)subject.getSession().getAttribute(Constants.StudentIdSession);
        StudentVO student = studentService.queryStudentVOById(id);
        model.addAttribute("student",student);
        return "student/self";
    }
    @PutMapping("/update")
    @ResponseBody
    public String update(@ModelAttribute StudentDTO studentDTO){
        try {
            studentDTO.setId((String)SecurityUtils.getSubject().getSession().getAttribute(Constants.StudentIdSession));
            return studentService.updateForm(studentDTO);
        }catch (Exception e){
            return new DataVOUtil<Staff>().errorJSON();
        }
    }
}
