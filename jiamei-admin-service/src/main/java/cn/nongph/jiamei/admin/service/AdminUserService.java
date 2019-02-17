package cn.nongph.jiamei.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.nongph.jiamei.admin.dao.AdminRoleDao;
import cn.nongph.jiamei.admin.dao.AdminUserDao;
import cn.nongph.jiamei.admin.domain.AdminRole;
import cn.nongph.jiamei.admin.domain.AdminUser;
import cn.nongph.jiamei.core.utils.Digests;
import cn.nongph.jiamei.core.utils.Encodes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Service
public class AdminUserService{

    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;
    public static final int SALT_SIZE = 8;

    @Resource
    private AdminUserDao dao;
    
    @Resource
	private AdminRoleDao roleDao;

    public List<AdminRole> getUserRoleList(Long userid){
        return roleDao.getRolesByUser(userid);
    }
    
    public List<AdminRole> getAllRoles(){
    	return roleDao.findAllRoles();
    }

    /**
     * @param UserName
     * @return
     */
    public AdminUser getAdminUserByUserName(String userName){
       return dao.getUserByName( userName );
    }
    
    public void saveAdminUser(AdminUser au){
    	dao.updateAdminUser(au);
    }
    /**
     * 
     * @param platformUser
     * @return
     */
    public void registerPlatformUser(AdminUser platformUser) {
        platformUser.setPassword( entryptPassword(platformUser.getPassword()) );
        dao.insertPlatformUser( platformUser );
    }

    /**
     * @param Id
     * @return
     */
    public AdminUser getAdminUserById(Long id){
    	return dao.getUserById( id ); 
    }

    public List<AdminUser> getAllUsers(){
    	return dao.getAllUsers() ; 
    }
    
    /**
     * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
     */
    private static String entryptPassword(String plainPassword) {
        byte[] salt = "nongph.cn".getBytes();
        byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
        return Encodes.encodeHex(hashPassword);
    }
           
    public void bindRoleToAdminUser( Long userId, Long roleId ){
    	dao.addUserRole(userId, roleId);
    }
    
    public void cancelRoleFromAdminUser( Long userId, Long roleId ) {
    	dao.deleteUserRole(userId, roleId);
    }
    
    public static AdminUserService instance;
    
    @PostConstruct
    public void setInstance(){
    	instance = this;
	}
}
