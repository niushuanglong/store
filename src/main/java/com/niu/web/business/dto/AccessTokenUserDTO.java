package com.niu.web.business.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author PC
 * @date 2022/9/27 22:29:24
 * @description 用户令牌DTO
 */
@ApiModel("AccessTokenUserDTO")
@JsonIgnoreProperties(ignoreUnknown=true)
public class AccessTokenUserDTO implements Serializable {
    private static final String DEPT = "dept";
    //private static final String ORG = "dept";

    @ApiModelProperty(value = "令牌")
    private String accessToken;
    @ApiModelProperty(value = "超时时间")
    private Date expireTime;
    @ApiModelProperty(value = "用户")
    private UserDTO user;
    @ApiModelProperty(value = "用户其他信息")
    private OtherData userInfo;

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public OtherData getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(OtherData userInfo) {
        this.userInfo = userInfo;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }


    public AccessTokenUserDTO() {
    }

    public AccessTokenUserDTO(String accessToken, Date expireTime, UserDTO user,  OtherData userInfo) {
        this.accessToken = accessToken;
        this.expireTime = expireTime;
        this.user = user;
        this.userInfo = userInfo;
    }

    /*********************************  开始领域方法 *************************************/
    //获取用户id
    public String getUserId() {
        if(this.user==null) {
            return null;
        }
        return this.user.getId();
    }
    //获取用户name
    public String getUserName() {
        if(this.user==null) {
            return null;
        }
        return this.user.getUsername();
    }
    //获取用户部门
    public UserDTO obtainDept() {
        UserDTO dept=userInfo.obtainVal(DEPT, UserDTO.class);
        if(dept==null) {
            return null;
        }
        return dept;
    }
    //获取用户部门id
    public String getDeptId() {
        UserDTO dept=userInfo.obtainVal(DEPT, UserDTO.class);
        if(dept==null) {
            return null;
        }
        return dept.getId();
    }
    //获取用户部门name
    public String getDeptName() {
        UserDTO dept=userInfo.obtainVal(DEPT, UserDTO.class);
        if(dept==null) {
            return null;
        }
        return dept.getUsername();
    }
//    //获取用户机构
//    public SysDataSimpleValObj obtainOrg() {
//        SysDataSimpleValObj org=userInfo.obtainVal(ORG, SysDataSimpleValObj.class);
//        if(org==null) {
//            return null;
//        }
//        return org;
//    }
//    //获取用户机构id
//    public String getOrgId() {
//        SysDataSimpleValObj org=userInfo.obtainVal(ORG, SysDataSimpleValObj.class);
//        if(org==null) {
//            return null;
//        }
//        return org.getId();
//    }
//    //获取用户机构名称
//    public String getOrgName() {
//        SysDataSimpleValObj org=userInfo.obtainVal(ORG, SysDataSimpleValObj.class);
//        if(org==null) {
//            return null;
//        }
//        return org.getName();
//    }




}
