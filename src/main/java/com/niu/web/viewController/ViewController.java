package com.niu.web.viewController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author niushuanglong
 * @date 2023/2/23 22:21:33
 * @description 配置最外层试图
 */
@RequestMapping("/view")
@Controller
public class ViewController {

    @RequestMapping("/login")
    public String loginHandler(){
        return "login";
    }
    @RequestMapping("/register")
    public String register(){
        return "register";
    }

    @RequestMapping("/{address}")
    public String address(@PathVariable String address){
        return address;
    }

    @RequestMapping("/page/{address}")
    public String pageAddress(@PathVariable String address){
        return "page/"+address;
    }

}
