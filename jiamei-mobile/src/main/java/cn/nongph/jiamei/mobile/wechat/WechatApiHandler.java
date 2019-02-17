package cn.nongph.jiamei.mobile.wechat;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.HttpEntity;
import org.apache.http.impl.client.AbstractResponseHandler;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.nongph.jiamei.core.service.HttpHandler;
import cn.nongph.jiamei.mobile.wechat.message.SendResponse;
import cn.nongph.jiamei.mobile.wechat.message.TemplateMessage;

@Service
public class WechatApiHandler {
	
	private Logger logger = LoggerFactory.getLogger( this.getClass() );
	
	// token 接口
	private final String GET_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
	
	private final String GET_USER_INFO="https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s";
	
	private final String SEND_TEMPLAYE_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s";  
	
	@Resource
	private HttpHandler httpHandler; 
	
	private final ObjectMapper mapper = new ObjectMapper(); 
	
	public Map<String, Object> getUserInfo(String id) {		
		return httpHandler.doGet( String.format(GET_USER_INFO, getAccessToken(), id ), 
				                  new AbstractResponseHandler<Map<String, Object>>(){
			@Override
			public Map<String, Object> handleEntity(HttpEntity entity) throws IOException {
			    String response =  EntityUtils.toString(entity, "utf-8") ;
          		logger.info( "response of get user info :{}",  response );
          		return mapper.readValue( response, Map.class );
			}
		});
	}
	
	public SendResponse sendTemplateMessage(TemplateMessage wechatTemplate) {      
		String jsonString;
		try {
			jsonString = mapper.writeValueAsString( wechatTemplate );
		} catch (JsonProcessingException e) {
			logger.error("error on serialize to json", e);
			return null;
		}
		logger.info( "send template message :{}",jsonString );
		return httpHandler.doPost( String.format(SEND_TEMPLAYE_MESSAGE, getAccessToken()), 
				                   jsonString, new AbstractResponseHandler<SendResponse>(){
								@Override
								public SendResponse handleEntity(HttpEntity entity) throws IOException {
									String response = EntityUtils.toString(entity, "utf-8") ;
									logger.info("response of template message :{}", response);
									return mapper.readValue(response, SendResponse.class);
								}
							} );
	}  
	
	private String getAccessToken() {
		AccessToken token = WechatCache.getAccessToken();
		if(token == null ){
			token = doGetAccessToken();
			if( token!=null ) 
				WechatCache.cacheAccessToken( token );			
		}
		if( token == null ){
			throw new RuntimeException("can't get wechat api token");
		} else {
			return token.getAccessToken();
		}
	}
	
	private AccessToken doGetAccessToken(){
		String tockenUrl = String.format(GET_TOKEN, WechatConfig.instance.getAppId(), WechatConfig.instance.getAppSecret());
		AccessToken token = httpHandler.doGet(tockenUrl, new AbstractResponseHandler<AccessToken>() {
			@Override
			public AccessToken handleEntity(HttpEntity entity) throws IOException {
				String response =  EntityUtils.toString(entity, "utf-8");
				Map<String, Object> rsp = mapper.readValue( response, Map.class );
				if( rsp.containsKey("errcode") ) {
					logger.error( "error on get token from wechat, code:{}, desc:{}", rsp.get("errcode"), rsp.get("errmsg")  );
					return null;
				} else {
					AccessToken token = new AccessToken();
					token.setAccessToken( (String)rsp.get("access_token") );
					token.setExpiresIn( (Integer)rsp.get("expires_in") );
					return token;
				}
			}
		});
		return token;
	}
}
