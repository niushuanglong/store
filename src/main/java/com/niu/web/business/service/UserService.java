package com.niu.web.business.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.niu.web.business.dto.AccessTokenUserDTO;
import com.niu.web.business.dto.UserDTO;
import com.niu.web.business.pojo.User;
import com.niu.web.business.utils.JsonResult;
import com.niu.web.business.vo.LoginVO;
import com.niu.web.business.vo.UserQueryVO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author niushuanglong
 * @date 2022/11/17 20:24:44
 * @description
 */

public interface UserService extends IService<User> {
    /**
     * 校验用户信息
     * @return
     */
    JsonResult checkLogin(LoginVO vo, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    /**
     * 新增用户
     * @param dto
     * @return
     */
    JsonResult registerUser(UserDTO dto);

    /**
     * 查询用户
     * @param accessToken
     * @return
     */
    JsonResult findUserInfo(AccessTokenUserDTO accessToken,UserQueryVO vo, Model model);

    /**
     * 删除用户
     * @param userId
     * @return
     */
    JsonResult delUser(String userId);

    /**
     * 修改用户基本资料
     * @param dto
     * @return
     */
    JsonResult changeUserInfo(UserDTO dto);

    /**
     * 根据用户id查询
     * @param id
     * @return
     */
    JsonResult findUserById(String id);
}
