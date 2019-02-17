package cn.nongph.jiamei.admin.domain;

import cn.nongph.jiamei.core.domain.BasicDomain;

public class AdminRole extends BasicDomain {
	
	public static enum KEY{
		ADMIN, TEACHER;
	}
	
    private Long id;
    
    private String rolekey;
    
    private String rolename;
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRolekey() {
        return rolekey;
    }

    public void setRolekey(String rolekey) {
        this.rolekey = rolekey;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

	@Override
	protected BasicDomain doLoad() {
		return null;
	}
}
