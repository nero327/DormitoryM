package com.neepa.controller.admin;

import com.neepa.dto.StudentDTO;
import com.neepa.entity.Building;
import com.neepa.entity.Staff;
import com.neepa.entity.Student;
import com.neepa.service.impl.BuildingServiceImpl;
import com.neepa.service.impl.StudentServiceImpl;
import com.neepa.utils.Constants;
import com.neepa.utils.DataVOUtil;
import com.neepa.vo.StudentVO;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@Controller("AdminStudentController")
@RequestMapping("/admin/student")
@RequiresRoles({Constants.StaffRole,"student"})
public class StudentController {
    @Autowired
    StudentServiceImpl studentService;
    @Autowired
    BuildingServiceImpl buildingService;

    @RequiresPermissions("student:read")
    @GetMapping({"","/","/index"})
    public String index(){
        return "admin/student/index";
    }

    @RequiresPermissions("student:create")
    @GetMapping("create")
    public String create(Model model){
        List<Building> buildingList = buildingService.list();
        model.addAttribute("buildingList",buildingList);
        return "admin/student/create";
    }

    @RequiresPermissions("student:update")
    @PostMapping({"","/"})
    @ResponseBody
    public String save(@ModelAttribute StudentDTO studentDTO){
        studentDTO.setPassword(studentDTO.getPassword());
        try{
            return studentService.saveForm(studentDTO);
        }catch (Exception e){
            return new DataVOUtil<Staff>().errorJSON();
        }
    }

    @RequiresPermissions("student:read")
    @GetMapping("/{id}")
    public String read(Integer id){
        Student student = studentService.getById(id);
        return new DataVOUtil<Student>().returnDataJSON(student);
    }

    @RequiresPermissions("student:read")
    @GetMapping("/list")
    @ResponseBody
    public String list(@RequestParam(value = "page",required = false) Integer page,
                       @RequestParam(value = "limit",required = false) Integer limit,
                       @RequestParam(value = "id",required = false) String id,
                       @RequestParam(value = "name",required = false) String name,
                       @RequestParam(value = "sex",required = false) Integer sex,
                       @RequestParam(value = "phone",required = false) String phone){
        if(id!=null||name!=null||sex!=null||phone!=null)
            return studentService.queryListByConditionToJSON(page,limit,id,name,sex,phone);
        return studentService.queryListByPageToJSON(page,limit);
    }

    @RequiresPermissions("student:update")
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") String id, Model model){
        StudentVO student = studentService.queryStudentVOById(id);
        if (student==null) return "redirect:/admin";
        model.addAttribute("student",student);
        List<Building> buildingList = buildingService.list();
        model.addAttribute("buildingList",buildingList);
        return "admin/student/edit";
    }

    @RequiresPermissions("student:update")
    @PutMapping("/{id}")
    @ResponseBody
    public String update(@PathVariable Integer id,@ModelAttribute StudentDTO studentDTO){
        try {
            return studentService.updateForm(studentDTO);
        }catch (Exception e){
            return new DataVOUtil<Staff>().errorJSON();
        }
    }

    @RequiresPermissions("student:delete")
    @DeleteMapping("/{id}")
    @ResponseBody
    public String delete(@PathVariable String id){
        return studentService.removeStudentbyId(id);
    }


}
