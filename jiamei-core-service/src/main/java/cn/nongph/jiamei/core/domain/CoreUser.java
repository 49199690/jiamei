package cn.nongph.jiamei.core.domain;

import java.util.Date;

import cn.nongph.jiamei.core.annotation.RequiredField;
import cn.nongph.jiamei.core.service.CoreUserService;

public class CoreUser extends BasicDomain{
    
	public static enum FROM_TYPE{
		PROACTIVE, 
		JM_INPUT, JM_SPREAD,
		COOP_SPREAD
	}
	
	private Long id;
	
	@RequiredField
	private String phone;
	 
    private String name;
    
    private String pid;
    
    private String fromType;
    
    private Long fromId;
    
    private Date createTime;
    
    private String wechatId;
    
    private String wechatName;
    
    private String wechatAvatar;
    
    public CoreUser(){}
    
    public CoreUser(Long id){
    	this.id = id;
    }
    
    public String getName() {
    	loadIfNecessary();
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
    	loadIfNecessary();
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWechatId() {
		loadIfNecessary();
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	public String getWechatName() {
		loadIfNecessary();
		return wechatName;
	}

	public void setWechatName(String wechatName) {
		this.wechatName = wechatName;
	}

	public String getFromType() {
		loadIfNecessary();
		return fromType;
	}
	
	public FROM_TYPE getFromTypeEnum(){
		return FROM_TYPE.valueOf(getFromType());
	}
	
	public void setFromType(String fromType) {
		this.fromType = fromType;
	}

	public Long getFromId() {
		loadIfNecessary();
		return fromId;
	}

	public void setFromId(Long fromId) {
		this.fromId = fromId;
	}

	public String getPid() {
		loadIfNecessary();
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
	
	public Date getCreateTime() {
		loadIfNecessary();
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getWechatAvatar() {
		return wechatAvatar;
	}

	public void setWechatAvatar(String wechatAvatar) {
		this.wechatAvatar = wechatAvatar;
	}

	@Override
	protected BasicDomain doLoad() {
		return CoreUserService.instance.getUserById(id);
	}
}
