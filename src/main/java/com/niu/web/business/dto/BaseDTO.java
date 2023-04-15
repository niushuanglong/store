package com.niu.web.business.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author niushuanglong
 * @date 2023/2/25 20:21:57
 * @description
 */
@ApiModel("BaseDTO")
@JsonIgnoreProperties(ignoreUnknown=true)
public class BaseDTO {
    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "创建日期")
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    private Date createTime;
    @ApiModelProperty(value = "最后修改时间", hidden = true)
    private Date lastUpdateTime;
    @ApiModelProperty(value = "用户IP地址")
    private String ipAddress;


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

    public BaseDTO() {
    }

    public BaseDTO(String id, Date createTime, Date lastUpdateTime, String ipAddress) {
        this.id = id;
        this.createTime = createTime;
        this.lastUpdateTime = lastUpdateTime;
        this.ipAddress = ipAddress;
    }
}
