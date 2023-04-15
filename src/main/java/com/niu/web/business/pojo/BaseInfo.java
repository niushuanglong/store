package com.niu.web.business.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.niu.web.business.utils.IPUtils;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @author niushuanglong
 * @date 2023/2/26 10:25:11
 * @description 基础信息
 */
@MappedSuperclass
public abstract class BaseInfo {
    @Id
    @GeneratedValue
    @TableId(type = IdType.ASSIGN_ID)
    public String id;
    @TableField(fill = FieldFill.INSERT)
    public Date createTime;//日期
    @ApiModelProperty(value = "最后修改时间", hidden = true)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    public Date lastUpdateTime;
    @ApiModelProperty(value = "用户IP地址")
    public String ipAddress;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public BaseInfo() {
    }

    public void  setBaseInfo() {
        this.createTime = new Date();
        this.lastUpdateTime = new Date();
        this.ipAddress = IPUtils.getIpAddr();
    }
}
