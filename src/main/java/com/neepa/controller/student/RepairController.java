package com.neepa.controller.student;

import com.neepa.entity.Repair;
import com.neepa.service.impl.RepairServiceImpl;
import com.neepa.utils.Constants;
import com.neepa.utils.DataVOUtil;
import com.neepa.vo.RepairVO;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Api
@Controller("StudentRepairController")
@RequestMapping("/stu/repair")
public class RepairController {
    @Autowired
    RepairServiceImpl repairService;
    @GetMapping({"","/","/index"})
    public String index(){
        return "student/repair/index";
    }

    @GetMapping("create")
    public String create(){
        return "student/repair/create";
    }

    @PostMapping({"","/"})
    @ResponseBody
    public String save(@ModelAttribute Repair repair){
        repair.setStatus(100);
        boolean save = repairService.save(repair);
        if (save){
            return new DataVOUtil<Repair>().successJSON();
        }else {
            return new DataVOUtil<Repair>().errorJSON();
        }
    }

    @GetMapping("/list")
    @ResponseBody
    public String list(Integer page, Integer limit, RepairVO repairVO){
        return repairService.queryVOListByPageToJSON(page,limit,repairVO);
    }

    @GetMapping("/count")
    @ResponseBody
    public String count(Integer studentId){
        return repairService.queryCountByStatus(studentId);
    }

    @PutMapping("/confirm")
    @ResponseBody
    public String confirm_101(@RequestParam("repairId") Integer repairId,
                             @RequestParam("studentId") Integer studentId){
        Repair repair = repairService.getById(repairId);
        if(repair==null||!repair.getStatus().equals(200)||!repair.getStudentId().equals(studentId))
            return new DataVOUtil<Repair>().errorJSON();
        repair.setStatus(400);
        boolean update = repairService.updateById(repair);
        if (update) return new DataVOUtil<Repair>().successJSON();
        else return new DataVOUtil<Repair>().errorJSON();
    }
    @PutMapping("/report")
    @ResponseBody
    public String report_101(@RequestParam("repairId") Integer repairId,
                              @RequestParam("studentId") Integer studentId){
        Repair repair = repairService.getById(repairId);
        if(repair==null||!repair.getStatus().equals(200)||!repair.getStudentId().equals(studentId))
            return new DataVOUtil<Repair>().errorJSON();
        repair.setStatus(202);
        boolean update = repairService.updateById(repair);
        if (update) return new DataVOUtil<Repair>().successJSON();
        else return new DataVOUtil<Repair>().errorJSON();
    }
    @PutMapping("/withdrawn")
    @ResponseBody
    public String withdrawn(@RequestParam("repairId") Integer repairId,
                             @RequestParam("studentId") Integer studentId){
            Repair repair = repairService.getById(repairId);
            if(repair==null||!repair.getStudentId().equals(studentId))
                return new DataVOUtil<Repair>().errorJSON();
            repair.setStatus(404);
        boolean update = repairService.updateById(repair);
        if (update) return new DataVOUtil<Repair>().successJSON();
        else return new DataVOUtil<Repair>().errorJSON();
    }
    @PutMapping("/cancel")
    @ResponseBody
    public String cancel_100(@RequestParam("repairId") Integer repairId,
                         @RequestParam("studentId") Integer studentId){
        Repair repair = repairService.getById(repairId);
        if(repair==null||!repair.getStatus().equals(100)||!repair.getStudentId().equals(studentId))
            return new DataVOUtil<Repair>().errorJSON();
        repair.setStatus(104);
        boolean update = repairService.updateById(repair);
        if (update) return new DataVOUtil<Repair>().successJSON();
        else return new DataVOUtil<Repair>().errorJSON();
    }
    @PutMapping("/report/cancel")
    @ResponseBody
    public String cancel_202(@RequestParam("repairId") Integer repairId,
                         @RequestParam("studentId") Integer studentId){
        Repair repair = repairService.getById(repairId);
        if(repair==null||!repair.getStatus().equals(202)||!repair.getStudentId().equals(studentId))
            return new DataVOUtil<Repair>().errorJSON();
        repair.setStatus(200);
        boolean update = repairService.updateById(repair);
        if (update) return new DataVOUtil<Repair>().successJSON();
        else return new DataVOUtil<Repair>().errorJSON();
    }
}
