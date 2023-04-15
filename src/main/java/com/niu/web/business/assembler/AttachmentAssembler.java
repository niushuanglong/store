package com.niu.web.business.assembler;

import com.niu.web.business.SYSCONSTANT.Constant;
import com.niu.web.business.dto.AttachmentValObj;
import com.niu.web.business.dto.SysDataSimpleValObj;
import com.niu.web.business.pojo.Attachment;
import com.niu.web.business.utils.FileUtils;

import java.io.File;
import java.util.HashMap;

/**
 * @author niushuanglong
 * @date 2023/3/10 20:13:29
 * @description
 */
public class AttachmentAssembler {

    public void toFileAttachment(){


    }


    public AttachmentValObj formAttachmentDTO(Attachment attachment) {
        return new AttachmentValObj(attachment.getId(), FileUtils.getOSName()+ File.separator +attachment.getName(),attachment.getCategory(),
                new SysDataSimpleValObj(attachment.getUserId(),"暂时没记录用户名"),
                attachment.getCreateTime(),attachment.getContentType(),attachment.getFileSize(),new HashMap<>());
    }
}
