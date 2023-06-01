package com.niu.web.business;

import com.niu.web.business.SYSCONSTANT.Constant;
import com.niu.web.business.businessException.BusinessException;
import com.niu.web.business.dto.AccessTokenUserDTO;
import com.niu.web.business.pojo.AccessTokenUser;
import com.niu.web.business.utils.JWTUtils;
import com.niu.web.system.service.AccessTokenService;
import jdk.nashorn.internal.runtime.Version;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Enumeration;

/**
 * @author niushuanglong
 * @date 2023/3/15 9:32:42
 * @description
 */
public class APIFilter implements Filter {
    private static Logger logger = LogManager.getLogger(APIFilter.class);
    private AccessTokenService tokenService;

    public APIFilter(AccessTokenService tokenService) {
        this.tokenService=tokenService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        //获取filter名字
//        String filterName = filterConfig.getFilterName();
//        //获取filter里配置的init-param配置指定参数的值,如果参数不存在，则返回 null
//        String email = filterConfig.getInitParameter("email");
//        //获取filter里配置的init-param配置所有参数值,如果过滤器没有初始化参数，则返回一个空的 Enumeration
//        Enumeration<String> initParameterNames = filterConfig.getInitParameterNames();
//        //返回对调用者在其中执行操作的 ServletContext 的引用。
//        ServletContext servletContext = filterConfig.getServletContext();
    }
    @Override
    public void destroy() {

    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        this.checkUrl(req,resp,chain);
        this.checkAccessTokenUser(req,resp);
        chain.doFilter(req, resp);
        return;
    }

    private void checkAccessTokenUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        AccessTokenUserDTO accessToken;
        String id = Constant.ACCESS_USER.getId();
        String token = null;
        if (null!= session.getAttribute(id)){
            accessToken=(AccessTokenUserDTO)session.getAttribute(id);
            token=accessToken.getAccessToken();
            JWTUtils.verifyTokenAndGetClaims(accessToken.getAccessToken());
        }else if (null!=resp.getHeader(id)){
            token=resp.getHeader(id);
            JWTUtils.verifyTokenAndGetClaims(token);
        }else if (null!=req.getParameter(id)){
            token=req.getParameter(id);
            JWTUtils.verifyTokenAndGetClaims(token);
        }
        if(StringUtils.isBlank(token)){
            token=this.getCookieByCookieName("access_token", req);
        }
        AccessTokenUserDTO accessTokenUserDTO=null;
        if (StringUtils.isNotBlank(token)){
            accessTokenUserDTO=tokenService.getAccessTokenUser(token);
        }
        if(accessTokenUserDTO==null|| StringUtils.isBlank(accessTokenUserDTO.getUserId())){
            logger.error("请求地址{}无法获取到access_token【{}】所以无法获取登录用户。",req.getRequestURI(),accessTokenUserDTO);
            throw new BusinessException("登录已过期，请重新登录!",Constant.STATUS_ERR.getId());
        }
        if (accessTokenUserDTO.getExpireTime().compareTo(new Date())<0){
            resp.sendRedirect(req.getContextPath()+"/view/login");
            throw new BusinessException("登录已过期，请重新登录!",Constant.STATUS_ERR.getId());
        }
        req.getSession().setAttribute(Constant.ACCESS_USER.getId(), accessTokenUserDTO);
    }

    //校验路径
    private void checkUrl(HttpServletRequest req,HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        // 如果是OPTIONS则结束请求
        if (HttpMethod.OPTIONS.toString().equals(req.getMethod())) {
            resp.setStatus(HttpStatus.NO_CONTENT.value());
            return;
        }
        //不校验权限
        if(req.getRequestURI().contains("/unauth")){
            chain.doFilter(req, resp);
            return;
        }
        //String accessUrl = getAccessUrl(req);

    }


    private String getCookieByCookieName(String cookieName,HttpServletRequest hreq) {
        Cookie[] cookies = hreq.getCookies();
        if(cookies!=null){
            for(Cookie cookie:cookies){
                if(cookieName.equalsIgnoreCase(cookie.getName())){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private String getAccessUrl(HttpServletRequest req) {
        String origUrl = req.getRequestURI();
        String accessUrl = origUrl.replaceFirst(req.getContextPath(),"")
                .replaceAll("/{1,}","/");
        return accessUrl;
    }
}
