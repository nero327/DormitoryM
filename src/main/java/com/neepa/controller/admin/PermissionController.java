package com.neepa.controller.admin;

import com.neepa.entity.Permission;
import com.neepa.service.impl.PermissionServiceImpl;
import com.neepa.utils.Constants;
import com.neepa.utils.DataVOUtil;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Api
@Controller("AdminPermissionController")
@RequestMapping("/admin/perm")
@RequiresRoles({Constants.StaffRole,"perm"})
public class PermissionController {
    @Autowired
    PermissionServiceImpl permissionService;

    @RequiresPermissions("perm:read")
    @GetMapping({"","/","/index"})
    public String index(){
        return "admin/permission/index";
    }

    @RequiresPermissions("perm:create")
    @GetMapping("create")
    public String create(){
        return "admin/permission/create";
    }

    @RequiresPermissions("perm:create")
    @PostMapping({"","/"})
    @ResponseBody
    public String save(@ModelAttribute Permission permission){
        boolean save = permissionService.save(permission);
        if (save){
            return new DataVOUtil<Permission>().successJSON();
        }else {
            return new DataVOUtil<Permission>().errorJSON();
        }
    }

    @RequiresPermissions("perm:read")
    @GetMapping("/{id}")
    public String read(Integer id){
        Permission permission = permissionService.getById(id);
        return new DataVOUtil<Permission>().successJSON(permission);
    }

    @RequiresPermissions("perm:read")
    @GetMapping("/list")
    @ResponseBody
    public String list(Integer page, Integer limit,Permission permission){
        return permissionService.queryListByPageToJSON(page,limit,permission);
    }

    @RequiresPermissions("perm:update")
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Integer id, Model model){
        Permission permission = permissionService.getById(id);
        if (permission==null) return "redirect:/admin";
        model.addAttribute("permission",permission);
        return "admin/permission/edit";
    }

    @RequiresPermissions("perm:update")
    @PutMapping("/{id}")
    @ResponseBody
    public String update(@PathVariable Integer id,@ModelAttribute Permission permission){
        boolean update = permissionService.updateById(permission);;
        if (update){
            return new DataVOUtil<Permission>().successJSON();
        }else {
            return new DataVOUtil<Permission>().errorJSON();
        }
    }

    @RequiresPermissions("perm:delete")
    @DeleteMapping("/{id}")
    @ResponseBody
    public String delete(@PathVariable Integer id){
        boolean delete = permissionService.removeById(id);;
        if (delete){
            return new DataVOUtil<Permission>().successJSON();
        }else {
            return new DataVOUtil<Permission>().errorJSON();
        }
    }
}
