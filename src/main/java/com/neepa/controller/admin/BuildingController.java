package com.neepa.controller.admin;

import com.neepa.entity.Building;
import com.neepa.service.impl.BuildingServiceImpl;
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
@Controller("AdminBuildingController")
@RequestMapping("/admin/building")
@RequiresRoles({Constants.StaffRole,"building"})
public class BuildingController {
    @Autowired
    BuildingServiceImpl buildingService;

    @RequiresPermissions("building:read")
    @GetMapping({"","/","/index"})
    public String index(){
        return "admin/building/index";
    }

    @RequiresPermissions("building:create")
    @GetMapping("create")
    public String create(){
        return "admin/building/create";
    }

    @RequiresPermissions("building:create")
    @PostMapping({"","/"})
    @ResponseBody
    public String save(@ModelAttribute Building building){
        boolean save = buildingService.save(building);
        if (save){
            return new DataVOUtil<Building>().successJSON();
        }else {
            return new DataVOUtil<Building>().errorJSON();
        }
    }

    @RequiresPermissions("building:read")
    @GetMapping("/{id}")
    public String read(Integer id){
        Building building = buildingService.getById(id);
        return new DataVOUtil<Building>().successJSON(building);
    }

    @RequiresPermissions("building:read")
    @GetMapping("/list")
    @ResponseBody
    public String list(Integer page, Integer limit){
        return buildingService.queryListByPageToJSON(page,limit);
    }

    @RequiresPermissions("building:update")
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Integer id, Model model){
        Building building = buildingService.getById(id);
        if (building==null) return "redirect:/admin";
        model.addAttribute("building",building);
        return "admin/building/edit";
    }

    @RequiresPermissions("building:update")
    @PutMapping("/{id}")
    @ResponseBody
    public String update(@PathVariable Integer id,@ModelAttribute Building building){
        boolean update = buildingService.updateById(building);;
        if (update){
            return new DataVOUtil<Building>().successJSON();
        }else {
            return new DataVOUtil<Building>().errorJSON();
        }
    }

    @RequiresPermissions("building:delete")
    @DeleteMapping("/{id}")
    @ResponseBody
    public String delete(@PathVariable Integer id){
        boolean delete = buildingService.removeById(id);;
        if (delete){
            return new DataVOUtil<Building>().successJSON();
        }else {
            return new DataVOUtil<Building>().errorJSON();
        }
    }
}
