package com.niu.web.business.utils;

import cn.hutool.crypto.SmUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.InputStream;

/**
 * sm3  用于生成摘要
 * @author WQ
 */
public class SM3Utils {

    public static void main(String[] args) throws Exception {
        
    	String content = "为人民服务5-28，zs登陆，信息采编，如图所示得信息，没有对应出相似信息呢石景山信息管理系统（AK）SJSXXAK-41已链接应用程序\n"
    			+ "BUG管理系统\n"
    			+ "仪表板\n"
    			+ "项目\n"
    			+ "问题\n"
    			+ "面板\n"
    			+ "测试\n"
    			+ "停靠筛选器面板([)";
    	System.err.println(sm3(content));
    }
    
    
	/**
	 * SM3加密，生成16进制SM3字符串<br>
	 * @param data 数据
	 * @return SM3字符串
	 */
	public static String sm3(String data) {
		if(StringUtils.isEmpty(data)) return null;
		return SmUtil.sm3().digestHex(data);
	}
	/**
	 * SM3加密，生成16进制SM3字符串<br>
	 * @param data 数据
	 * @return SM3字符串
	 */
	public static String sm3(InputStream data) {
		return SmUtil.sm3().digestHex(data);
	}
	/**
	 * SM3加密文件，生成16进制SM3字符串<br>
	 * @param dataFile 被加密文件
	 * @return SM3字符串
	 */
	public static String sm3(File dataFile) {
		return SmUtil.sm3().digestHex(dataFile);
	}
	
}
