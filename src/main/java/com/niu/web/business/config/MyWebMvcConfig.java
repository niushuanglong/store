package com.niu.web.business.config;

import com.niu.web.business.MyInterceptor;
import com.niu.web.business.SYSCONSTANT.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.util.UrlPathHelper;

import java.nio.charset.StandardCharsets;

/**
 * @author niushuanglong
 * @date 2023/3/15 8:49:33
 * @description
 */
@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {
    @Autowired
    public MyInterceptor myInterceptor;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String os = System.getProperty("os.name");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        //是windows系统
        if (os.contains("Win")){
            registry.addResourceHandler("/images/**").addResourceLocations("file:"+Constant.RESOURCE_WINDOWS_IMAGE_PATH.getId());
            registry.addResourceHandler("/chat/**").addResourceLocations("file:"+Constant.RESOURCE_WINDOWS_CHAT_IMG.getId());
            registry.addResourceHandler("/files/**").addResourceLocations("file:"+Constant.RESOURCE_WINDOWS_FILE_PATH.getId());
        }else {
            registry.addResourceHandler("/images/**").addResourceLocations("file:"+Constant.RESOURCE_LINUX_IMAGE_PATH.getId());
            registry.addResourceHandler("/files/**").addResourceLocations("file:"+Constant.RESOURCE_LINUX_FILE_PATH.getId());
            registry.addResourceHandler("/chat/**").addResourceLocations("file:"+Constant.RESOURCE_LINUX_CHAT_IMG.getId());
        }
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/anno/**")
                .excludePathPatterns("/api/file/**")
                .excludePathPatterns("/view/**");
    }

    /**
     * 将音频的路径改了又改，也在游览器上试了直接用我代码里面写的链接进行测试访问，是通常的，
     * tips:为什么我用file:加本地路径可以访问是因为基于file Protocol 协议!
     * File Protocol 中文释义：本地文件传输协议 注解：File协议主要用于访问本地计算机中的文件
     * 后面就点开看里面的源码开始捣鼓，后面发现我访问的音频带有中文直接decode url 了导致访问不到对应的路径：如下设定好不将url进行decode就可以正常访问了！
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        UrlPathHelper urlPathHelper=new UrlPathHelper();
        urlPathHelper.setUrlDecode(false);
        urlPathHelper.setDefaultEncoding(StandardCharsets.UTF_8.name());
        configurer.setUrlPathHelper(urlPathHelper);
    }

}
