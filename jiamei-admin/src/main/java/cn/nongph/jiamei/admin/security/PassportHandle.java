package cn.nongph.jiamei.admin.security;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import cn.nongph.jiamei.admin.utils.CookieUtil;
import cn.nongph.jiamei.admin.domain.AdminUser;
import cn.nongph.jiamei.admin.service.AdminUserService;

import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PassportHandle {
    
    private static String PASSPORT_TOKEN = "passport_token";
    
    private static ConcurrentMap<String, AdminUser> passportMap = new ConcurrentHashMap<String, AdminUser>(); 
    
    private static int TOKEN_EXPIRE = 24*60*60;
    
    public static ServletContext application;
    
    private static AdminUserService platformUserService;

    public static final String CACHE_USER = "CACHE_USER";

    public static void init( ServletContext application ) {
    	platformUserService = (AdminUserService)WebApplicationContextUtils.getWebApplicationContext(application).getBean( AdminUserService.class );
    }
    
    public static AdminUser getCurrentUser(HttpServletRequest request){
    	Cookie token = WebUtils.getCookie( request, PASSPORT_TOKEN );
    	if( token!=null ) {
    		 return passportMap.get( token.getValue() );
    	} else {
    		return null;
    	}
    }
   
    /**
     * 完成登录，如果成功返回passport token
     * @param userName
     * @param pass
     * @return
     */
    public static String doLogin(String userName, String pass, HttpServletRequest request, HttpServletResponse rsp) {
    	AdminUser user = platformUserService.getAdminUserByUserName(userName);
    	if( user!=null && user.getPassword().equals(pass) ) {
    		String token = UUID.randomUUID().toString();
    		CookieUtil.setCookie( rsp, null, PASSPORT_TOKEN, token, TOKEN_EXPIRE );
    		passportMap.put( token, user );
    		return token;
    	} else {
    		return null;
    	}
    }
    
    public static void doLogout( HttpServletRequest request, HttpServletResponse rsp ) {
    	String token = CookieUtil.getCookie(request, PASSPORT_TOKEN);
    	if( token!=null ) {
    		CookieUtil.delCookie( rsp, null, PASSPORT_TOKEN);
    		passportMap.remove( token );
    	}
    }
}
