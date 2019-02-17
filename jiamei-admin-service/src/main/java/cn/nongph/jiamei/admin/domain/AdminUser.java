package cn.nongph.jiamei.admin.domain;

import java.util.List;

import cn.nongph.jiamei.admin.service.AdminUserService;
import cn.nongph.jiamei.core.annotation.RequiredField;
import cn.nongph.jiamei.core.domain.BasicDomain;

public class AdminUser extends BasicDomain{

    private Long id;
    
    @RequiredField
    private String username;
    
    private String password;
    
    private String name;
    
    private String mobile;
    
    //用户角色
    private List<AdminRole> roleList;
    
    public AdminUser(){
    }
    
    public AdminUser(Long id){
    	this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
    	loadIfNecessary();
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AdminRole> getRoleList() {
    	if( roleList==null ) {
    		roleList = AdminUserService.instance.getUserRoleList(id);
    	}
        return roleList;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMobile() {
		loadIfNecessary();
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
		
	public boolean isAdmin(){
		return canPerform(AdminRole.KEY.ADMIN);
	}
		
	/**
	 * 判断用户是否具有角色
	 * @param key
	 * @return
	 */
	public boolean canPerform(AdminRole.KEY key){
		for( AdminRole role : getRoleList() ) {
			if( role.getRolekey().equals( key.name() ) )
				return true;
		}
		return false;
	}
		
	@Override
	protected AdminUser doLoad() {
		return AdminUserService.instance.getAdminUserById(id);
	}
}
