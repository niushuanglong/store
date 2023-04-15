//package com.niu.web.business.config;
//
//import cn.hutool.http.HttpRequest;
//import cn.hutool.http.HttpResponse;
//import cn.hutool.json.JSONUtil;
//import com.google.gson.JsonObject;
//import com.niu.web.business.utils.JsonResult;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.lang.reflect.Method;
//
//
//@Aspect
//@Component
//@Slf4j
//public class GlobalAOP {
//
//    private static Logger logger = LoggerFactory.getLogger(GlobalAOP.class);
//    public static HttpServletResponse response=null;
//    public static HttpServletRequest request=null;
//
//
//    @Pointcut("execution(* com.niu.web.business.controller.*.*(..))")
//    public void pointCut() {
//    }
//
//
//    @Around("pointCut()")
//    public Object around(ProceedingJoinPoint jp) throws Throwable {
//        if (response==null||request==null){
//            RequestAttributes ra = RequestContextHolder.getRequestAttributes();
//            ServletRequestAttributes sra = (ServletRequestAttributes) ra;
//            assert sra != null;
//            request = sra.getRequest();
//            response = sra.getResponse();
//        }
//        assert request.getRequestURL() != null;
//        String url = request.getRequestURL().toString();
//        String method = request.getMethod();
//        String queryString = request.getQueryString();
//        long startTime = System.currentTimeMillis();
//        logger.info("{url:{}, method:{}, queryString:{}}", url, method, queryString);
//        Object rs = null;
//        boolean successAble = false;
//        JsonObject paramsJson = new JsonObject();
//        try {
//            Object[] params = jp.getArgs();
//            MethodSignature target = (MethodSignature)jp.getSignature();
//            target.getMethod();
//            System.err.println(target.getMethod());
//            for (int i = 0; i < params.length; i++) {
//                if (params[i] instanceof BindingResult || params[i] instanceof HttpRequest || params[i] instanceof HttpResponse){
//                    continue;
//                }
//                if (method.equals("POST")){
//                    paramsJson.addProperty("param-" + i, JSONUtil.toJsonPrettyStr(params[i]));
//                }else  if (method.equals("GET")){
//                    paramsJson.addProperty("param-" + i, params[i].toString());
//                }
//            }
//            rs = jp.proceed();
//            successAble = true;
//        }catch (Exception e){
//            e.printStackTrace();
//            logger.error( e.getMessage());
//            return new JsonResult<>(e.getMessage());
//        }finally {
//            logger.info("{url:{}, method:{}, success-able:{}, exe-time:{}, params:{}}", url, method, successAble, System.currentTimeMillis() - startTime, paramsJson);
//        }
//        return rs;
//    }
//    @AfterReturning(value = "execution(* com.niu.web.business.controller.*.*(..))",returning = "methodResult")
//    public void afterReturning(JoinPoint joinPoint,Object methodResult){
//        MethodSignature target = (MethodSignature)joinPoint.getSignature();
//        Method method = target.getMethod();
//        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
//        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
//        assert sra != null;
//        HttpServletRequest request = sra.getRequest();
//        HttpServletResponse response = sra.getResponse();
//        if (methodResult!=null){
//            JsonResult result = (JsonResult) methodResult;
//            if (result.getData()!=null){
//
//            }
//
//        }
//    }
//
//
//}
