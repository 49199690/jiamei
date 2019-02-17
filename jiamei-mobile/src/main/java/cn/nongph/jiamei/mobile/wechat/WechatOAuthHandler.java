package cn.nongph.jiamei.mobile.wechat;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.nongph.jiamei.core.service.HttpHandler;

import org.apache.http.HttpEntity;
import org.apache.http.impl.client.AbstractResponseHandler;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

/**
 * @author felix.wu
 * @since 2015-09-20
 */
@Component
public class WechatOAuthHandler {    
    private Logger logger = LoggerFactory.getLogger( WechatOAuthHandler.class );
    
    @Resource
    private HttpHandler httpHandler;
    
    private final ObjectMapper mapper = new ObjectMapper(); 
         
    public final static String TRATE_STATE = "trade_state";
    
    /**
     * request openId by code
     * 超时返回null，调用者需要处理
     * @param code {@code String}
     * @return openid
     */
    public Map<String, String> requestOauthToken(String code) {
        final String requestURL = "https://api.weixin.qq.com/sns/oauth2/access_token"
                + "?appid=" + WechatConfig.instance.getAppId()
                + "&secret=" + WechatConfig.instance.getAppSecret()
                + "&code=" + code
                + "&grant_type=authorization_code";
        Map<String, String> result = httpHandler.doGet( requestURL, new AbstractResponseHandler<Map<String, String>>(){
          	 public Map<String, String> handleEntity(HttpEntity entity)throws IOException{
           		 String response =  EntityUtils.toString(entity, "utf-8") ;
           		 logger.info( "request access token rsp:" + response );
           		 return mapper.readValue( response, Map.class );
           	 }
        });
        return result;
    }
    
    public Map<String, String> requestUserInfo(String token, String openid){
    	 final String requestURL = "https://api.weixin.qq.com/sns/userinfo?"
    			 +"access_token="+token
    			 +"&openid="+openid
    			 +"&lang=zh_CN";
    	 Map<String, String> result = httpHandler.doGet( requestURL, new AbstractResponseHandler<Map<String, String>>(){
          	 public Map<String, String> handleEntity(HttpEntity entity)throws IOException{
           		 String response =  EntityUtils.toString(entity, "utf-8") ;
           		 logger.info( "request user info rsp:" + response );
           		 return mapper.readValue( response, Map.class );
           	 }
        });
    	return result;
    }
}
