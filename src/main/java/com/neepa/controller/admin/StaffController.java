package com.neepa.controller.admin;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.neepa.entity.*;
import com.neepa.service.StaffRoleService;
import com.neepa.service.impl.DepartmentServiceImpl;
import com.neepa.service.impl.StaffRoleServiceImpl;
import com.neepa.service.impl.StaffServiceImpl;
import com.neepa.utils.Constants;
import com.neepa.utils.DataVOUtil;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Api
@Controller("AdminStaffController")
@RequestMapping("/admin/staff")
@RequiresRoles({Constants.StaffRole,"staff"})
public class StaffController {
    @Autowired
    StaffServiceImpl staffService;
    @Autowired
    DepartmentServiceImpl departmentService;
    @Autowired
    StaffRoleServiceImpl staffRoleService;

    @RequiresPermissions("staff:read")
    @GetMapping({"","/","/index"})
    public String index(Model model){
        List<Department> departmentList = departmentService.list();
        model.addAttribute("departmentList",departmentList);
        return "admin/staff/index";
    }

    @RequiresPermissions("staff:create")
    @GetMapping("create")
    public String create(Model model){
        List<Department> departmentList = departmentService.list();
        model.addAttribute("departmentList",departmentList);
        return "admin/staff/create";
    }

    @RequiresPermissions("staff:create")
    @PostMapping({"","/"})
    @ResponseBody
    public String save(@ModelAttribute Staff staff){
        if(staff.getPassword()==null) staff.setPassword(staff.getId().toString());
        try{
            boolean save = staffService.save(staff);
            if (save){
                return new DataVOUtil<Staff>().successJSON();
            }else {
                return new DataVOUtil<Staff>().errorJSON();
            }
        }catch (Exception e){
            return new DataVOUtil<Staff>().errorJSON();
        }
    }

    @RequiresPermissions("staff:read")
    @GetMapping("/{id}")
    @ResponseBody
    public String read(Integer id){
        Staff staff = staffService.getById(id);
        return new DataVOUtil<Staff>().successJSON(staff);
    }

    @RequiresPermissions("staff:read")
    @GetMapping("/list")
    @ResponseBody
    public String list(@RequestParam(value = "page",required = false) Integer page,
                       @RequestParam(value = "limit",required = false) Integer limit,
                       @RequestParam(value = "id",required = false) Integer id,
                       @RequestParam(value = "name",required = false) String name,
                       @RequestParam(value = "secx",required = false) Integer sex,
                       @RequestParam(value = "phone",required = false) String phone,
                       @RequestParam(value = "departmentId",required = false) Integer departmentId){
        if(id!=null||name!=null||sex!=null||phone!=null)
            return staffService.queryVOListByConditionToJSON(page,limit,id,name,sex,phone,departmentId);
        return staffService.queryVOListByPageToJSON(page,limit);
    }

    @RequiresPermissions("staff:update")
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Integer id, Model model){
        Staff staff = staffService.getById(id);
        List<Department> departmentList = departmentService.list();
        if (staff==null) return "redirect:/admin";
        model.addAttribute("staff",staff);
        model.addAttribute("departmentList",departmentList);
        return "admin/staff/edit";
    }

    @RequiresPermissions("staff:update")
    @PutMapping("/{id}")
    @ResponseBody
    public String update(@PathVariable Integer id,@ModelAttribute Staff staff){
        boolean update = staffService.update(null, Wrappers.<Staff>lambdaUpdate()
                .eq(Staff::getId, staff.getId())
                .set(Staff::getName, staff.getName())
                .set(Staff::getSex, staff.getSex())
                .set(Staff::getPhone, staff.getPhone())
                .set(Staff::getDepartmentId, staff.getDepartmentId()));
        if (update){
            return new DataVOUtil<Staff>().successJSON();
        }else {
            return new DataVOUtil<Staff>().errorJSON();
        }
    }

    @RequiresPermissions("staff:delete")
    @DeleteMapping("/{id}")
    @ResponseBody
    public String delete(@PathVariable Integer id){
        boolean delete = staffService.removeById(id);;
        if (delete){
            return new DataVOUtil<Staff>().successJSON();
        }else {
            return new DataVOUtil<Staff>().errorJSON();
        }
    }

    @RequiresPermissions("staff:read")
    @GetMapping("/{id}/role")
    public String role(@PathVariable Integer id,Model model){
        model.addAttribute("staffId",id);
        return "admin/staff/role";
    }

    @RequiresPermissions("staff:read")
    @GetMapping("/{id}/roleList")
    public String permList(@PathVariable Integer id,Model model){
        model.addAttribute("staffId",id);
        return "admin/staff/roleList";
    }

    @RequiresPermissions("staff:update")
    @PostMapping("/role")
    @ResponseBody
    public String saveRole(@RequestParam("sr") String sr){
        List<StaffRole> srList = JSON.parseArray(sr,StaffRole.class);
        boolean saveBatch = staffRoleService.saveBatchUni(srList);
        if (saveBatch){
            return new DataVOUtil<RolePermission>().successJSON();
        }else {
            return new DataVOUtil<RolePermission>().errorJSON();
        }
    }

    @RequiresPermissions("staff:delete")
    @DeleteMapping("/role")
    @ResponseBody
    public String deleteRole(Integer staffId,Integer roleId){
        HashMap<String,Object> map = new HashMap();
        map.put("staff_id",staffId);
        map.put("role_id",roleId);
        boolean remove = staffRoleService.removeByMap(map);
        if (remove){
            return new DataVOUtil<Role>().successJSON();
        }else {
            return new DataVOUtil<Role>().errorJSON();
        }
    }

    @RequiresPermissions("staff:read")
    @GetMapping("/{id}/role/list")
    @ResponseBody
    public String roleList(Integer page, Integer limit,@PathVariable Integer id){
        StaffRole sr = new StaffRole();
        sr.setStaffId(id);
        return staffRoleService.queryRoleListByPageToJSON(page,limit,sr);
    }
}
