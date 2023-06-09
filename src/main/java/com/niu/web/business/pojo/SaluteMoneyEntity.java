package com.niu.web.business.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import org.springframework.data.redis.core.index.GeoIndexed;

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
@Data
public class SaluteMoneyEntity{
    @Id
    @GeneratedValue
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    protected String name;//姓名
    protected Date time;//时间
    protected String salute;//礼金
    protected String reason;//事由
    protected String funeralOrHappy;//丧事or喜事


    public SaluteMoneyEntity(String name, Date time, String salute, String reason, String funeralOrHappy) {
        this.name = name;
        this.time = time;
        this.salute = salute;
        this.reason = reason;
        this.funeralOrHappy = funeralOrHappy;
    }

    public void update(String name, Date time, String salute, String reason, String funeralOrHappy) {
        this.name = name;
        this.time = time;
        this.salute = salute;
        this.reason = reason;
        this.funeralOrHappy = funeralOrHappy;
    }
}
