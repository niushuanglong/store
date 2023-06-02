package com.niu.web.business.pojo;

import cn.hutool.crypto.digest.SM3;
import com.baomidou.mybatisplus.annotation.*;
import com.niu.web.business.utils.IPUtils;
import com.niu.web.business.utils.SM3Utils;
import com.niu.web.business.utils.Sm4Utils;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;

/**
 * @author niushuanglong
 * @date 2022/11/17 20:35:49
 * @description
 */
@Entity
@Table(
        name = "user",
        indexes={
                @Index(name="idx_user1",columnList="Id"),
                @Index(name="idx_user2",columnList="username"),
        }
)
@TableName("user")
public class User extends BaseInfo{
    @Email
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "用户昵称")
    private String nickname;
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
    //@Column(name = "avatar",columnDefinition = "BLOB(65535)")
    private String avatar;
    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPhone() {
        return phone;
    }

    public Date getBirthday() {
        return birthday;
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

    public Long getLockedTime() {
        return lockedTime;
    }

    public int getPwdError() {
        return pwdError;
    }

    public String getAddress() {
        return address;
    }

    public String getMotto() {
        return motto;
    }

    public String getAvatar() {
        return avatar;
    }

    public User() {
    }

    //lockeTime 需要在锁定时候设置
    public User(String email, String username, String phone, String gender,Date birthday,
                String password, int valid, int pwdError, String address, String motto,
                String avatar,String nickname) {
        this.setBaseInfo();
        this.email = email;
        this.username = username;
        this.phone = phone;
        this.gender = gender;
        this.password = password;
        this.valid = valid;
        this.pwdError = pwdError;
        this.address = address;
        this.birthday=birthday;
        this.motto=motto;
        this.avatar=avatar;
        this.nickname=nickname;
    }

    /**************************************   开始领域方法       ***********************************/
    public void updatePassword(String password) {
        this.password=password;
        this.lastUpdateTime=new Date();
    }

    public void updateInfo(String nickname,String motto,String phone,String email,String avatar) {
        this.lastUpdateTime=new Date();
        this.nickname=nickname;
        this.motto=motto;
        this.phone=phone;
        this.email=email;
        this.avatar=avatar;
    }
}
