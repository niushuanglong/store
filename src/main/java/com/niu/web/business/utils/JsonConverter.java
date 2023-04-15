package com.niu.web.business.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;

/**
 *
 * JSON 转换
 * @author wq
 */
public class JsonConverter {
    private static ObjectMapper objectMapper = new ObjectMapper();
    static{
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }
    /**
     * 根据对象获取json字符串
     * @param obj
     * @return
     */
    public static final String toJsonStr(Object obj){
        try{
            if(obj==null){
                return "";
            }
            return objectMapper.writeValueAsString(obj);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据jsonstr获取对象
     * @param jsonStr
     * @param c
     * @return
     * @return
     */
    public static final <T> T jsonStrToObject(String jsonStr,Class<T> c){
        try{
            if(StringUtils.isBlank(jsonStr)){
                return null;
            }
            return objectMapper.readValue(jsonStr,c);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据jsonstr获取对象集合
     * @param <T>
     * @param jsonStr
     * @return
     */
    public static final <T> T jsonStrToObject(String jsonStr, TypeReference<T> valueTypeRef) {
        try{
            if(StringUtils.isBlank(jsonStr)){
                return null;
            }
            return objectMapper.readValue(jsonStr, valueTypeRef);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
