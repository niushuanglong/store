package com.niu.web.business.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;


@NoArgsConstructor
@Data
@Accessors(chain = true)
@Entity
@Table(
        name = "t_char_message",
        indexes={
                @Index(name="idx_T_CHAR_MESSAGE1",columnList="sendUserId"),
                @Index(name="idx_T_CHAR_MESSAGE2",columnList="receiveUserId"),
        }
)
@TableName("t_char_message")
public class ChatMessage extends BaseInfo{
    private String sendUserId;
    private String receiveUserId;
    //消息类型 文本消息 图片消息  MSG_TYPE_TEXT MSG_TYPE_IMAGE
    private String msgType;
    //消息内容
    private String content;
    //接收时间
    private Date receiveDate;

    public ChatMessage(String sendUserId, String receiveUserId, String msgType, String content, Date receiveDate) {
        this.sendUserId = sendUserId;
        this.receiveUserId = receiveUserId;
        this.msgType = msgType;
        this.content = content;
        this.receiveDate = receiveDate;
    }
}
