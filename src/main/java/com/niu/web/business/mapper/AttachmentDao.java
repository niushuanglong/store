package com.niu.web.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.niu.web.business.pojo.Attachment;
import com.niu.web.business.pojo.User;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author niushuanglong
 * @date 2023/3/10 18:15:07
 * @description
 */
public interface AttachmentDao extends BaseMapper<Attachment>{


    String findAttachmentByTypeUserId(@RequestParam("userId") String userId,
                                                @RequestParam("saveType")String saveType, @RequestParam("category")String category);
}