package cn.nongph.jiamei.mobile.controller;

import java.net.URLDecoder;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.nongph.jiamei.core.domain.CoreUser;
import cn.nongph.jiamei.core.service.CoreUserService;
import cn.nongph.jiamei.core.utils.AESHandler;
import cn.nongph.jiamei.core.utils.Digests;
import cn.nongph.jiamei.mobile.util.CookieUtil;
import cn.nongph.jiamei.core.vo.UniversalResult;
import cn.nongph.jiamei.mobile.vo.UserVO;

/**
 * error code
 * 0201:用户未登录错
 * 0202:登录手机号格式错误
 * 0203：登录确认手机号格式错误
 * 0204：登录手机号和确认手机号不相同
 * 0205：地址不存在
 * 0206:收货人手机号格式错误
 * @author felix.wu
 */
@Controller
@RequestMapping("/close/user")
public class UserController {
	private Logger logger = LoggerFactory.getLogger( UserController.class );
	
	@Resource
	private CoreUserService userServcie;
	
	private static Pattern phonePatten = Pattern.compile("1[3|4|5|7|8]\\d{9}");
	private static int PID_EXPIRE = 90*24*60*60;//3个月
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseBody
	public UniversalResult login(HttpServletRequest request, HttpServletResponse rsp, 
			                     @RequestParam String phone1, @RequestParam String phone2,
			                     @RequestParam(required=false) String aid){
		if( !phonePatten.matcher( phone1 ).matches() ) 
			return UniversalResult.createErrorResult( 202 );
		if( !phonePatten.matcher( phone2 ).matches() )
			return UniversalResult.createErrorResult( 203 );
		if( !phone1.equals(phone2) ) 
			return UniversalResult.createErrorResult( 204 );
		
		CoreUser user = findOrCreateUser(phone1, aid);
		user.setPid( Digests.md5HexWithSalt( user.getPhone(), String.valueOf(System.currentTimeMillis())) );
		CookieUtil.setCookie(rsp, null, "jmpid", user.getPid(), PID_EXPIRE);
		
		String wid = CookieUtil.getCookie(request, "wid");
		if( StringUtils.isNotEmpty(wid) ) {
			user.setWechatId(wid);
			String wnm = CookieUtil.getCookie(request, "wnm");
			if( StringUtils.isNotEmpty(wnm) ) {
				try {
					wnm = URLDecoder.decode(wnm, "UTF-8");
				} catch (Exception e) {
					logger.error( "can't url decode", e);
				}
				user.setWechatName( wnm );
			}
		}
		userServcie.updateUser( user );
		return UniversalResult.createSuccessResult( user.getPhone() );
	}
	
	@ResponseBody
	@RequestMapping(value="/login2", method=RequestMethod.POST)
	public UniversalResult login2(@RequestParam String phone, @RequestParam(required=false) String aid){
		CoreUser user = findOrCreateUser(phone, aid);
		return UniversalResult.createSuccessResult(user.getPhone());
	}
	
	private CoreUser findOrCreateUser(String phone, String aid) {
		CoreUser user = userServcie.findUserByPhone( phone );
		if( user==null ) {
			user = new CoreUser();
			user.setPhone(phone);
			if( StringUtils.isNotBlank( aid ) ) {
				String aidLong = AESHandler.decrypt(aid);
				
				user.setFromType( CoreUser.FROM_TYPE.COOP_SPREAD.name() );
				user.setFromId( Long.valueOf(aidLong) );
			}
			userServcie.createUser(user);
		}
		return user;
	}
	
	@ResponseBody
	@RequestMapping("/loginState")
	public UniversalResult checkLoginState(HttpServletRequest request, HttpServletResponse rsp){
		boolean logined = false;
		
		String pid = CookieUtil.getCookie(request, "jmpid");
		if( StringUtils.isNotEmpty( pid ) ) {
			CoreUser cu = userServcie.getUserByPid( pid );
			if( cu!=null ) {
				logined=true;
			} 
		}
		
		if( !logined ) {
			String wid = CookieUtil.getCookie(request, "wid");
			if( StringUtils.isNotEmpty( wid ) ) {
				CoreUser cu = userServcie.getUserByWid(wid);
				if( cu!=null ) {
					logined=true;
					CookieUtil.setCookie(rsp, null, "jmpid", cu.getPid(), PID_EXPIRE);
				}
			}
		}
		
		if( !logined && StringUtils.isNotEmpty( pid )) {
			//pid非法，或者过期
			CookieUtil.delCookie(rsp, null, "jmpid");
		}
		
		if( logined ) {
			return UniversalResult.createSuccessResult( "logined" );
		} else {
			return UniversalResult.createSuccessResult( "unlogin" );
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/base", method=RequestMethod.GET)
	public UniversalResult baseInfo(HttpServletRequest request){
		CoreUser u = (CoreUser)request.getAttribute("current_user");
		UserVO vo = new UserVO();
		vo.setName( u.getName() );
		vo.setPhone( u.getPhone() );
		return UniversalResult.createSuccessResult( vo );
	}
}
