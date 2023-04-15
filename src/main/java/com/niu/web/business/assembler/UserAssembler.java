package com.niu.web.business.assembler;

import com.niu.web.business.dto.UserDTO;
import com.niu.web.business.pojo.User;

import java.util.Date;

/**
 * @author niushuanglong
 * @date 2023/2/24 22:05:54
 * @description 实体类转换
 */
public class UserAssembler {


    public User toUser(UserDTO dto) {
       return new User(dto.getEmail(),dto.getUsername(),dto.getPhone(),dto.getGender(),
               dto.getBirthday(),dto.getPassword(),dto.getValid(),dto.getPwdError(),
               dto.getAddress(), dto.getMotto(),dto.getImg());
    }
    public UserDTO fromUserDTO(User user) {
        return new UserDTO(user.getId(),user.getCreateTime(),user.getLastUpdateTime(),user.getIpAddress(),
                user.getEmail(),user.getUsername(),user.getPhone(),user.getBirthday(),user.getGender(),
                user.getPassword(),user.getValid(),user.getLockedTime(),user.getPwdError(),user.getAddress(),user.getMotto(),user.getAvatar());
    }
}
