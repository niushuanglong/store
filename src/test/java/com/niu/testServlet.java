package com.niu;

import javax.servlet.http.HttpServlet;
import java.util.Map;
import java.util.Properties;

/**
 * @author niushuanglong
 * @date 2022/11/27 17:12:58
 * @description
 */
public class testServlet extends HttpServlet {
    public static void main(String[] args) {
        Map<String, String> getenv = System.getenv();
        Properties properties = System.getProperties();
        System.err.println("sss");
    }
}
