package com.niu.web.business.service.Impl;

import cn.hutool.core.io.resource.FileResource;
import cn.hutool.core.io.resource.Resource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niu.web.business.SYSCONSTANT.Constant;
import com.niu.web.business.dto.AccessTokenUserDTO;
import com.niu.web.business.mapper.AttachmentDao;
import com.niu.web.business.pojo.Attachment;
import com.niu.web.business.service.AttachmentService;
import com.niu.web.business.utils.FileUtils;
import com.niu.web.business.utils.JsonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author niushuanglong
 * @date 2023/3/10 18:10:35
 * @description
 */
@Service
public class AttachmentServiceImpl extends ServiceImpl<AttachmentDao, Attachment> implements AttachmentService {
    @Autowired
    private AttachmentDao attachmentDao;

    @Override
    public JsonResult upload(MultipartFile file,String userId){
        long fileSize = file.getSize();
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String contentType = file.getContentType();
        String category= Constant.FILE_CATEGORY_IMAGE.getId();
        String savePath=null;//磁盘路径
        //如果是windows系统
        if (System.getProperty("os.name").contains("Win")){
            savePath=Constant.RESOURCE_WINDOWS_IMAGE_PATH.getId();
        }else {
            savePath=Constant.RESOURCE_LINUX_IMAGE_PATH.getId();
        }
        OutputStream outputStream=null;
        InputStream inputStream;
        String uuidFileName = UUID.randomUUID()+suffix;
        String diskPath=savePath + File.separator + uuidFileName;
        try {
            inputStream = file.getInputStream();
            File file1 = new File(diskPath);
            if(!file1.getParentFile().exists()) {
                file1.getParentFile().mkdirs();
            }
            file1.createNewFile();
            outputStream = new FileOutputStream(file1);
            int len = -1;
            byte[] buffer = new byte[1024 * 50];
            while ((len = inputStream.read(buffer)) > -1) {
                outputStream.write(buffer, 0, len);
            }
        }catch (Exception e){
            e.getMessage();
            e.printStackTrace();
        } finally {
            FileUtils.clean(outputStream);
        }
        String base64 = new FileUtils().toBase64(file);
        Attachment attachment = new Attachment(uuidFileName,Constant.SAVE_TYPE_AVATAR.getId(),Constant.RESOURCE_WINDOWS_IMAGE_PATH.getName(),diskPath,
                fileSize,base64, contentType, category,1,"",userId);
        attachmentDao.insert(attachment);
        return new JsonResult("上传成功");
    }

    @Override
    public JsonResult findCarouselImages(Model model, HttpServletResponse response) throws IOException {
        QueryWrapper<Attachment> queryWrapper=new QueryWrapper();
        Map<String, Object> map = new HashMap<>();
        map.put("category",Constant.FILE_CATEGORY_IMAGE.getId());
        queryWrapper.allEq(map);
        List<Attachment> list = attachmentDao.selectList(queryWrapper);
        List<String> urlList = list.stream().filter(Objects::nonNull).map(l -> l.getBaseDir() +"/"+ l.getName()).collect(Collectors.toList());
        return new JsonResult(urlList);
    }

}
