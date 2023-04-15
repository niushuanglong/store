package com.niu.web.business.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * @author niushuanglong
 * @date 2023/3/19 15:37:52
 * @description
 */
@ApiModel(value="ChatMessageDTO ",description="聊天消息DTO")
@JsonIgnoreProperties(ignoreUnknown=true)
@SuppressWarnings("unchecked")
@Data
public class ChatMessageDTO extends BaseDTO{
    private String sendUserId;
    private String receiveUserId;
    //消息类型 文本消息 图片消息  MSG_TYPE_TEXT MSG_TYPE_IMAGE
    private String msgType;
    //消息内容
    private String content;
    //接收时间
    private Date receiveDate;
    public UserDTO userDTO;//sendUserId用户的信息

    public ChatMessageDTO(String id, Date createTime, Date lastUpdateTime, String ipAddress,
                          String sendUserId, String receiveUserId, String msgType, String content,
                          Date receiveDate,UserDTO userDTO) {
        super(id, createTime, lastUpdateTime, ipAddress);
        this.sendUserId = sendUserId;
        this.receiveUserId = receiveUserId;
        this.msgType = msgType;
        this.content = content;
        this.receiveDate = receiveDate;
        this.userDTO=userDTO;
    }
}
