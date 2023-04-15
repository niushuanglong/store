package com.niu.web.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.niu.web.business.dto.AccessTokenUserDTO;
import com.niu.web.business.dto.ChatFriendsDTO;
import com.niu.web.business.dto.ChatMessageDTO;
import com.niu.web.business.pojo.ChatMessage;
import com.niu.web.business.utils.JsonResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 
 *
 * @author niu
 * @email 2578267070@qq.com
 * @date 2023-03-17 18:52:14
 */
public interface ChatMessageService extends IService<ChatMessage> {

    JsonResult findFriends(String userId);


    JsonResult queryFriendsMsg(String userId,String receiveUserId);

    void insertCharMessage(ChatMessageDTO dto);

    JsonResult uploadImg(MultipartFile request);

    JsonResult queryUser(String username,String userId);

    JsonResult adduser(String userId,String receiveUserId);
}

