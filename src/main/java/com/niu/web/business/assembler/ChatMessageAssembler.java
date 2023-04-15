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

    public List<ChatFriendsDTO> formChatFriendsDTOS(List<ChatFriends> chatFriends) {
        return chatFriends.stream().filter(Objects::nonNull).map(c -> new ChatFriendsDTO(
                c.getId(), c.getCreateTime(), c.getLastUpdateTime(), c.getIpAddress(), c.getUserId(), c.getFUserId(),
                "/images/e16f6205-2acd-43c6-8d4b-65f7432dfe7e.png",c.getUsername())
        ).collect(Collectors.toList());
    }

    public List<ChatMessageDTO> formChatMessageDTOS(List<ChatMessage> chatMessages, UserMapper userMapper) {
        UserAssembler userAssembler = new UserAssembler();
        return chatMessages.stream().filter(Objects::nonNull).map(c ->
                new ChatMessageDTO(c.getId(),c.getCreateTime(),c.getLastUpdateTime(),c.getIpAddress(),
                c.getSendUserId(),c.getReceiveUserId(),c.getMsgType(),c.getContent(),c.getReceiveDate(),
                        userAssembler.fromUserDTO(userMapper.selectById(c.getSendUserId())))
        ).collect(Collectors.toList());

    }

    public ChatMessage formChatMessageDTO(ChatMessageDTO dto) {
        return new ChatMessage(dto.getSendUserId(),dto.getReceiveUserId(),dto.getMsgType(),dto.getContent(),dto.getReceiveDate());
    }
}
