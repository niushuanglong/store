package com.niu.web.business.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;

/**
 * @author niushuanglong
 * @date 2023/3/9 10:35:17
 * @description
 */
@ApiModel(value="LoginParamDTO ",description="登录验证码DTO")
@JsonIgnoreProperties(ignoreUnknown=true)
@SuppressWarnings("unchecked")
public class LoginParamDTO {
    public String key;
    public String code;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LoginParamDTO() {
    }

    public LoginParamDTO(String key, String code) {
        this.key = key;
        this.code = code;
    }
}
