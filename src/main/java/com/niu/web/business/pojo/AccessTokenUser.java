package com.niu.web.business.pojo;


import com.baomidou.mybatisplus.annotation.TableName;
import com.niu.web.business.dto.SysDataSimpleValObj;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
@Entity
@Table(
        name = "access_token",
        indexes={
                @Index(name="idx_access_token1",columnList="userId"),

        }
)
@TableName("access_token")
public class AccessTokenUser extends BaseInfo implements Serializable {
    @ApiModelProperty(value="用户")
    private String userId;
    @ApiModelProperty(value="用户sm4加密的账号 令牌生成时的账号")
    private String account;
    //身份令牌
    private String accessToken;
    private Date expireTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public AccessTokenUser() {
    }

    public AccessTokenUser(String accessToken, Date expireTime,String account,String userId) {
        this.accessToken = accessToken;
        this.expireTime = expireTime;
        this.account=account;
        this.userId=userId;
    }


    /*************************************更新领域对象******************************************/
    public void updateExpireTime(Date expireTime) {
        this.expireTime=expireTime;
    }



}
