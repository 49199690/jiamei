package cn.nongph.jiamei.mobile.controller;

import java.net.URLEncoder;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.nongph.jiamei.core.domain.CoreUser;
import cn.nongph.jiamei.core.service.CoreUserService;
import cn.nongph.jiamei.mobile.util.CookieUtil;
import cn.nongph.jiamei.core.vo.UniversalResult;
import cn.nongph.jiamei.mobile.wechat.WechatConfig;
import cn.nongph.jiamei.mobile.wechat.WechatOAuthHandler;

/**
 * @author felix.wu
 * 错误吗分配03xx
 */
@Controller
@RequestMapping("/open/wechat/authorize")
public class WeChatAuthorizeController {
	
	@Resource
	private WechatOAuthHandler wechatHandler;
    
	@Resource
	private CoreUserService userService;
	
	@ResponseBody
	@RequestMapping("/base/url")
	public UniversalResult requestBaseAuthorize(HttpServletRequest request, @RequestHeader("user-agent") String ua, @RequestParam("au") String afterUrl ) throws Exception{
		if( ua.toLowerCase().contains("micromessenger") ) {//微信浏览器
	        String redirectUri = "http://"+request.getServerName()+"/mvc/open/wechat/authorize/base/callback";
	        if( StringUtils.isNotEmpty(afterUrl) )
	        	redirectUri = redirectUri + "?au=" + afterUrl;
	  
	        String href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WechatConfig.instance.getAppId()
	                 + "&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8")
	                 + "&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
	        return UniversalResult.createSuccessResult(href);
		} else {
			return UniversalResult.createErrorResult(301);
		}
	}
	
	@RequestMapping("/base/callback")
	public String authorizeBaseCallback(HttpServletResponse resp, @RequestParam String code, @RequestParam String au){
        Map<String, String> tokenMap = wechatHandler.requestOauthToken(code);
        CookieUtil.setCookie(resp, null, "wid", tokenMap.get("openid"), 30*24*60*60);
	    return "redirect:" + (StringUtils.isNotEmpty(au) ? au : "/");
	}
	
	@ResponseBody
	@RequestMapping("/info/url")
	public UniversalResult requestInfoAuthorize(HttpServletRequest request, @RequestHeader("user-agent") String ua, @RequestParam("au") String afterUrl ) throws Exception{
		if( ua.toLowerCase().contains("micromessenger") ) {//微信浏览器
	        String redirectUri = "http://"+request.getServerName()+"/mvc/open/wechat/authorize/info/callback";
	        if( StringUtils.isNotEmpty(afterUrl) )
	        	redirectUri = redirectUri + "?au=" + afterUrl;
	  
	        String href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WechatConfig.instance.getAppId()
	                 + "&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8")
	                 + "&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
	        return UniversalResult.createSuccessResult(href);
		} else {
			return UniversalResult.createErrorResult(301);
		}
	}
	
	@RequestMapping("/info/callback")
	public String authorizeInfoCallback(HttpServletRequest req, HttpServletResponse resp, @RequestParam String code, @RequestParam String au){
        Map<String, String> tokenMap = wechatHandler.requestOauthToken(code);
        String openid = tokenMap.get("openid");
        String token = tokenMap.get("access_token");
        
        Map<String, String> infoMap = wechatHandler.requestUserInfo(token, openid);
        String nickname = infoMap.get("nickname");
        
        CookieUtil.setCookie(resp, req.getServerName(), "wid", openid, 30*24*60*60);
        CookieUtil.setCookie(resp, req.getServerName(), "wnm", nickname, 30*24*60*60);
        
        String pid = CookieUtil.getCookie( req, "jmpid" );
        if( StringUtils.isNotEmpty(pid) ) {
        	CoreUser user = userService.getUserByPid(pid);
        	if( user!=null ) {
        		modifyWechatBind( user, openid, nickname );
        	} else {
        		CookieUtil.delCookie(resp, req.getServerName(), "jmpid");
        	}
        }
        return "redirect:" + (StringUtils.isNotEmpty(au) ? au : "/");
	}
	
	private void modifyWechatBind( CoreUser user, String openId, String nickname ) {
		boolean changed = false;
		if( !openId.equals( user.getWechatId() ) ) {
			user.setWechatId( openId );
			changed = true;
		}
		if( !nickname.equals( user.getWechatName() ) ) { 
			user.setWechatName( nickname );
			changed = true;
		}
		
		if( changed ) {
			userService.updateUser( user );
		}
	}
}
