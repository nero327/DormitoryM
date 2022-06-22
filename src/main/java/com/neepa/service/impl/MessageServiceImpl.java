package com.neepa.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neepa.mapper.MessageMapper;
import com.neepa.entity.Message;
import com.neepa.service.MessageService;
import com.neepa.utils.DataVOUtil;
import com.neepa.vo.MessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {
    @Autowired
    MessageMapper messageMapper;
    @Override
    public String queryVOListByPageToJSON(Integer page, Integer limit,String studentId) {
        IPage<MessageVO> messageVOPage=null;
        if (page != null && limit != null) {
            messageVOPage = new Page<>(page, limit);
        }
        List<MessageVO> messageVOList = messageMapper.selectVoListByPage(messageVOPage,studentId);
        return new DataVOUtil<MessageVO>().returnDataListJSON(messageVOList, messageVOPage.getTotal());
    }
}
