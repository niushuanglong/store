package com.niu.web.business.service.Impl;


import com.niu.web.business.SYSCONSTANT.Constant;
import com.niu.web.business.businessException.CodeException;
import com.niu.web.business.service.ValidateCodeService;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.base.Captcha;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.cache.CacheKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 验证码服务
 */
@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {
    @Resource
    private RedisTemplate<String ,Object> redisTemplate;

    @Override
    //生成算术验证码，同时将验证码进行缓存
    public String create(String key, HttpServletResponse response) throws IOException {
        if (StringUtils.isBlank(key)) {
            new CodeException(Constant.CODE_ERROR.getName());
        }
        Captcha captcha = new ArithmeticCaptcha(115, 42);
        captcha.setCharType(2);
        //本次产生的验证码
        String text = captcha.text();
        //将验证码进行缓存
//        redisTemplate.boundValueOps(Constant.CODE_CAPTCHA.getId()).expire(1,TimeUnit.MINUTES);
//        redisTemplate.expire(Constant.CODE_CAPTCHA.getId(),1, TimeUnit.MINUTES);
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        response.setHeader(HttpHeaders.PRAGMA, "No-cache");
        response.setHeader(HttpHeaders.CACHE_CONTROL, "No-cache");
        response.setDateHeader(HttpHeaders.EXPIRES, 0L);
        //将生成的验证码图片通过输出流写回客户端浏览器页面
        captcha.out(response.getOutputStream());
        return text;
    }
}

