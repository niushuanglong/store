package com.niu.web.business.controller;

import com.niu.web.business.assembler.AccessTokenAssembler;
import com.niu.web.business.dto.AccessTokenUserDTO;
import com.niu.web.business.service.AttachmentService;
import com.niu.web.business.utils.JsonResult;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author niushuanglong
 * @date 2023/3/10 17:27:32
 * @description
 */
@RequestMapping("/api/file")
@RestController
@Api(value = "AttachmentController", tags = "文件服务")
public class AttachmentController {
    @Autowired
    private AttachmentService attachmentService;

    @RequestMapping(value = "/uploadFiles", method = RequestMethod.POST)
    public JsonResult uploadFilesFromHtml(@RequestParam("file")MultipartFile file, HttpServletRequest request) {
        String userId = request.getSession().getAttribute("userId")+"";
        return attachmentService.upload(file,userId);
    }

    @RequestMapping(value = "/findCarouselImages", method = RequestMethod.GET)
    public JsonResult findCarouselImages(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        AccessTokenUserDTO accessToken = new AccessTokenAssembler().getAccessTokenUserFromReq(request);
        return attachmentService.findCarouselImages(model,response);
    }



}
