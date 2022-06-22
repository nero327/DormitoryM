package com.neepa.controller.admin;

import com.neepa.entity.Building;
import com.neepa.entity.Message;
import com.neepa.service.impl.BuildingServiceImpl;
import com.neepa.service.impl.MessageServiceImpl;
import com.neepa.utils.Constants;
import com.neepa.utils.DataVOUtil;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@Controller("AdminMessageController")
@RequestMapping("/admin/message")
@RequiresRoles({Constants.StaffRole,"msg"})
public class MessageController {
    @Autowired
    MessageServiceImpl messageService;
    @Autowired
    BuildingServiceImpl buildingService;

    @RequiresPermissions("msg:read")
    @GetMapping({"","/","/index"})
    public String index(){

        return "admin/message/index";
    }

    @RequiresPermissions("msg:create")
    @GetMapping("create")
    public String create(Model model){
        List<Building> buildingList = buildingService.list();
        model.addAttribute("buildingList",buildingList);
        return "admin/message/create";
    }

    @RequiresPermissions("msg:create")
    @PostMapping({"","/"})
    @ResponseBody
    public String save(@ModelAttribute Message message){
        boolean save = messageService.save(message);
        if (save){
            return new DataVOUtil<Message>().successJSON();
        }else {
            return new DataVOUtil<Message>().errorJSON();
        }
    }

    @RequiresPermissions("msg:read")
    @GetMapping("/{id}")
    public String read(Integer id){
        Message message = messageService.getById(id);
        return new DataVOUtil<Message>().successJSON(message);
    }

    @RequiresPermissions("msg:read")
    @GetMapping("/list")
    @ResponseBody
    public String list(Integer page, Integer limit){
        return messageService.queryVOListByPageToJSON(page,limit,null);
    }

    @RequiresPermissions("msg:update")
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Integer id, Model model){
        Message message = messageService.getById(id);
        if (message==null) return "redirect:/admin";
        model.addAttribute("message",message);
        List<Building> buildingList = buildingService.list();
        model.addAttribute("buildingList",buildingList);
        return "admin/message/edit";
    }

    @RequiresPermissions("msg:update")
    @PutMapping("/{id}")
    @ResponseBody
    public String update(@PathVariable Integer id,@ModelAttribute Message message){
        boolean update = messageService.updateById(message);
        if (update){
            return new DataVOUtil<Message>().successJSON();
        }else {
            return new DataVOUtil<Message>().errorJSON();
        }
    }

    @RequiresPermissions("msg:delete")
    @DeleteMapping("/{id}")
    @ResponseBody
    public String delete(@PathVariable Integer id){
        boolean delete = messageService.removeById(id);;
        if (delete){
            return new DataVOUtil<Message>().successJSON();
        }else {
            return new DataVOUtil<Message>().errorJSON();
        }
    }
}
