package com.niu.web.business.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;

import io.swagger.annotations.ApiModel;

import java.util.HashMap;
import java.util.Map;

@ApiModel(value="OtherInfo ",description="其他信息 对象")
@JsonIgnoreProperties(ignoreUnknown=true)
@SuppressWarnings("unchecked")
public class OtherData extends HashMap<String,Object>{
    private static final long serialVersionUID = 1L;

    //缓存的 转换后的特定类型  数据
    @JsonIgnore
    private Map<String,Object> ctypeNameToData=new HashMap<String,Object>();

    //获取指定类型的对象
    public <T> T obtainVal(String key) {
        Object value=this.get(key);
        return (T)value;
    }
    //获取指定类型的对象
    public <T> T obtainVal(String key, Class<T> valClass) {
        if(valClass==null) {
            throw new RuntimeException("未指定返回值Class类型");
        }
        Object value=this.get(key);
        if(value==null||valClass.getName().equals(value.getClass().getName())) {
            return (T)value;
        }
        String ctypeName=key+"_"+valClass.getName();
        synchronized (value) {
            T relVal=(T)this.ctypeNameToData.get(ctypeName);
            if(relVal!=null) {
                return relVal;
            }
            //relVal=JsonConverter.jsonStrToObject(JsonConverter.toJsonStr(value),valClass);

            this.ctypeNameToData.put(ctypeName, relVal);
            return relVal;
        }
    }
    //获取指定转换类型的对象
    public <T> T obtainVal(String key, TypeReference<T> valTypeRef) {
        if(valTypeRef==null) {
            throw new RuntimeException("未指定返回值TypeReference转换类型");
        }
        Object value=this.get(key);
        if(value==null) {
            return (T)value;
        }
        String ctypeName=key+"_"+valTypeRef.getType();
        synchronized (value) {
            T relVal=(T)this.ctypeNameToData.get(ctypeName);
            if(relVal!=null) {
                return relVal;
            }
            //relVal= JsonConverter.jsonStrToObject(JsonConverter.toJsonStr(value),valTypeRef);

            this.ctypeNameToData.put(ctypeName, relVal);
            return relVal;
        }
    }

}

