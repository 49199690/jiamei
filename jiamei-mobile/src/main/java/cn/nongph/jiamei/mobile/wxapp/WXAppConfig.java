package cn.nongph.jiamei.mobile.wxapp;

import cn.nongph.jiamei.core.utils.AESHandler;
import cn.nongph.jiamei.pay.wechat.WechatPayConfig;

public class WXAppConfig extends WechatPayConfig {
	
	private String appSecret;
    
    private WXAppConfig(){
		setAppId( AESHandler.decrypt(System.getProperty("wxapp.appid")) );
		setAppSecret( AESHandler.decrypt( System.getProperty("wxapp.as") ) );
		setDevice( "wxapp" );
		
		setMchId( AESHandler.decrypt( System.getProperty("wp.mid") ) );
		setMchSecret( AESHandler.decrypt( System.getProperty("wp.as") ) );
		
		setNotifyCallback("/mvc/close/api/pay/wechat/notify/wxapp");
    }
    
    public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public static WXAppConfig instance = null;
    
    static{
    	instance = new WXAppConfig();
    }
}