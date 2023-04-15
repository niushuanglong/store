package com.niu.web.system.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 地址:省表
 * @author niu
 * @email 2578267070@qq.com
 * @date 2023-02-25 16:47:34
 */
@TableName("province")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProvinceEntity implements Serializable {
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


}
