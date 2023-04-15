package com.niu.web.business.controller;

import com.niu.web.business.assembler.AccessTokenAssembler;
import com.niu.web.business.dto.AccessTokenUserDTO;
import com.niu.web.business.dto.UserDTO;
import com.niu.web.business.service.UserService;
import com.niu.web.business.utils.JsonResult;
import com.niu.web.business.utils.Sm4Utils;
import com.niu.web.business.vo.LoginVO;
import com.niu.web.business.vo.UserQueryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author niushuanglong
 * @date 2022/11/17 20:21:39
 * @description
 */

@RequestMapping("/api/user")
@RestController
@Api("用户接口集")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "新增用户",httpMethod = "POST")
    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public JsonResult addUser(@RequestBody UserDTO dto, HttpServletResponse response, HttpServletRequest request){
        return userService.addUser(dto);
    }

    @ApiOperation(value = "查询用户信息",httpMethod = "POST")
    @RequestMapping(value = "/findUserInfo",method = RequestMethod.POST)
    public JsonResult findUserInfo(@RequestBody UserQueryVO vo, HttpServletResponse response, HttpServletRequest request, Model model){
        AccessTokenUserDTO accessToken = new AccessTokenAssembler().getAccessTokenUserFromReq(request);
        return userService.findUserInfo(accessToken,vo,model);
    }
    @ApiOperation(value = "删除用户信息",httpMethod = "GET")
    @RequestMapping(value = "/delUser",method = RequestMethod.GET)
    public JsonResult delUser(String userId, HttpServletRequest request){
        AccessTokenUserDTO accessToken = new AccessTokenAssembler().getAccessTokenUserFromReq(request);
        return userService.delUser(userId);
    }

}
