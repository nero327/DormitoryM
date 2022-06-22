package com.neepa.controller.student;

import com.neepa.service.impl.BuildingServiceImpl;
import com.neepa.service.impl.MessageServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Api
@Controller("StudentMessageController")
@RequestMapping("/stu/message")
public class MessageController {
    @Autowired
    MessageServiceImpl messageService;
    @Autowired
    BuildingServiceImpl buildingService;

    @GetMapping({"","/","/index"})
    public String index(){
        return "student/message/index";
    }
    @GetMapping("/list")
    @ResponseBody
    public String list(Integer page, Integer limit,String studentId){
        return messageService.queryVOListByPageToJSON(page,limit,studentId);
    }


}
