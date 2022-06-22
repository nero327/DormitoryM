package com.neepa.controller.admin;

import com.neepa.entity.Repair;
import com.neepa.service.impl.RepairServiceImpl;
import com.neepa.utils.Constants;
import com.neepa.utils.DataVOUtil;
import com.neepa.vo.RepairVO;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Api
@Controller("AdminRepairController")
@RequestMapping("/admin/repair")
@RequiresRoles({Constants.StaffRole,"repair"})
public class RepairController {
    @Autowired
    RepairServiceImpl repairService;

    @RequiresPermissions("repair:read")
    @GetMapping({"","/","/index"})
    public String index(){
        return "admin/repair/index";
    }

    @RequiresPermissions("repair:create")
    @GetMapping("create")
    public String create(){
        return "admin/repair/create";
    }

    @RequiresPermissions("repair:create")
    @PostMapping({"","/"})
    @ResponseBody
    public String save(@ModelAttribute Repair repair){
        boolean save = repairService.save(repair);
        if (save){
            return new DataVOUtil<Repair>().successJSON();
        }else {
            return new DataVOUtil<Repair>().errorJSON();
        }
    }

    @RequiresPermissions("repair:read")
    @GetMapping("/{id}")
    public String read(Integer id){
        Repair repair = repairService.getById(id);
        return new DataVOUtil<Repair>().successJSON(repair);
    }

    @RequiresPermissions("repair:read")
    @GetMapping("/list")
    @ResponseBody
    public String list(Integer page, Integer limit,RepairVO repairVO){
        return repairService.queryVOListByPageToJSON(page,limit,repairVO);
    }

    @RequiresPermissions("repair:read")
    @GetMapping("/count")
    @ResponseBody
    public String count(){
        Integer staffId = (Integer) SecurityUtils.getSubject().getSession().getAttribute(Constants.StaffIdSession);
        return repairService.queryCountByStatus(staffId);
    }

    @RequiresPermissions("repair:update")
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Integer id, Model model){
        Repair repair = repairService.getById(id);
        if (repair==null) return "redirect:/admin";
        model.addAttribute("repair",repair);
        return "admin/repair/edit";
    }

    @RequiresPermissions("repair:update")
    @PutMapping("/{id}")
    @ResponseBody
    public String update(@PathVariable Integer id,@ModelAttribute Repair repair){
        boolean update = repairService.updateById(repair);;
        if (update){
            return new DataVOUtil<Repair>().successJSON();
        }else {
            return new DataVOUtil<Repair>().errorJSON();
        }
    }

    @RequiresPermissions("repair:delete")
    @DeleteMapping("/{id}")
    @ResponseBody
    public String delete(@PathVariable Integer id){
        boolean delete = repairService.removeById(id);
        if (delete){
            return new DataVOUtil<Repair>().successJSON();
        }else {
            return new DataVOUtil<Repair>().errorJSON();
        }
    }

    @RequiresPermissions("repair:update")
    @PutMapping("/{id}/confirmed")
    @ResponseBody
    public String confirmed_101(@PathVariable("id") Integer repairId){
        Repair repair = repairService.getById(repairId);
        if (repair.getStatus()!=100) return new DataVOUtil<Repair>().errorJSON();
        Integer staffId = (Integer) SecurityUtils.getSubject().getSession().getAttribute(Constants.StaffIdSession);
        repair.setStaffId(staffId);
        repair.setStatus(101);
        boolean update = repairService.updateById(repair);
        if (update) return new DataVOUtil<Repair>().successJSON();
        else return new DataVOUtil<Repair>().errorJSON();
    }

    @RequiresPermissions("repair:update")
    @PutMapping("/{id}/handled")
    @ResponseBody
    public String handled_200(@PathVariable("id") Integer repairId,@RequestParam("fee")float fee){
        Repair repair = repairService.getById(repairId);
        Integer staffId = (Integer) SecurityUtils.getSubject().getSession().getAttribute(Constants.StaffIdSession);
        if (!repair.getStaffId().equals(staffId) || !(repair.getStatus()==101 || repair.getStatus()==202)) {
            return new DataVOUtil<Repair>().errorJSON();
        }
        repair.setStatus(200);
        repair.setFee(fee);
        boolean update = repairService.updateById(repair);
        if (update) return new DataVOUtil<Repair>().successJSON();
        else return new DataVOUtil<Repair>().errorJSON();
    }

    @RequiresPermissions("repair:update")
    @PutMapping("/{id}/withdrawn")
    @ResponseBody
    public String withdrawn_404(@PathVariable("id") Integer repairId){
        Repair repair = repairService.getById(repairId);
        Integer staffId = (Integer) SecurityUtils.getSubject().getSession().getAttribute(Constants.StaffIdSession);
        if (!repair.getStaffId().equals(staffId) || repair.getStatus()!=404)
            return new DataVOUtil<Repair>().errorJSON();
        repair.setStatus(405);
        boolean update = repairService.updateById(repair);
        if (update) return new DataVOUtil<Repair>().successJSON();
        else return new DataVOUtil<Repair>().errorJSON();
    }

}
