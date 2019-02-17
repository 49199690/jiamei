package cn.nongph.jiamei.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import cn.nongph.jiamei.core.domain.CoreUser;

/**
 * Created by felix.wu
 */
@Repository
public interface CoreUserDao {
	
	public List<CoreUser> findAllUsers();
	
	public PageList<CoreUser> findUsers(PageBounds pageBounds, @Param("key")String key);
	
    public CoreUser findUserByPhone(@Param("phone") String phone);
    
    public CoreUser getUserById(Long id);
    
    public CoreUser getUserByPid(String pid);
    
    public CoreUser getUserByWid(String wid);
        
    public void insertUser( CoreUser user);
    
    public void updateUser( CoreUser user); 
    
}
