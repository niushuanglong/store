package com.niu.web.business.utils;

import com.niu.web.business.APIFilter;
import com.niu.web.business.SYSCONSTANT.Constant;
import com.niu.web.business.pojo.Attachment;
import com.niu.web.business.service.Impl.AttachmentServiceImpl;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author niushuanglong
 * @date 2023/3/12 22:04:44
 * @description
 */
public class FileUtils {
    private static Logger logger = LogManager.getLogger(FileUtils.class);
    /**
     * @param filePath:
     * @return 文件内容-String类型
     * @description 读取文件内容
     * @date  2022/9/9
     */
    public static String readFileContent(String filePath) {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader bfr = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath)), "UTF-8"));
            String lineTxt = null;
            while ((lineTxt = bfr.readLine()) != null) {
                result.append(lineTxt);
            }
            bfr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 获得当前系统Linux或者windows
     * @return
     */
    public static String getOSId(){
        String OSId;
        //如果是windows系统
        if (System.getProperty("os.name").contains("Win")){
            OSId=Constant.RESOURCE_WINDOWS_IMAGE_PATH.getId();
        }else {
            OSId=Constant.RESOURCE_LINUX_IMAGE_PATH.getId();
        }
        return OSId;
    }

    /**
     * 获取系统文件保存位置
     * @return
     */
    public static String getOSName(){
        String savePath;
        //如果是windows系统
        if (System.getProperty("os.name").contains("Win")){
            savePath=Constant.RESOURCE_WINDOWS_IMAGE_PATH.getName();
        }else {
            savePath=Constant.RESOURCE_LINUX_IMAGE_PATH.getName();
        }
        return savePath;
    }
    //文件转Base64
    public String toBase64(MultipartFile multipartFile) {
        String map;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            BASE64Encoder base64Encoder =new BASE64Encoder();
            map= base64Encoder.encode(multipartFile.getBytes());
        } catch (IOException e) {
            return "图片转换失败";
        }
        return map;
    }
    /**
     * 根据路径生成文件
     * @param file
     * @return 文件名称
     */
    public static String saveFile(MultipartFile file,String urlPath)  {
        //AttachmentServiceImpl.IMAGES_PATH
        //如果是windows系统
        String savePath=System.getProperty("os.name").contains("Win")?Constant.RESOURCE_WINDOWS_IMAGE_PATH.getId():Constant.RESOURCE_LINUX_IMAGE_PATH.getId();
        if (StringUtils.isNotBlank(urlPath)) savePath=urlPath;
        //后缀名
        String extension = "."+FilenameUtils.getExtension(file.getOriginalFilename());
        String uuidFilename = UUID.randomUUID()+extension;
        File file1 = new File( savePath+ uuidFilename);
        InputStream in=null;
        OutputStream outputStream=null;
        try {
            in=file.getInputStream();
            if (!file1.getParentFile().exists()){
                file1.getParentFile().mkdirs();
            }
            file1.createNewFile();
            outputStream=new FileOutputStream(file1);
            int len=-1;
            byte[] buffer=new byte[1024*1024];
            while ((len=in.read(buffer))>-1){
                outputStream.write(buffer,0,len);
            }
        }catch (IOException e){
            logger.error(e.getMessage());
            e.printStackTrace();
        }finally {
            clean(outputStream);
        }
        return uuidFilename;
    }

    /**
     * 关闭流、链接等
     * @param resources
     */
    public static void clean(Object...resources) {
        if(resources==null) return;
        for(Object o :resources){
            if(o==null){
                continue;
            }
            if (o instanceof OutputStream){

                try {
                    ((OutputStream)o).close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
            else if (o instanceof InputStream){

                try {
                    ((InputStream)o).close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }else if (o instanceof Connection){

                try {
                    ((Connection)o).close();
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }else if (o instanceof ResultSet){

                try {
                    ((ResultSet)o).close();
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }else if (o instanceof ResultSet){

                try {
                    ((ResultSet)o).close();
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }else if (o instanceof Statement){

                try {
                    ((Statement)o).close();
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }

        }
    }

}
