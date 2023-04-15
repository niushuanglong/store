package com.niu.web.business.pojo;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author niu
 * @email 2578267070@qq.com
 * @date 2023-02-26 21:46:21
 */
@TableName("score")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ScoreEntity extends BaseInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String studentsId;

	/**
	 * 
	 */
	private String teacherId;

	/**
	 * 
	 */
	private String courseId;

	/**
	 * 
	 */
	private Integer num;


}
