package com.rbs.project.pojo;

/**
 * @Author: 17Wang
 * @Date: 19:17 2018/12/5
 * @Description:
 */
public class RespInfo {
    private Integer status;
    private String msg;
    private Object obj;

    private RespInfo(){
    }

    private RespInfo(Integer status,String msg,Object obj) {
        this.status = status;
        this.msg = msg;
        this.obj = obj;
    }

    public static RespInfo build(){
        return new RespInfo();
    }

    public static RespInfo ok(String msg,Object obj) {
        return new RespInfo(200, msg, obj);
    }

    public static RespInfo ok(String msg) {
        return new RespInfo(200, msg, null);
    }

    public static RespInfo error(String msg,Object obj) {
        return new RespInfo(500, msg, obj);
    }

    public static RespInfo error(String msg) {
        return new RespInfo(500, msg, null);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
