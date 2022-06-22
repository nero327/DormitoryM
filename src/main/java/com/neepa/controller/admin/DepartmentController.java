package com.neepa.controller.admin;

import com.neepa.entity.Department;
import com.neepa.service.impl.DepartmentServiceImpl;
import com.neepa.utils.Constants;
import com.neepa.utils.DataVOUtil;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Api
@Controller("AdminDepartmentController")
@RequestMapping("/admin/department")
@RequiresRoles({Constants.StaffRole,"dept"})
public class DepartmentController {
    @Autowired
    DepartmentServiceImpl departmentService;

    @RequiresPermissions("dept:read")
    @GetMapping({"","/","/index"})
    public String index(){
        return "admin/department/index";
    }

    @RequiresPermissions("dept:create")
    @GetMapping("create")
    public String create(){
        return "admin/department/create";
    }

    @RequiresPermissions("dept:create")
    @PostMapping({"","/"})
    @ResponseBody
    public String save(@ModelAttribute Department department){
        boolean save = departmentService.save(department);
        if (save){
            return new DataVOUtil<Department>().successJSON();
        }else {
            return new DataVOUtil<Department>().errorJSON();
        }
    }

    @RequiresPermissions("dept:read")
    @GetMapping("/{id}")
    public String read(Integer id){
        Department department = departmentService.getById(id);
        return new DataVOUtil<Department>().successJSON(department);
    }

    @RequiresPermissions("dept:read")
    @GetMapping("/list")
    @ResponseBody
    public String list(Integer page, Integer limit){
        return departmentService.queryVOListByPageToJSON(page,limit);
    }

    @RequiresPermissions("dept:update")
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Integer id, Model model){
        Department department = departmentService.getById(id);
        if (department==null) return "redirect:/admin";
        model.addAttribute("department",department);
        return "admin/department/edit";
    }

    @RequiresPermissions("dept:update")
    @PutMapping("/{id}")
    @ResponseBody
    public String update(@PathVariable Integer id,@ModelAttribute Department department){
        boolean update = departmentService.updateById(department);;
        if (update){
            return new DataVOUtil<Department>().successJSON();
        }else {
            return new DataVOUtil<Department>().errorJSON();
        }
    }

    @RequiresPermissions("dept:delete")
    @DeleteMapping("/{id}")
    @ResponseBody
    public String delete(@PathVariable Integer id){
        boolean delete = departmentService.removeById(id);;
        if (delete){
            return new DataVOUtil<Department>().successJSON();
        }else {
            return new DataVOUtil<Department>().errorJSON();
        }
    }
}
