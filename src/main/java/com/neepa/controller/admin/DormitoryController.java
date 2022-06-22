package com.neepa.controller.admin;

import com.neepa.entity.Building;
import com.neepa.entity.Dormitory;
import com.neepa.service.impl.BuildingServiceImpl;
import com.neepa.service.impl.DormitoryServiceImpl;
import com.neepa.utils.Constants;
import com.neepa.utils.DataVOUtil;
import com.neepa.vo.StudentVO;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api
@Controller("AdminDormitoryController")
@RequestMapping("/admin/dormitory")
@RequiresRoles({Constants.StaffRole,"dormitory"})
public class DormitoryController {
    @Autowired
    DormitoryServiceImpl dormitoryService;
    @Autowired
    BuildingServiceImpl buildingService;

    @RequiresPermissions("dormitory:read")
    @GetMapping({"","/","/index"})
    public String index(Model model){
        List<Building> buildingList = buildingService.list();
        model.addAttribute("buildingList",buildingList);
        return "admin/dormitory/index";
    }

    @RequiresPermissions("dormitory:create")
    @GetMapping("create")
    public String create(Model model){
        List<Building> buildingList = buildingService.list();
        model.addAttribute("buildingList",buildingList);
        return "admin/dormitory/create";
    }

    @RequiresPermissions("dormitory:create")
    @PostMapping({"","/"})
    @ResponseBody
    public String save(@ModelAttribute Dormitory dormitory,@RequestParam(name = "count",required=false) Integer count){
        boolean save;
        dormitory.setAvailable(dormitory.getMax());
        if(count==null){
            save = dormitoryService.save(dormitory);
        }else {
            List<Dormitory> dormitoryList = new ArrayList<>();
            for (int i = 1; i <= count; i++) {
                dormitory.setNumber(i);
                Dormitory d = new Dormitory();
                BeanUtils.copyProperties(dormitory, d);
                dormitoryList.add(d);
            }
            save = dormitoryService.saveBatch(dormitoryList);
        }
        if (save){
            return new DataVOUtil<Dormitory>().successJSON();
        }else {
            return new DataVOUtil<Dormitory>().errorJSON();
        }
    }

    @RequiresPermissions("dormitory:read")
    @GetMapping("/{id}")
    public String read(Integer id){
        Dormitory dormitory = dormitoryService.getById(id);
        return new DataVOUtil<Dormitory>().successJSON(dormitory);
    }

    @RequiresPermissions("dormitory:read")
    @GetMapping("/list")
    @ResponseBody
    public String list(@RequestParam(value = "page",required = false) Integer page,
                       @RequestParam(value = "limit",required = false) Integer limit,
                       @RequestParam(value = "buildingId",required = false) Integer buildingId,
                       @RequestParam(value = "floor",required = false) Integer floor,
                       @RequestParam(value = "number",required = false) Integer number){
        if(buildingId!=null||floor!=null||number!=null)
            return dormitoryService.queryListByConditionToJSON(page,limit,buildingId,floor,number);
        return dormitoryService.queryListByPageToJSON(page,limit);
    }

    @RequiresPermissions("dormitory:update")
    @PutMapping("/{id}")
    @ResponseBody
    public String update(@PathVariable Integer id,@ModelAttribute Dormitory dormitory){
        boolean update = dormitoryService.updateById(dormitory);;
        if (update){
            return new DataVOUtil<Dormitory>().successJSON();
        }else {
            return new DataVOUtil<Dormitory>().errorJSON();
        }
    }

    @RequiresPermissions("dormitory:delete")
    @GetMapping("/delete")
    public String delete(Model model){
        List<Building> buildingList = buildingService.list();
        model.addAttribute("buildingList",buildingList);
        return "/admin/dormitory/delete";
    }

    @RequiresPermissions("dormitory:delete")
    @DeleteMapping("/delete")
    @ResponseBody
    public String deleteBatch(Integer buildingId,Integer floor){
        boolean delete = dormitoryService.deleteBatch(buildingId, floor);
        if (delete){
            return new DataVOUtil<Dormitory>().successJSON();
        }else {
            return new DataVOUtil<Dormitory>().errorJSON();
        }
    }

    @RequiresPermissions("dormitory:delete")
    @DeleteMapping("/{id}")
    @ResponseBody
    public String delete(@PathVariable Integer id){
        boolean delete = dormitoryService.removeById(id);;
        if (delete){
            return new DataVOUtil<Dormitory>().successJSON();
        }else {
            return new DataVOUtil<Dormitory>().errorJSON();
        }
    }

    @RequiresPermissions("dormitory:read")
    @GetMapping("/getFloorsByBuildingId")
    @ResponseBody
    public String getFloorsByBuildingId(Integer id){
        Building building = buildingService.getById(id);
        if(building==null)return new DataVOUtil<Integer>().errorJSON();
        return new DataVOUtil<Integer>().returnDataJSON(building.getFloors());
    }

    @RequiresPermissions("dormitory:read")
    @GetMapping("/getNumberByInfo")
    @ResponseBody
    public String getNumberByInfo(Integer buildingId,Integer floor){
        List<Dormitory> dormitoryList = dormitoryService.getAvailableByInfo(buildingId, floor);
        if(dormitoryList==null)return new DataVOUtil<Integer>().errorJSON();
        return new DataVOUtil<Dormitory>().returnDataListJSON(dormitoryList);
    }

    @RequiresPermissions("dormitory:read")
    @GetMapping("/{id}/student")
    public String studentByDormitoryId(@PathVariable("id") Integer id,Model model){
        model.addAttribute("dormitoryId",id);
        return "/admin/dormitory/student";
    }

    @RequiresPermissions("dormitory:read")
    @GetMapping("/{id}/list")
    @ResponseBody
    public String getStudentListByDormitoryId(@PathVariable("id") Integer id){
        List<StudentVO> studentVOList = dormitoryService.queryStudentListById(id);
        return new DataVOUtil<StudentVO>().returnDataListJSON(studentVOList);
    }
}

