package cn.nongph.jiamei.mobile.wxapp;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.HttpEntity;
import org.apache.http.impl.client.AbstractResponseHandler;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.nongph.jiamei.core.service.HttpHandler;
import cn.nongph.jiamei.mobile.util.JSONHandler;

@Service
public class WXAppService {
	
	private Logger logger = LoggerFactory.getLogger(WXAppService.class);
	
	public static final String WX_API_JSCODE_TO_SESSION = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
	
	@Resource
	private HttpHandler httpHandler; 
	
	public WXJscode2SessionResult getSessionInfo(String jsCode){
		 String url = String.format( WX_API_JSCODE_TO_SESSION, WXAppConfig.instance.getAppId(), WXAppConfig.instance.getAppSecret(), jsCode );
		 return httpHandler.doGet( url, new AbstractResponseHandler<WXJscode2SessionResult>() {
					@Override
					public WXJscode2SessionResult handleEntity(HttpEntity entity) throws IOException {
						String response =  EntityUtils.toString(entity, "utf-8");
						try {
							Map<String, Object> rsp = JSONHandler.toMap(response);
							if( rsp.containsKey("errcode") ) {
								logger.error( "error on jscode to session, code:{}, desc:{}", rsp.get("errcode"), rsp.get("errmsg")  );
								return null;
							} else {
								WXJscode2SessionResult session = new WXJscode2SessionResult();
								session.setOpenid( (String)rsp.get("openid") );
								session.setSessionKey( (String)rsp.get("session_key") );
								return session;
							}
						} catch(Exception e) {
							logger.error( "can't parse response json:"+response, e);
							return null;
						}
					}
				});
	}
}
