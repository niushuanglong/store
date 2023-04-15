package com.niu.web.business.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niu.web.business.SYSCONSTANT.Constant;
import com.niu.web.business.assembler.AttachmentAssembler;
import com.niu.web.business.assembler.ChatMessageAssembler;
import com.niu.web.business.assembler.UserAssembler;
import com.niu.web.business.dto.AttachmentValObj;
import com.niu.web.business.dto.ChatFriendsDTO;
import com.niu.web.business.dto.ChatMessageDTO;
import com.niu.web.business.dto.UserDTO;
import com.niu.web.business.mapper.AttachmentDao;
import com.niu.web.business.mapper.ChatFriendsDao;
import com.niu.web.business.mapper.ChatMessageDao;
import com.niu.web.business.mapper.UserMapper;
import com.niu.web.business.pojo.Attachment;
import com.niu.web.business.pojo.ChatFriends;
import com.niu.web.business.pojo.ChatMessage;
import com.niu.web.business.pojo.User;
import com.niu.web.business.service.ChatMessageService;
import com.niu.web.business.utils.FileUtils;
import com.niu.web.business.utils.JsonResult;
import com.niu.web.business.utils.Sm4Utils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@Service
public class ChatMessageServiceImpl extends ServiceImpl<ChatMessageDao, ChatMessage> implements ChatMessageService {
    @Autowired
    private ChatMessageDao chatMessageDao;
    @Autowired
    private ChatFriendsDao chatFriendsDao;
    @Autowired
    private AttachmentDao attachmentDao;
    @Autowired
    private UserMapper userMapper;

    @Override
    public JsonResult adduser(String userId,String receiveUserId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", userId);
        User user = userMapper.selectOne(queryWrapper);
        if (user==null){
            return new JsonResult("未查询到用户!请重新输入");
        }
        QueryWrapper<ChatFriends> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId).eq("f_user_id",receiveUserId);
        ChatFriends friends = chatFriendsDao.selectOne(wrapper);
        if (friends!=null){
            return new JsonResult("您已经添加过好友!请勿重复添加.");
        }
        ChatFriends chatFriends = new ChatFriends(userId,user.getUsername(),receiveUserId);
        chatFriendsDao.insert(chatFriends);
        return new JsonResult("添加成功!");
    }

    @Override
    public JsonResult findFriends(String userId) {
        //根据用户id加载好友列表展示
        QueryWrapper<ChatFriends> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId).or().eq("f_user_id",userId);
        List<ChatFriends> chatFriends = chatFriendsDao.selectList(wrapper);
        List<String> fUserIds = chatFriends.stream().map(c -> c.getFUserId()).collect(Collectors.toList());
        List<String> userIds = chatFriends.stream().map(c -> c.getUserId()).collect(Collectors.toList());
        if (fUserIds==null&&fUserIds.size()==0 && userIds.size()==0)return new JsonResult();
        fUserIds.addAll(userIds);
        List<User> users = userMapper.selectBatchIds(fUserIds);
        List<ChatFriendsDTO> collect = users.stream().filter(u->!u.getId().equals(userId)).map(user -> {
            Attachment attachment = attachmentDao.selectById(attachmentDao.findAttachmentByTypeUserId(userId,Constant.SAVE_TYPE_AVATAR.getId(),Constant.FILE_CATEGORY_IMAGE.getId()));
            return new ChatFriendsDTO(null, user.getId(), Constant.RESOURCE_WINDOWS_IMAGE_PATH.getName() + "/" + attachment.getName(), user.getUsername());
        }).collect(Collectors.toList());
        return new JsonResult(collect);
    }
    //根据接收方id查询消息记录
    @Override
    public JsonResult queryFriendsMsg(String userId,String receiveUserId) {
        List<ChatMessage> chatMessages = chatMessageDao.LookTwoUserMsg(userId,receiveUserId);
        List<ChatMessageDTO> dto=new ChatMessageAssembler().formChatMessageDTOS(chatMessages,userMapper);
        return new JsonResult(dto);
    }

    @Override
    public void insertCharMessage(ChatMessageDTO dto) {
        ChatMessageAssembler chatMessageAssembler = new ChatMessageAssembler();
        ChatMessage chatMessage=chatMessageAssembler.formChatMessageDTO(dto);
        chatMessageDao.insert(chatMessage);
    }

    @Override
    public JsonResult uploadImg(MultipartFile file) {
        String urlPath;
        String src;
        if (System.getProperty("os.name").contains("Win")){
            urlPath= Constant.RESOURCE_WINDOWS_CHAT_IMG.getId();
            src=Constant.RESOURCE_WINDOWS_CHAT_IMG.getName();
        }else {
            urlPath=Constant.RESOURCE_LINUX_CHAT_IMG.getId();
            src=Constant.RESOURCE_LINUX_CHAT_IMG.getName();
        }
        String filename = FileUtils.saveFile(file, urlPath);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("src",src +File.separator+ filename);
        return new JsonResult(jsonObject);
    }

    @Override
    public JsonResult queryUser(String username,String userId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        //不需要查询自己 不能添加自己为好友!
        queryWrapper.ne("id",userId);
        User user = userMapper.selectOne(queryWrapper);
        if (user==null){
            return new JsonResult("未查询到用户!请重新输入");
        }
        UserDTO userDTO = new UserAssembler().fromUserDTO(user);
        QueryWrapper<Attachment> wrapper = new QueryWrapper<>();
        wrapper.eq("user_Id",userId).eq("category",Constant.FILE_CATEGORY_IMAGE.getId());
        List<Attachment> attachments = attachmentDao.selectList(wrapper);
        Attachment attachment = attachments.get(0);
        userDTO.setImg(FileUtils.getOSName()+ File.separator +attachment.getName());
//        if (attachment!=null){
//            AttachmentValObj attachmentValObj = new AttachmentAssembler().formAttachmentDTO(attachment);
//            userDTO.setAttachmentValObj(attachmentValObj);
//        }
        return new JsonResult(userDTO);
    }

}