package com.neepa.vo;

import lombok.Data;

import java.util.Date;

@Data
public class MessageVO {
    private Integer id;
    private String title;
    private String content;
    private Integer buildingId;
    private String buildingName;
    private Date createTime;
    private Date updateTime;
}
