package com.niu.web.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.niu.web.business.pojo.ChatMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author niu
 * @email 2578267070@qq.com
 * @date 2023-03-17 18:52:14
 */

public interface ChatMessageDao extends BaseMapper<ChatMessage> {

    List<ChatMessage> LookTwoUserMsg(@Param("userId")String userId,@Param("receiveUserId") String receiveUserId);
}
