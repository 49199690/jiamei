package cn.nongph.jiamei.admin.dao;

import cn.nongph.jiamei.admin.domain.AdminUser;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminUserDao{
	
    public AdminUser getUserById(Long id);
    
    public AdminUser getUserByName(String userName);
    
    public List<AdminUser> getAllUsers();
    
    public void insertPlatformUser( AdminUser user );
    
    public void updateAdminUser( AdminUser user );
    
    public void deleteUserRole(@Param("userId") Long userId, @Param("roleId")Long roleId );
    
    public void addUserRole(@Param("userId")Long userId, @Param("roleId")Long roleId );

}
