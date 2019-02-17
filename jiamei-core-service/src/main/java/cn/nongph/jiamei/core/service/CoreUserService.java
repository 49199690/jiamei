package cn.nongph.jiamei.core.service;

import java.util.Date;
import java.util.List;

import cn.nongph.jiamei.core.dao.CoreUserDao;
import cn.nongph.jiamei.core.domain.CoreUser;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Service
public class CoreUserService{
	
	@Resource
    private CoreUserDao dao;

	public List<CoreUser> findAllUsers(){
		return dao.findAllUsers();
	}
	
	public PageList<CoreUser> findUsers(PageBounds pageBounds, String key){
		if( StringUtils.isBlank(key) )
			key = null;
		return dao.findUsers(pageBounds, key);
	}
	
    public CoreUser findUserByPhone(String phone){
        return dao.findUserByPhone(phone);
    }
    
    public CoreUser getUserById(Long id){
    	return dao.getUserById(id);
    }
    
    public CoreUser getUserByPid(String pid){
    	return dao.getUserByPid(pid);
    }
    
    public CoreUser getUserByWid(String wid){
    	return dao.getUserByWid(wid);
    }
    
    public void createUser(CoreUser user){
		user.setCreateTime( new Date() );
		dao.insertUser( user );
    }
    
    public void updateUser(CoreUser user){
    	dao.updateUser(user);
    }
        
    public CoreUser findOrUpdateOrCreateUser(CoreUser user){
    	CoreUser existUser = dao.findUserByPhone( user.getPhone() );
    	if( existUser==null ) {
    		createUser( user );
    		return user;
    	} else {
    		if( !StringUtils.equals(existUser.getName(), user.getName()) ) {
    			existUser.setName( user.getName() );
    			dao.updateUser(existUser);
    		}
    		return existUser;
    	}
    }
    
    public CoreUser findOrCreateUserByPhone(String phone){
    	CoreUser user = dao.findUserByPhone( phone );
    	if( user==null ) {
    		user = new CoreUser();
    		user.setPhone(phone);
    		createUser( user );
    	}
    	return user;
    }
    
    public static CoreUserService instance;
    
    @PostConstruct
    public void setInstance(){
    	instance = this;
    }
}