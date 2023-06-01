package com.niu.web.business.pojo;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author niu
 * @email 2578267070@qq.com
 * @date 2023-02-26 21:46:21
 */
@Entity
@Table(
		name = "t_teacher"
)
@TableName("teacher")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TeacherEntity extends BaseInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String userId;

	/**
	 * 
	 */
	private String position;

	/**
	 * 
	 */
	private String authority;

	/**
	 * 
	 */
	private String classId;


}
