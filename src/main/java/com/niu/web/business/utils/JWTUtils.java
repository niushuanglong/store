package com.niu.web.business.utils;

import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTHeader;
import cn.hutool.jwt.signers.JWTSignerUtil;

import java.security.Security;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {

	private static final byte[] SECRET = "XX#$%()(#*!()!KL<><MQLMNQNQJQK sdfkjsdrow32234545fdf>?N<:{LWPW".getBytes();

	static {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}

	/**
	 * 生成jwtToken
	 * @param claims
	 * @param maxLife
	 * @return
	 */

	public static String createToken(Map<String,String> claims, long maxLife) {
	    try {
	        JWT jwt=JWT.create();
	        //jwt.setHeader(JWTHeader.TYPE, "JWT");//默认值
	        jwt.setHeader(JWTHeader.ALGORITHM, "HS256");
	        jwt.setSigner(JWTSignerUtil.createSigner("HS256", SECRET));
	        if(claims!=null&&claims.size()>0){
	        	jwt.addPayloads(claims);
	        }
	        jwt.setExpiresAt(new Date(System.currentTimeMillis()+maxLife));//有效时间 即改token 有效时长
	        String token=jwt.sign();//加密
	        return token;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("根据token获取对象失败");
        }
	}
	
	/**
	 * 获取对象
	 * @param jwt
	 * @return token
	 */
	public static Map<String,String> verifyTokenAndGetClaims(String jwt_token) {
		JWT jwt=JWT.create();
        //jwt.setHeader(JWTHeader.TYPE, "JWT");//默认值
        jwt.setHeader(JWTHeader.ALGORITHM, "HS256");
        jwt.setSigner(JWTSignerUtil.createSigner("HS256", SECRET));
	    try {
	        jwt.parse(jwt_token);
		} catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("校验jwt_token令牌失败");
        }
	    if(!jwt.verify()) {
        	throw new RuntimeException("无效的jwt_token");
        }
        
    	Map<String,String> result = new HashMap<String,String>();
    	
    	JSONObject jo=jwt.getPayloads();
    	if(jo==null||jo.size()==0) {
    		return result;
    	}
        for (Map.Entry<String,Object> d: jo.entrySet()) {
        	if(d.getValue()==null) {
        		continue;
        	}
        	result.put(d.getKey(), d.getValue().toString());
		}
        
        return result;
	}
	
}
