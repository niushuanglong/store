package com.niu.web.business.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@NoArgsConstructor
@Data
@Accessors(chain = true)
@TableName("t_char_friends")
@Entity
@Table(name = "t_char_friends")
public class ChatFriends extends BaseInfo{
    private String userId;
    private String username;
    private String fUserId;

    public ChatFriends(String userId, String username, String fUserId) {
        this.userId = userId;
        this.username = username;
        this.fUserId = fUserId;
        this.setBaseInfo();
    }
}