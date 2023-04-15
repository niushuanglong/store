package com.niu.web.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.niu.web.business.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author niushuanglong
 * @date 2022/11/17 20:34:16
 * @description
 */

public interface UserMapper extends BaseMapper<User> {

    //User getUser(String username,String password);
}
