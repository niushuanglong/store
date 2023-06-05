package com.niu.web.business.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author niushuanglong
 * @date 2023/6/3 11:56:06
 * @description
 */
@ApiModel("SaluteDTO")
@JsonIgnoreProperties(ignoreUnknown=true)
@Getter
@Setter
public class SaluteDTO implements Serializable {
    @ApiModelProperty(value = "id")
    protected String id;
    @ApiModelProperty(value = "姓名")
    protected String name;
    @ApiModelProperty(value = "时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    protected Date time;
    @ApiModelProperty(value = "礼金")
    protected String salute;
    @ApiModelProperty(value = "事由")
    protected String reason;
    @ApiModelProperty(value = "丧事or喜事")
    protected String funeralOrHappy;



    public SaluteDTO(String id, String name, Date time, String salute, String reason, String funeralOrHappy) {
        this.id=id;
        this.name = name;
        this.time = time;
        this.salute = salute;
        this.reason = reason;
        this.funeralOrHappy = funeralOrHappy;
    }
}
