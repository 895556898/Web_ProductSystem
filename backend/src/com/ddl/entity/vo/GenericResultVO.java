package com.ddl.entity.vo;

// 通用API响应视图对象
public class GenericResultVO {
    private String msg;    // 响应消息
    private String status; // 状态 ("ok", "error")
    private Object data;   // 携带的数据 (例如：成功时返回订单ID)

    public GenericResultVO(String msg, String status, Object data) {
        this.msg = msg;
        this.status = status;
        this.data = data;
    }

    // Getter 和 Setter
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}