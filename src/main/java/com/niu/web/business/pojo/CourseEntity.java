package com.niu.web.business.pojo;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author niu 课程表
 * @email 2578267070@qq.com
 * @date 2023-02-26 21:46:21
 */
@Entity
@Table(
		name = "course"
)
@TableName("course")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CourseEntity extends BaseInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 课程表名称
	 */
	private String name;

	/**
	 * 老师Id
	 */
	private Integer teacherId;



}
