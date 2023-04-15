package com.niu.web.business.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SysDataSimpleValObj implements Serializable {
	private static final long serialVersionUID = 4932371676138097751L;
	
	private String id;//系统数据ID
	private String name;//系统数据name

    public static SysDataSimpleValObj buildByMap(Map<String, Object> map) {
    	return new SysDataSimpleValObj(Optional.ofNullable(map).map(v -> (String) v.get("id")).orElse(null),
				Optional.ofNullable(map).map(v -> (String) v.get("name")).orElse(null));
    }


    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    
    
    public SysDataSimpleValObj() {
        super();
    }
    public SysDataSimpleValObj(String id, String name) {
        super();
        this.id = id;
        this.name = name;
    }
    public SysDataSimpleValObj(String id, String name, OtherData otherData) {
        super();
        this.id = id;
        this.name = name;
        this.otherData = otherData;
    }
    
    @JsonIgnore
    private OtherData otherData;
    public OtherData getNotNullOtherData() {
    	if(otherData==null) return new OtherData();
    	return otherData;
    }
    public void putOtherData(String key, Object data) {
    	if(otherData==null) otherData=new OtherData();
    	otherData.put(key, data);
    }
    public void putOtherData(Map<String,Object> datas) {
    	if(otherData==null) otherData=new OtherData();
    	otherData.putAll(datas);
    }
    
    @Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SysDataSimpleValObj other = (SysDataSimpleValObj) obj;
		if(StringUtils.isEmpty(this.id) && StringUtils.isEmpty(other.id)) return true;
		return Optional.ofNullable(this.id).orElse("")
				.equals(Optional.ofNullable(other).map(o -> o.getId()).orElse(""));
	}
	
}
