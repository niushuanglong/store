package com.niu.web.business;

import com.niu.web.business.SYSCONSTANT.Constant;
import com.niu.web.business.dto.AccessTokenUserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author niushuanglong
 * @date 2023/3/15 9:12:12
 * @description
 */
@Component
public class MyInterceptor implements HandlerInterceptor {
    private static Logger logger = LogManager.getLogger(MyInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            //统一拦截（查询当前session是否存在user）(这里user会在每次登陆成功后，写入session)
            AccessTokenUserDTO tokenUserDTO=(AccessTokenUserDTO)request.getSession().getAttribute(Constant.ACCESS_USER.getId());
            if(tokenUserDTO!=null){
                logger.info("用户未登录");
                requestDirect(request, response);
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;//如果设置为false时，被请求时，拦截器执行到此处将不会继续操作
        //如果设置为true时，请求将会继续执行后面的操作
    }

    //判断是否请求为重定向
    private void requestDirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取当前请求的路径
        String basePath = request.getScheme() + "://" + request.getServerName() + ":"  + request.getServerPort()+request.getContextPath();
        //如果request.getHeader("X-Requested-With") 返回的是"XMLHttpRequest"说明就是ajax请求
        if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
            //前端需要判断是否是重定向
            response.setHeader("SESSIONSTATUS", "TIMEOUT");
            response.setHeader("CONTEXTPATH",basePath+"/login");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }else{
            response.sendRedirect(basePath + "/login");
        }
    }
    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
//         System.out.println("执行了TestInterceptor的postHandle方法");
    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
//        System.out.println("执行了TestInterceptor的afterCompletion方法");
    }

}
