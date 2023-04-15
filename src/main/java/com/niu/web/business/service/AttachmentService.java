package com.niu.web.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.niu.web.business.dto.AccessTokenUserDTO;
import com.niu.web.business.pojo.Attachment;
import com.niu.web.business.utils.JsonResult;
import com.niu.web.business.vo.LoginVO;
import org.bouncycastle.math.raw.Mod;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author niushuanglong
 * @date 2023/3/10 17:29:34
 * @description
 */
public interface AttachmentService extends IService<Attachment> {


    JsonResult upload(MultipartFile multipartFile,String userId);

    JsonResult findCarouselImages(Model model, HttpServletResponse response) throws IOException;
}
