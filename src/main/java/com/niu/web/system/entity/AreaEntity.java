package com.niu.web.system.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 地址:区表
 * @author niu
 * @email 2578267070@qq.com
 * @date 2023-02-25 16:47:34
 */
@TableName("area")
public class AreaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/**
	 * 
	 */
	private String code;

	/**
	 * 
	 */
	private String name;

	/**
	 * 
	 */
	private String citycode;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCitycode() {
		return citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public AreaEntity() {
	}

	public AreaEntity(Integer id, String code, String name, String citycode) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.citycode = citycode;
	}
}
