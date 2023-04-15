package com.niu.web.business.config;

import com.niu.web.business.APIFilter;
import com.niu.web.system.service.AccessTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author niushuanglong
 * @date 2023/3/15 12:38:41
 * @description
 */
@Configuration
public class FiltersConfig{

        @Autowired
        private AccessTokenService tokenService;
        //前后端交互处理-拦截所有api访问请求
        @Bean
        public FilterRegistrationBean<APIFilter> apiFilterRegistrationBean() {
            FilterRegistrationBean<APIFilter> registrationBean = new FilterRegistrationBean<APIFilter>();
            registrationBean.setFilter(new APIFilter(tokenService));
            registrationBean.addUrlPatterns("/api/*");
            registrationBean.setOrder(5);
            return registrationBean;
        }
}
