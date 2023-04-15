package com.niu.web.business.utils;

import com.niu.web.business.annotationUtils.OperationLog;
import org.junit.Assert;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author niushuanglong
 * @date 2022/12/8 21:37:43
 * @description 扫描日志工具
 */
public class AnnotationScanUtils {
    private static List<Map<String,Object>> mapList=null;
    public static List<Map<String,Object>> toOperationLog(Object object) throws IllegalAccessException {
        if (mapList==null) mapList = new ArrayList<>();
        else return mapList;
        Class<?> clazz = object.getClass();
        //Annotation[] annotations = clazz.getDeclaredAnnotations();
        //获得这个类上的属性
        Field[] fields = clazz.getDeclaredFields();//获取的是类上Autowired这样的属性上的
        for (Field field : fields) {//里边的私有方法也可访问 如果方法修饰符是private
            field.setAccessible(true);
            //如果包含这个注解
            if (field.isAnnotationPresent(OperationLog.class)) {
                Map<String, Object> map = new HashMap<>();
                Assert.assertNotNull(field.getAnnotation(OperationLog.class).value());
                Object obj = field.get(object);
                map.put(field.getAnnotation(OperationLog.class).value(), obj);
                mapList.add(map);
            }
        }
        return mapList;
        //        String name = field.getName();
        //        name="set"+name.substring(0,1).toUpperCase()+name.substring(1);
        //        Method method = clazz.getMethod(name, LoginService.class);
    }


}
