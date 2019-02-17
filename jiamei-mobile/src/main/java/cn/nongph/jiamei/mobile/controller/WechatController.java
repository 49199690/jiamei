package cn.nongph.jiamei.mobile.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.nongph.jiamei.mobile.util.CookieUtil;
import cn.nongph.jiamei.core.vo.UniversalResult;
import cn.nongph.jiamei.mobile.wechat.WechatApiHandler;

/**
 * @author felix.wu
 * error code 06xx:
 * 0601非法访问
 */
@Controller
@RequestMapping("/open/wechat")
public class WechatController {
	
	@Resource
	private WechatApiHandler wechatApiHandler;
	
	@RequestMapping("/user/isSubscribe")
	@ResponseBody
	public UniversalResult isSubscribe(HttpServletRequest request){
		String wid = CookieUtil.getCookie( request, "wid");
		if( StringUtils.isEmpty(wid) ) {
			return UniversalResult.createErrorResult( 601 );
		} else {
			Map<String, Object> userInfo = wechatApiHandler.getUserInfo( wid );
			return UniversalResult.createSuccessResult( userInfo.get("subscribe").equals(1) );
		}
	}
}
