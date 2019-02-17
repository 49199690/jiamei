package cn.nongph.jiamei.mobile.wechat;

import cn.nongph.jiamei.core.utils.AESHandler;
import cn.nongph.jiamei.pay.wechat.WechatPayConfig;

public class WechatConfig extends WechatPayConfig{
	    
    private String appSecret;
    
    private WechatConfig(){
		setAppId( System.getProperty("wechat.appid") );
		setAppSecret( AESHandler.decrypt( System.getProperty("wechat.as") ) );
		setDevice( "gzh" );
		
		setMchId( AESHandler.decrypt( System.getProperty("wp.mid") ) );
		setMchSecret( AESHandler.decrypt( System.getProperty("wp.as") ) );
		setNotifyCallback("http://m.nongph.cn/mvc/close/api/pay/wechat/notify");
    }
    
    public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public static WechatConfig instance = null;
    
    static{
    	instance = new WechatConfig();
    }
}
