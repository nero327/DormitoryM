package com.neepa.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DataVO<T> implements Serializable {
    private Integer code;
    private String msg;
    private long count;
    private List<T> data;
}