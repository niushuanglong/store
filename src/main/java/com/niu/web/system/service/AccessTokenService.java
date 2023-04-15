package com.niu.web.system.service;

import com.niu.web.business.dto.AccessTokenUserDTO;
import com.niu.web.business.pojo.AccessTokenUser;
import com.niu.web.business.pojo.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @author niushuanglong
 * @date 2023/3/15 9:55:44
 * @description
 */
public interface AccessTokenService {
    String createAccessToken(HttpServletRequest request, User user);

    AccessTokenUserDTO getAccessTokenUser(String token);
}
