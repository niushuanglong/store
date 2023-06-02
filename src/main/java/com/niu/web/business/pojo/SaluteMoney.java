package com.niu.web.business.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author niushuanglong
 * @date 2023/6/2 22:10:20
 * @description 礼金记录
 */
@Entity
@Table(
        name = "t_salute_money"
)
@TableName("t_salute_money")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SaluteMoney extends BaseInfo{
    protected String name;//姓名
    protected Date time;//时间
    protected String salute;//礼金
    protected String reason;//事由
    protected String funeralOrHappy;//丧事or喜事

}
