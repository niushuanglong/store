package com.niu.web.business.assembler;



import com.niu.web.business.SYSCONSTANT.Constant;
import com.niu.web.business.dto.AccessTokenUserDTO;
import com.niu.web.business.dto.OtherData;
import com.niu.web.business.dto.UserDTO;
import com.niu.web.business.pojo.AccessTokenUser;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author niushuanglong
 * @date 2022/10/12 22:52:18
 * @description
 */
public class AccessTokenAssembler {

    public AccessTokenUserDTO toAccessTokenUserDTO(String token, UserDTO userDTO,  Date date) {
        return new AccessTokenUserDTO(token,date,userDTO,new OtherData());
    }

    public AccessTokenUserDTO getAccessTokenUserFromReq(HttpServletRequest request) {
        AccessTokenUserDTO tokenUserDTO = (AccessTokenUserDTO)request.getSession().getAttribute(Constant.ACCESS_USER.getId());
        return tokenUserDTO;
    }

    public void setAccessTokenUserFromReq(HttpServletRequest request,AccessTokenUserDTO tokenUserDTO) {
        request.getSession().setAttribute(Constant.ACCESS_USER.getId(),tokenUserDTO);
        request.getSession().setAttribute("userId",tokenUserDTO.getUserId());
        request.getSession().setMaxInactiveInterval(60*60*60*1000);//超时时间 秒
    }


    public AccessTokenUserDTO formAccessTokenUserDTO(AccessTokenUser token,UserDTO dto) {
        return new AccessTokenUserDTO(token.getAccessToken(),token.getExpireTime(),dto,new OtherData());
    }
}
