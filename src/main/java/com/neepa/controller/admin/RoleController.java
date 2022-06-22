package com.neepa.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.neepa.entity.Permission;
import com.neepa.entity.Role;
import com.neepa.entity.RolePermission;
import com.neepa.mapper.RolePermissionMapper;
import com.neepa.service.impl.RolePermissionServiceImpl;
import com.neepa.service.impl.RoleServiceImpl;
import com.neepa.utils.Constants;
import com.neepa.utils.DataVOUtil;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.util.HashMap;
import java.util.List;

@Api
@Controller("AdminRoleController")
@RequestMapping("/admin/role")
@RequiresRoles({Constants.StaffRole,"role"})
public class RoleController {
    @Autowired
    RoleServiceImpl roleService;
    @Autowired
    RolePermissionServiceImpl rolePermissionService;

    @RequiresPermissions("role:read")
    @GetMapping({"","/","/index"})
    public String index(){
        return "admin/role/index";
    }

    @RequiresPermissions("role:create")
    @GetMapping("create")
    public String create(){
        return "admin/role/create";
    }

    @RequiresPermissions("role:create")
    @PostMapping({"","/"})
    @ResponseBody
    public String save(@ModelAttribute Role role){
        boolean save = roleService.save(role);
        if (save){
            return new DataVOUtil<Role>().successJSON();
        }else {
            return new DataVOUtil<Role>().errorJSON();
        }
    }

    @RequiresPermissions("role:read")
    @GetMapping("/{id}")
    public String read(Integer id){
        Role role = roleService.getById(id);
        return new DataVOUtil<Role>().successJSON(role);
    }

    @RequiresPermissions("role:read")
    @GetMapping("/list")
    @ResponseBody
    public String list(Integer page, Integer limit,Role role){
        return roleService.queryListByPageToJSON(page,limit,role);
    }

    @RequiresPermissions("role:update")
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Integer id, Model model){
        Role role = roleService.getById(id);
        if (role==null) return "redirect:/admin";
        model.addAttribute("role",role);
        return "admin/role/edit";
    }

    @RequiresPermissions("role:update")
    @PutMapping("/{id}")
    @ResponseBody
    public String update(@PathVariable Integer id,@ModelAttribute Role role){
        boolean update = roleService.updateById(role);;
        if (update){
            return new DataVOUtil<Role>().successJSON();
        }else {
            return new DataVOUtil<Role>().errorJSON();
        }
    }

    @RequiresPermissions("role:delete")
    @DeleteMapping("/{id}")
    @ResponseBody
    public String delete(@PathVariable Integer id){
        boolean delete = roleService.removeById(id);;
        if (delete){
            return new DataVOUtil<Role>().successJSON();
        }else {
            return new DataVOUtil<Role>().errorJSON();
        }
    }

    @RequiresPermissions("role:read")
    @GetMapping("/{id}/perm")
    public String perm(@PathVariable Integer id,Model model){
        model.addAttribute("roleId",id);
        return "admin/role/perm";
    }

    @RequiresPermissions("role:read")
    @GetMapping("/{id}/permList")
    public String permList(@PathVariable Integer id,Model model){
        model.addAttribute("roleId",id);
        return "admin/role/permList";
    }

    @RequiresPermissions("role:update")
    @PostMapping("/perm")
    @ResponseBody
    public String savePerm(@RequestParam("rp") String rp){
        List<RolePermission> rpList = JSON.parseArray(rp,RolePermission.class);
        boolean saveBatch = rolePermissionService.saveBatchUni(rpList);
        if (saveBatch){
            return new DataVOUtil<RolePermission>().successJSON();
        }else {
            return new DataVOUtil<RolePermission>().errorJSON();
        }
    }

    @RequiresPermissions("role:delete")
    @DeleteMapping("/perm")
    @ResponseBody
    public String deletePerm(Integer roleId,Integer permId){
        HashMap<String,Object> map = new HashMap();
        map.put("role_id",roleId);
        map.put("permission_id",permId);
        boolean remove = rolePermissionService.removeByMap(map);
        if (remove){
            return new DataVOUtil<Role>().successJSON();
        }else {
            return new DataVOUtil<Role>().errorJSON();
        }
    }

    @RequiresPermissions("role:read")
    @GetMapping("/{id}/perm/list")
    @ResponseBody
    public String permList(Integer page, Integer limit,@PathVariable Integer id){
        RolePermission rp = new RolePermission();
        rp.setRoleId(id);
        return rolePermissionService.queryPermListByPageToJSON(page,limit,rp);
    }
}
