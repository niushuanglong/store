package com.niu.web.viewController;

import com.niu.web.business.dto.LoginParamDTO;
import com.niu.web.business.dto.UserDTO;
import com.niu.web.business.service.UserService;
import com.niu.web.business.service.ValidateCodeService;
import com.niu.web.business.utils.JsonResult;
import com.niu.web.business.vo.LoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录
 * 配置文件pd-auth-server.yml中，"权限模块"扫描的包com.itheima.pinda.authority.controller.auth
 * 所以在swagger中可以在"权限模块"中查看
 */
@RestController
//@RequestMapping("/api/anno")
@Api(value = "LoginController", tags = "登录控制器")
@Slf4j
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private ValidateCodeService validateCodeService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation(value = "校验用户进行登录操作",httpMethod = "POST")
    public JsonResult checkLogin(@RequestBody LoginVO vo, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return userService.checkLogin(vo,request,response);
    }

    @ApiOperation(value = "新增用户",httpMethod = "POST")
    @RequestMapping(value = "/registerUser",method = RequestMethod.POST)
    public JsonResult addUser(@RequestBody UserDTO dto, HttpServletResponse response, HttpServletRequest request){
        return userService.registerUser(dto);
    }

    /**
     * 生成验证码
     */
    @ApiOperation(value = "验证码", notes = "验证码")
    @GetMapping(value = "/captcha", produces = "image/png")
    public String captcha(@RequestParam(value = "key") String key, HttpServletResponse response) throws IOException {
        return validateCodeService.create(key, response);
    }

}
