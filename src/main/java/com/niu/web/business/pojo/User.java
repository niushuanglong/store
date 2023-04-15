package com.niu.web.business.pojo;

import cn.hutool.crypto.digest.SM3;
import com.baomidou.mybatisplus.annotation.*;
import com.niu.web.business.utils.IPUtils;
import com.niu.web.business.utils.SM3Utils;
import com.niu.web.business.utils.Sm4Utils;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import java.util.Date;

/**
 * @author niushuanglong
 * @date 2022/11/17 20:35:49
 * @description
 */

@TableName("user")
public class User extends BaseInfo{
    @Email
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "用户名称")
    private String username;
    @ApiModelProperty(value = "电话号码")
    private String phone;
    @ApiModelProperty(value = "用户生日")
    private Date birthday;
    @ApiModelProperty(value = "用户性别")
    private String gender;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "是否启用")
    private int valid =0;
    @ApiModelProperty(value = "锁定时间 毫秒表示")
    private Long lockedTime;
    @ApiModelProperty(value = "密码错误次数")
    private int pwdError;
    @ApiModelProperty(value = "用户地址")
    private String address;
    @ApiModelProperty(value = "座右铭")
    private String motto;
    @ApiModelProperty(value = "用户头像")
    private String avatar;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getLockedTime() {
        return lockedTime;
    }

    public void setLockedTime(Long lockedTime) {
        this.lockedTime = lockedTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getPwdError() {
        return pwdError;
    }

    public void setPwdError(int pwdError) {
        this.pwdError = pwdError;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPhone() {
        return phone;
    }

    public String getGender() {
        return gender;
    }

    public String getPassword() {
        return password;
    }

    public int getValid() {
        return valid;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPassword(String password) {
        this.password = SM3Utils.sm3(password);
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    public User() {
    }

    //lockeTime 需要在锁定时候设置
    public User(String email, String username, String phone, String gender,Date birthday,
                String password, int valid, int pwdError, String address, String motto,String avatar) {
        this.setBaseInfo();
        this.email = email;
        this.username = username;
        this.phone = phone;
        this.gender = gender;
        this.password = SM3Utils.sm3(password);
        this.valid = valid;
        this.pwdError = pwdError;
        this.address = address;
        this.birthday=birthday;
        this.motto=motto;
        this.avatar=avatar;
    }
}
