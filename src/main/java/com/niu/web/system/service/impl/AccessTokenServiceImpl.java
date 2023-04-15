package com.niu.web.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niu.web.business.assembler.AccessTokenAssembler;
import com.niu.web.business.assembler.UserAssembler;
import com.niu.web.business.dto.AccessTokenUserDTO;
import com.niu.web.business.dto.UserDTO;
import com.niu.web.business.mapper.AccessTokenDao;
import com.niu.web.business.mapper.UserMapper;
import com.niu.web.business.pojo.AccessTokenUser;
import com.niu.web.business.pojo.User;
import com.niu.web.business.utils.CalendarUtils;
import com.niu.web.business.utils.JWTUtils;
import com.niu.web.system.service.AccessTokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;

/**
 * @author niushuanglong
 * @date 2023/3/15 9:55:58
 * @description
 */
@Service
@Transactional
public class AccessTokenServiceImpl extends ServiceImpl<AccessTokenDao, AccessTokenUser> implements AccessTokenService {
    @Autowired
    private AccessTokenDao accessTokenDao;
    @Autowired
    private UserMapper userMapper;
    private static final long MAXLIFE=new Date().getTime()+24*60*60*1000;
    @Override
    public String createAccessToken(HttpServletRequest request, User user) {
        UserDTO userDTO = new UserAssembler().fromUserDTO(user);
        String token = JWTUtils.createToken(new HashMap<>(), MAXLIFE);
        AccessTokenAssembler accessTokenAssembler = new AccessTokenAssembler();
        AccessTokenUser accessToken = new AccessTokenUser(token, CalendarUtils.offsetDays(new Date(),1),user.getUsername(),user.getId());
        accessTokenAssembler.setAccessTokenUserFromReq(
                request,accessTokenAssembler.toAccessTokenUserDTO(token, userDTO,CalendarUtils.offsetDays(new Date(),1) ));
        this.save(accessToken);
        return token;
    }

    @Override
    public AccessTokenUserDTO getAccessTokenUser(String token) {
        if (StringUtils.isBlank(token)){
            return null;
        }
        QueryWrapper<AccessTokenUser> tokenQuery=new QueryWrapper<>();
        tokenQuery.eq("access_token",token);
        AccessTokenUser accessTokenUser = accessTokenDao.selectOne(tokenQuery);
        QueryWrapper<User> userQuery=new QueryWrapper<>();
        userQuery.eq("id",accessTokenUser.getUserId());
        UserDTO userDTO = new UserAssembler().fromUserDTO(userMapper.selectOne(userQuery));
        return new AccessTokenAssembler().formAccessTokenUserDTO(accessTokenUser,userDTO);
    }
}
