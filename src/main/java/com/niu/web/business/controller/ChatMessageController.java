package com.niu.web.business.controller;

import com.niu.web.business.assembler.AccessTokenAssembler;
import com.niu.web.business.dto.AccessTokenUserDTO;
import com.niu.web.business.dto.ChatFriendsDTO;
import com.niu.web.business.service.ChatMessageService;
import com.niu.web.business.utils.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author niushuanglong
 * @date 2023/3/17 18:57:08
 * @description
 */
@RequestMapping("/api/chat")
@RestController
@Api("聊天室接口集")
public class ChatMessageController {
    @Autowired
    private ChatMessageService chatMessageService;


    @ApiOperation("查找当前用户好友")
    @RequestMapping(value = "/findFriends/{userId}", method = RequestMethod.POST)
    public JsonResult findFriends(@PathVariable String userId,HttpServletRequest request) {
        AccessTokenUserDTO accessTokenUserFromReq = new AccessTokenAssembler().getAccessTokenUserFromReq(request);
        return chatMessageService.findFriends(userId);
    }
    @ApiOperation("获取好友聊天记录")
    @RequestMapping(value = "/queryFriendsMsg/{receiveUserId}/{userId}", method = RequestMethod.POST)
    public JsonResult queryFriendsMsg(@PathVariable("receiveUserId") String receiveUserId,@PathVariable("userId")String userId,
                                      HttpSession session, HttpServletRequest request) {
        return chatMessageService.queryFriendsMsg(userId,receiveUserId);
    }

    @ApiOperation("添加好友")
    @RequestMapping(value = "/adduser/{userId}/{receiveUserId}", method = RequestMethod.POST)
    public JsonResult adduser(@PathVariable("userId") String userId,@PathVariable("receiveUserId")String receiveUserId) {
        return chatMessageService.adduser(userId,receiveUserId);
    }
    @ApiOperation("查询好友")
    @RequestMapping(value = "/queryUser", method = RequestMethod.GET)
    public JsonResult queryUser(@RequestParam("username")String username,@RequestParam("userId")String userId) {
        return chatMessageService.queryUser(username,userId);
    }
    @ApiOperation("上传聊天图片")
    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    public JsonResult uploadImg(@RequestParam("file")MultipartFile file) {
        return chatMessageService.uploadImg(file);
    }

}
