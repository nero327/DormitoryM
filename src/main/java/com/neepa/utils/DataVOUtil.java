package com.neepa.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

public class DataVOUtil<T> {
    public String returnDataListJSON(List<T> data, long count){
        if (data==null){
            return this.errorJSON();
        }else{
            return this.successJSON(data,count);
        }
    }
    public String returnDataListJSON(List<T> data){
        if (data==null){
            return this.errorJSON();
        }else{
            return this.successJSON(data);
        }
    }
    public String returnDataJSON(T data){
        if (data==null){
            return this.errorJSON();
        }else{
            return this.successJSON(data);
        }
    }
    public String returnErrorMsgJSON(String msg){
        JSONObject obj = new JSONObject();
        obj.put("code",500);
        obj.put("msg",msg);
        return obj.toJSONString();
    }
    public String returnSuccessMsgJSON(String msg){
        JSONObject obj = new JSONObject();
        obj.put("code",200);
        obj.put("msg",msg);
        return obj.toJSONString();
    }
    public String successJSON(List<T> data,long count){
        JSONObject obj = new JSONObject();
        obj.put("code",200);
        obj.put("msg","请求成功");
        obj.put("count",count);
        obj.put("data",data);
        return obj.toJSONString();
    }
    public String successJSON(List<T> data){
        JSONObject obj = new JSONObject();
        obj.put("code",200);
        obj.put("msg","请求成功");
        obj.put("count",0);
        obj.put("data",data);
        return obj.toJSONString();
    }
    public String successJSON(T data){
        JSONObject obj = new JSONObject();
        obj.put("code",200);
        obj.put("msg","请求成功");
        obj.put("data",data);
        return obj.toJSONString();
    }
    public String successJSON(){
        JSONObject obj = new JSONObject();
        obj.put("code",200);
        obj.put("msg","请求成功");
        return obj.toJSONString();
    }
    public String errorJSON(){
        JSONObject obj = new JSONObject();
        obj.put("code",500);
        obj.put("msg","请求失败");
        return obj.toJSONString();
    }
}
