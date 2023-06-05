package com.niu.web.business.assembler;

import com.niu.web.business.dto.ChatFriendsDTO;
import com.niu.web.business.dto.ChatMessageDTO;
import com.niu.web.business.mapper.UserMapper;
import com.niu.web.business.pojo.ChatFriends;
import com.niu.web.business.pojo.ChatMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author niushuanglong
 * @date 2023/3/18 8:45:14
 * @description
 */
public class ChatMessageAssembler {

    public List<ChatMessageDTO> formChatMessageDTOS(List<ChatMessage> chatMessages, UserMapper userMapper) {
        UserAssembler userAssembler = new UserAssembler();
        return chatMessages.stream().filter(Objects::nonNull).map(c ->
                new ChatMessageDTO(c.getId(),c.getCreateTime(),c.getLastUpdateTime(),c.getIpAddress(),
                c.getSendUserId(),c.getReceiveUserId(),c.getMsgType(),c.getContent(),c.getReceiveDate(),
                        userAssembler.fromUserDTO(userMapper.selectById(c.getSendUserId())))
        ).collect(Collectors.toList());

    }

    public ChatMessage toChatMessageDTO(ChatMessageDTO dto) {
        return new ChatMessage(dto.getSendUserId(),dto.getReceiveUserId(),dto.getMsgType(),dto.getContent(),dto.getReceiveDate());
    }
}
