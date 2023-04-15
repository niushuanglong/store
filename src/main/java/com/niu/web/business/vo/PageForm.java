package com.niu.web.business.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="PageForm",description="分页查询基础类")
@JsonIgnoreProperties(ignoreUnknown=true)
public abstract class PageForm {
	
    @ApiModelProperty(value="所查询的页码")
	protected int pageNo = 1;
    @ApiModelProperty(value="所查询的每页多少条")
	protected int pageSize = 20;
    @ApiModelProperty(value="排序属性  根据业务定义")
    protected String orderKey;
    @ApiModelProperty(value="排序类型  asc/desc")
    protected String orderType;
    
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getOrderKey() {
		return orderKey;
	}
	public void setOrderKey(String orderKey) {
		this.orderKey = orderKey;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
}
