package com.niu.web.business.pojo;
import com.baomidou.mybatisplus.annotation.TableName;
import com.niu.web.business.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;


import javax.persistence.*;
import java.io.*;
import java.sql.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(
	name = "T_ATT",
	indexes={
		@Index(name="idx_T_ATT1",columnList="category"),

	}
)
@TableName("T_ATT")
public class Attachment extends BaseInfo {
    public static String SAVE_TYPE_DISK = "0"; // 磁盘存储
    public static String SAVE_TYPE_DB = "1"; // 数据库存储
	
	private String name; //附件名称
	private String saveType; //保存类型 Constant.SAVE_TYPE_
    private String baseDir; //如果为磁盘存储，此为磁盘起始路径
	private String diskPath ; // 磁盘路径 (相对路径)
	private long fileSize; //文件大小
    @Column(updatable=false)
    @Lob
    @Basic(fetch=FetchType.LAZY)
	private String dataBlob; //数据库存储时的文件数据
	private String contentType; //文件格式
	private String userId;//用户id
	private String category; //附件归类
	private Integer version=1; //版本号
	private int valid=1;//默认有效


	public String getName() {
		return name;
	}
	public String getSaveType() {
		return saveType;
	}
	public String getBaseDir() {
		return baseDir;
	}
	public String getDiskPath() {
		return diskPath;
	}
	public long getFileSize() {
		return fileSize;
	}
	public String getDataBlob() {
		return dataBlob;
	}
	public String getContentType() {
		return contentType;
	}
	public String getCategory() {
		return category;
	}
	public Integer getVersion() {
		return version;
	}

	public String getUserId() {
		return userId;
	}

	public String getTmpFilePath() {
		return tmpFilePath;
	}

	public Attachment() {
        super();
    }


	public Attachment(String name,String saveType, String baseDir, String diskPath, long fileSize, String dataBlob, String contentType, String category,
					  Integer version, String tmpFilePath,String userId) {
		this.name = name;
		this.saveType = saveType;
		this.baseDir = baseDir;
		this.diskPath = diskPath;
		this.fileSize = fileSize;
		this.dataBlob = dataBlob;
		this.contentType = contentType;
		this.category = category;
		this.version = version;
		this.tmpFilePath = tmpFilePath;
		this.setBaseInfo();
		this.userId=userId;
	}

	/**
	 * 用于重复打开的临时路径
	 */
	@Transient
	private String tmpFilePath;

	//为此附件创建临时文件
	@Transient
	private String createAttachmentTempFile(InputStream in){
		if(in==null){
			return null;
		}
		this.tmpFilePath = "/attrTempFile_"+ UUID.randomUUID();
		String ext = this.getFileExtName();
		if(StringUtils.isNotBlank(ext)){
			this.tmpFilePath = this.tmpFilePath+ext;
		}
		File tempFile = new File(tmpFilePath);
		
		OutputStream out = null;
		try{
			tempFile.createNewFile();
			out = new FileOutputStream(tempFile);
			byte[] buffer = new byte[1024*100];
			int len = -1;
			while ((len=in.read(buffer))>-1){
				out.write(buffer, 0, len);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			FileUtils.clean(out);
		}
		
		return this.tmpFilePath;
	}

	//取得 文件的名,不包含"."及扩展名
    @Transient
    public String getFileNameNotExt() {
        //取得文件类型
        String fileName = "";
        if(this.getName()!=null){
            int idx = this.getName().lastIndexOf(".");
            if(idx>-1){
                fileName = this.getName().substring(0,idx);
            }else{
                fileName= this.getName();
            }
        }
        return fileName;
    }
    //取得 文件的扩展名,包含"."符号
    @Transient
    public String getFileExtName(){
        //取得文件类型
        String ext = "";
        if(this.getName()!=null){
            int idx = this.getName().lastIndexOf(".");
            if(idx>-1){
                ext = this.getName().substring(idx);
            }
        }
        return ext;
    }
    /**
     * 获取文件全路径
     * @return
     */
    @Transient
	public String getFileFullPath(){
    	return this.getBaseDir()+"/"+this.getDiskPath();
    }
	
	/**
	 * 更新附件基本信息
	 * @param category
	 * @param fileNoExtName
	 */
    public void update(String category,String fileNoExtName){
        this.category=category;
        if(StringUtils.isNotBlank(fileNoExtName)){
            this.name=fileNoExtName+this.getFileExtName();
        }
        this.setLastUpdateTime(new Date());
    }
    /**
	 * 更新附件基本信息
	 * @param category
	 */
    public void update2(String category,String fileName){
        this.category=category;
        this.name=fileName;
		this.setLastUpdateTime(new Date());
    }
    /**
     * 将附件变无效
     */
	public void invalid() {
		this.valid=0;
		this.setLastUpdateTime(new Date());
	}


    /**
     * 更新文件大小
     * @param fileSize
     */
    public void addVersion(long fileSize) {
    	if(this.version==null) this.version=1;
    	this.version++;
		this.fileSize = fileSize;
	}
}
