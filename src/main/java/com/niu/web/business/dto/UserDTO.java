package com.niu.web.business.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.niu.web.business.utils.Sm4Utils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import java.util.Date;

/**
 * @author niushuanglong
 * @date 2022/11/17 20:23:13
 * @description
 */
@ApiModel("UserDTO")
public class UserDTO extends BaseDTO{
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "用户名称")
    private String username;
    @ApiModelProperty(value = "电话号码")
    private String phone;
    @ApiModelProperty(value = "用户年龄")
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date birthday;
    @ApiModelProperty(value = "用户性别")
    private String gender;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "是否启用")
    private Integer valid =0;
    @ApiModelProperty(value = "锁定时间 毫秒表示")
    private Long lockedTime;
    @ApiModelProperty(value = "密码错误次数")
    private Integer pwdError;
    @ApiModelProperty(value = "用户地址")
    private String address;
    @ApiModelProperty(value = "座右铭")
    private String motto;
    @ApiModelProperty(value = "用户照片")
    private AttachmentValObj attachmentValObj;
    @ApiModelProperty(value = "用户照片")
    private String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public AttachmentValObj getAttachmentValObj() {
        return attachmentValObj;
    }

    public void setAttachmentValObj(AttachmentValObj attachmentValObj) {
        this.attachmentValObj = attachmentValObj;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public Long getLockedTime() {
        return lockedTime;
    }

    public void setLockedTime(Long lockedTime) {
        this.lockedTime = lockedTime;
    }

    public Integer getPwdError() {
        return pwdError;
    }

    public void setPwdError(Integer pwdError) {
        this.pwdError = pwdError;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserDTO() {
    }

    public UserDTO(String id, Date createTime, Date lastUpdateTime, String ipAddress, String email, String username, String phone,
                   Date birthday, String gender, String password, Integer valid, Long lockedTime, Integer pwdError, String address,String motto,String img) {
        super(id, createTime, lastUpdateTime, ipAddress);
        this.email = email;
        this.username = username;
        this.phone = phone;
        this.birthday = birthday;
        this.gender = gender;
        this.password = password;
        this.valid = valid;
        this.lockedTime = lockedTime;
        this.pwdError = pwdError;
        this.address = address;
        this.motto=motto;
        this.img=img;
    }

}
