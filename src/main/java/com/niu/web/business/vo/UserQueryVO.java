package com.niu.web.business.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author niushuanglong
 * @date 2023/4/9 22:44:28
 * @description
 */
@ApiModel(value="UserQueryVO ",description="网站用户信息查询VO")
@JsonIgnoreProperties(ignoreUnknown=true)
public class UserQueryVO extends PageForm{
    @ApiModelProperty("id")
    public String id;
    @ApiModelProperty("用户名")
    public String username;
    @ApiModelProperty("邮箱")
    public String email;
    @ApiModelProperty("性别")
    public String gender;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public UserQueryVO() {
    }

    public UserQueryVO(String id, String username, String email, String gender) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.gender = gender;
    }
}
