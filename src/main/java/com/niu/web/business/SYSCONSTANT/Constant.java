package com.niu.web.business.SYSCONSTANT;

import com.niu.web.business.utils.CUSTOMER;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @author niushuanglong
 * @date 2023/2/26 11:38:43
 * @description 常量
 */
public class Constant {
    public static final CUSTOMER STATUS_SUCCESS=new CUSTOMER("200","成功");
    public static final CUSTOMER STATUS_ERR=new CUSTOMER("400","异常状态");
    public static final CUSTOMER STATUS_ERR_SERVER=new CUSTOMER("500","服务器异常");

    //登录常量
    public static final CUSTOMER ACCESS_USER=new CUSTOMER("access_user","用户令牌");

    /**-------------------------------- 文件后缀常量 ----------------------------*/
    public static final CUSTOMER SUFFIX_PNG=new CUSTOMER("png","图片");
    public static final CUSTOMER SUFFIX_JPEG=new CUSTOMER("jpeg","图片");
    public static final CUSTOMER SUFFIX_JPG=new CUSTOMER("jpg","图片");
    public static final CUSTOMER SUFFIX_GIF=new CUSTOMER("gif","图片");
    public static final CUSTOMER SUFFIX_ZIP=new CUSTOMER("zip","压缩文件");

    /**-------------------------------- 验证码常量 ----------------------------*/
    public static final CUSTOMER CODE_ERROR=new CUSTOMER("code_error","验证码key不能为空");
    public static final CUSTOMER CODE_CAPTCHA=new CUSTOMER("captcha","验证码");//验证码缓存KEY

    /**-------------------------------- 验证码常量 ----------------------------*/
    public static final CUSTOMER FILE_CATEGORY_IMAGE=new CUSTOMER("image","图片");
    public static final CUSTOMER FILE_CATEGORY_AVATAR=new CUSTOMER("AVATAR","头像");//验证码缓存KEY

    /**-------------------------------- 文件路径常量  ----------------------------*/
    //拦截器 会拦截这两个url请求 转发到本机路径
    public static final CUSTOMER RESOURCE_LINUX_IMAGE_PATH=new CUSTOMER("/data/java/images/","/images");
    public static final CUSTOMER RESOURCE_LINUX_FILE_PATH=new CUSTOMER("/data/java/files/","/files");
    public static final CUSTOMER RESOURCE_LINUX_CHAT_IMG=new CUSTOMER("/data/java/chat/","/chat");
    public static final CUSTOMER RESOURCE_WINDOWS_IMAGE_PATH=new CUSTOMER("D:\\atta\\images\\","/images");
    public static final CUSTOMER RESOURCE_WINDOWS_FILE_PATH=new CUSTOMER("D:\\atta\\files\\","/files");
    public static final CUSTOMER RESOURCE_WINDOWS_CHAT_IMG=new CUSTOMER("D:\\chat\\","/chat");

    /**-------------------------------- 聊天信息类型常量 ----------------------------*/
    public static final CUSTOMER MSG_TYPE_TEXT=new CUSTOMER("text","文本");
    public static final CUSTOMER MSG_TYPE_IMAGE=new CUSTOMER("image","图片");

    /**-------------------------------- 照片保存类型常量 ----------------------------*/
    public static final CUSTOMER SAVE_TYPE_AVATAR=new CUSTOMER("avatar","用户头像");
    public static final CUSTOMER SAVE_TYPE_HOME_IMG=new CUSTOMER("homeImg","首页轮播图");

    /**-------------------------------- 用户权限等级 ----------------------------*/
    //public static final CUSTOMER ROLE_ADMIN=new CUSTOMER("4","管理员");//管理所有人
//做成动态的 在页面添加

    /***************************************************** 获取某个常量的组合 ***********************************************************/


    /**
     * 获取文件后缀常量
     * @return
     */
    public List<CUSTOMER> getFileSuffix(){
        List<CUSTOMER> objects = new ArrayList<>();
        objects.add(SUFFIX_PNG);
        objects.add(SUFFIX_JPEG);
        objects.add(SUFFIX_ZIP);
        objects.add(SUFFIX_JPG);
        objects.add(SUFFIX_GIF);
        return objects;
    }





















}
