package com.niu.web.business.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author niushuanglong
 * @date 2023/3/8 22:05:20
 * @description
 */
@ApiModel(value="LoginVO ",description="登录信息")
@JsonIgnoreProperties(ignoreUnknown=true)
public class LoginVO {
    @ApiModelProperty(value="用户名")
    private String username;
    @ApiModelProperty(value="密码")
    private String password;
    @ApiModelProperty(value="验证码")
    private String captcha;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public LoginVO() {
    }

    public LoginVO(String username, String password, String captcha) {
        this.username = username;
        this.password = password;
        this.captcha = captcha;
    }
}
