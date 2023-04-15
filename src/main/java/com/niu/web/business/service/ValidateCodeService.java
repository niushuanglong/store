package com.niu.web.business.service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author niushuanglong
 * @date 2023/3/9 10:34:21
 * @description
 */
public interface  ValidateCodeService {
    /**
     * 生成验证码
     */
    String create(String key, HttpServletResponse response) throws IOException;

}
