package com.neepa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neepa.entity.Message;

public interface MessageService extends IService<Message> {
    String queryVOListByPageToJSON(Integer page, Integer limit,String studentId);
}
