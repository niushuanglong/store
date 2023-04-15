package com.niu.web.business.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.niu.web.business.utils.Sm4Utils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author niushuanglong
 * @date 2023/3/18 8:38:40
 * @description
 */
@ApiModel(value="ChatFriendsDTO ",description="当前用户好友DTO")
@JsonIgnoreProperties(ignoreUnknown=true)
@SuppressWarnings("unchecked")
@Data
public class ChatFriendsDTO extends BaseDTO{
    @ApiModelProperty(value = "当前用户id")
    private String userId;
    @ApiModelProperty(value = "好友id")
    private String fUserId;
    @ApiModelProperty(value = "用户头像")
    private String avatar;
    @ApiModelProperty(value = "用户名称")
    private String username;
    public ChatFriendsDTO(){}

    public ChatFriendsDTO(String id, Date createTime, Date lastUpdateTime, String ipAddress,
                          String userId, String fUserId, String avatar,String username) {
        super(id, createTime, lastUpdateTime, ipAddress);
        this.userId = userId;
        this.fUserId = fUserId;
        this.avatar = avatar;
        this.username= username;
    }

    public ChatFriendsDTO(String userId, String fUserId, String avatar, String username) {
        this.userId = userId;
        this.fUserId = fUserId;
        this.avatar = avatar;
        this.username = username;
    }
}
