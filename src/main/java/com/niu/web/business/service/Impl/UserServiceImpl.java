package com.niu.web.business.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niu.web.business.assembler.UserAssembler;
import com.niu.web.business.dto.AccessTokenUserDTO;
import com.niu.web.business.dto.UserDTO;
import com.niu.web.business.mapper.UserMapper;
import com.niu.web.business.pojo.User;
import com.niu.web.business.service.UserService;
import com.niu.web.business.utils.*;
import com.niu.web.business.vo.LoginVO;
import com.niu.web.business.vo.UserQueryVO;
import com.niu.web.system.service.AccessTokenService;
import io.jsonwebtoken.lang.Collections;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author niushuanglong
 * @date 2022/11/17 20:24:55
 * @description
 */
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AccessTokenService accessTokenService;
    @Resource
    private RedisTemplate<String ,Object> redisTemplate;


    @Override
    public JsonResult checkLogin(LoginVO vo, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //BoundValueOperations captchaKey = redisTemplate.boundValueOps(Constant.CODE_CAPTCHA.getId());
        //String captcha = (String) captchaKey.get();//验证码
//        if (!vo.getCaptcha().equals(captcha)){
//            return new JsonResult("验证码错误!");
//        }
        HashMap<String,Object> map = new HashMap<>();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.isNotNull("username").isNotNull("email").ge("age",12);
        map.put("username",vo.getUsername());
        map.put("password", SM3Utils.sm3(vo.getPassword()));
        List<User> users = userMapper.selectByMap(map);
        if (users.get(0)==null){
            return new JsonResult("未查询到用户!请重新输入!");
        }
        UserDTO userDTO = new UserAssembler().fromUserDTO(users.get(0));
        OtherData otherData = new OtherData();
        otherData.put("token",accessTokenService.createAccessToken(request, users.get(0)));
        return new JsonResult(userDTO,otherData);
    }


    @Override
    public JsonResult registerUser(UserDTO dto) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", dto.getUsername());
        User user = userMapper.selectOne(queryWrapper);
        if (user!=null){
            return new JsonResult(400,"用户已经存在!请重新填写用户名.",null,0);
        }
        UserAssembler userAssembler = new UserAssembler();
        dto.setCreateTime(new Date());
        dto.setPwdError(0);
        dto.setLastUpdateTime(new Date());
        dto.setIpAddress(IPUtils.getIpAddr());//password -> 207cf410532f92a47dee245ce9b11ff71f578ebd763eb3bbea44ebd043d018fb
        User userInfo=userAssembler.toUser(dto);
        int insert = userMapper.insert(userInfo);
        if (insert!=1){
            return new JsonResult(400,"注册失败",null,0);
        }
        return new JsonResult("注册成功");
    }

    @Override
    public JsonResult findUserInfo(AccessTokenUserDTO accessToken,UserQueryVO vo, Model model) {
        QueryWrapper queryWrapper = queryUserCondition(vo);
        Page<User> userPage = Page.of(vo.getPageNo(),vo.getPageSize());
        userMapper.selectPage(userPage, queryWrapper);
        List<User> records = userPage.getRecords();
        UserAssembler userAssembler = new UserAssembler();
        List<UserDTO> collect = records.stream().filter(Objects::nonNull).map(user -> userAssembler.fromUserDTO(user)).collect(Collectors.toList());
        return new JsonResult(collect);
    }

    @Override
    public JsonResult delUser(String userId) {
        if (StringUtils.isBlank(userId)){
            throw new RuntimeException("用户id错误!");
        }
        userMapper.deleteById(userId);
        return new JsonResult("删除成功!");
    }

    @Override
    public JsonResult changeUserInfo(UserDTO dto) {
        //如果新密码不为空就是修改密码 否则是修改基本信息
        User user = userMapper.selectById(dto.getId());
        if (StringUtils.isNotBlank(dto.getNewPassword())){
            if (!user.getPassword().equals(dto.getPassword())){
                throw new RuntimeException("旧密码错误!请重新输入");
            }
            user.updatePassword(dto.getNewPassword());
            userMapper.updateById(user);
        }
        user.updateInfo(dto.getNickname(),dto.getMotto(),dto.getPhone(),dto.getEmail(),dto.getAvatar());
        userMapper.updateById(user);
        return new JsonResult(new UserAssembler().fromUserDTO(userMapper.selectById(dto.getId())));
    }

    @Override
    public JsonResult findUserById(String id) {
        if (StringUtils.isBlank(id)){
            throw new RuntimeException("id不能为空!");
        }
        return new JsonResult(new UserAssembler().fromUserDTO(userMapper.selectById(id)));
    }

    private QueryWrapper queryUserCondition(UserQueryVO vo){
        QueryWrapper queryWrapper = new QueryWrapper();
        if (StringUtils.isNotBlank(vo.getId())){
            queryWrapper.eq("id",vo.getId());
        }
        if (StringUtils.isNotBlank(vo.getUsername())){
            queryWrapper.like("username",vo.getUsername());
        }
        if (StringUtils.isNotBlank(vo.getGender()) && !"0".equals(vo.getGender())){
            queryWrapper.eq("gender",vo.getGender());
        }
        if (StringUtils.isNotBlank(vo.getEmail())){
            queryWrapper.eq("email",vo.getEmail());
        }
        return queryWrapper;
    }


}
