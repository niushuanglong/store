package com.niu.web.business.utils;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.Map;

/**
 * 基于此对象封装控制层对象
 * 的响应结果,在此对象中应该
 * 包含返回到客户端的数据以及
 * 一个状态码和状态信息
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class JsonResult<T> implements Serializable {
    private static final long serialVersionUID = -5766977494287555486L;
    /**
     * 状态码
     */
    private int code = 100;//100 ok,400 error
    /**
     * 记录数
     */
    private int count = 50;//100 ok,400 error
    /**
     * 状态码对应的信息
     */
    private String msg = "";
    /**
     * 正常数据
     */
    private Object data;

    private OtherData otherData;

    public JsonResult(OtherData otherData) {
        this.otherData = otherData;
    }
    public JsonResult(String msg) {
        this.msg = msg;
    }

    public JsonResult(Object data) {
        this.data = data;
    }
    public JsonResult(Object data,OtherData otherData) {
        this.data = data;
        this.otherData=otherData;
    }
    public JsonResult(Throwable e) {
        this.code = 400;
        this.msg = e.getMessage();
    }
    public JsonResult(int code, String msg, Object data,int count ) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.count=count;
    }
    public JsonResult(int code, String msg, Object data,int count,OtherData otherData) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.count=count;
        this.otherData = otherData;
    }
    public static final <T> JsonResult<T> restapi(Object logic){
        JsonResult<T> result = new JsonResult<T>();
        try {

        } catch (Exception e) {
            e.printStackTrace();
            StringWriter writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer, true));
            String exceptionMsg = writer.toString();
            result.setMsg(e.getMessage());
        }
        return result;
    }
}



