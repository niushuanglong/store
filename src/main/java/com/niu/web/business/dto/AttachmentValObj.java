package com.niu.web.business.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ApiModel(value="附件基本信息")
@JsonIgnoreProperties(ignoreUnknown=true)
public class AttachmentValObj {
	
	@ApiModelProperty(value="附件id")
    protected String id;
    @ApiModelProperty(value="附件全名称")
    protected String fileName;
    @ApiModelProperty(value="附件归类类型")
    protected String category;
    @ApiModelProperty(value="附件创建人")
    protected SysDataSimpleValObj creator;
    @ApiModelProperty(value="附件创建时间 到时分秒")
    protected Date createTime;
    @ApiModelProperty(value="附件contentType")
    protected String fileContentType;
    @ApiModelProperty(value="附件的字节大小")
    protected long fileByteSize=-1l;
    @ApiModelProperty(value="其他信息")
    protected Map<String,Object> otherData=new HashMap<String,Object>();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public SysDataSimpleValObj getCreator() {
        return creator;
    }

    public void setCreator(SysDataSimpleValObj creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public long getFileByteSize() {
        return fileByteSize;
    }

    public void setFileByteSize(long fileByteSize) {
        this.fileByteSize = fileByteSize;
    }

    public Map<String, Object> getOtherData() {
        return otherData;
    }

    public void setOtherData(Map<String, Object> otherData) {
        this.otherData = otherData;
    }

    public AttachmentValObj(){

    }
    public AttachmentValObj(String id, String fileName, String category, SysDataSimpleValObj creator, Date createTime,
                            String fileContentType, long fileByteSize, Map<String, Object> otherData) {
        this.id = id;
        this.fileName = fileName;
        this.category = category;
        this.creator = creator;
        this.createTime = createTime;
        this.fileContentType = fileContentType;
        this.fileByteSize = fileByteSize;
        this.otherData = otherData;
    }

    /**
     * 获取附件无后缀名称
     * @return
     */
    public String qbtainFileNoExtName() {
      //取得文件类型
        String fileName="";
        if(this.fileName!=null){
            int idx=this.fileName.lastIndexOf(".");
            if(idx>-1){
                fileName=this.fileName.substring(0,idx);
            }else{
                fileName=this.fileName;
            }
        }
        return fileName;
    }
    /**
     * 获取附件 后缀名称  带.
     * @return
     */
    public String qbtainFileExtName() {
        //取得文件类型
        String ext="";
        if(this.fileName!=null){
            int idx=this.fileName.lastIndexOf(".");
            if(idx>-1){
                ext=this.fileName.substring(idx);
            }
        }
        return ext;
    }
    /**
     * 新附件名称 根据页面提交的新无后缀名称构建新 新附件名称
     * @return
     */
    public String qbtainNewFileName(){
        return this.otherData.get("fileNoExtName")+this.qbtainFileExtName();
    }
}
