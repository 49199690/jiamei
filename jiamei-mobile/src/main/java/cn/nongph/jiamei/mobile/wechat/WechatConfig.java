package cn.nongph.jiamei.mobile.wechat;

import cn.nongph.jiamei.core.utils.AESHandler;
import cn.nongph.jiamei.pay.wechat.WechatPayConfig;

public class WechatConfig extends WechatPayConfig{
	    
    private String appSecret;
    
    private WechatConfig(){
		setAppId( System.getProperty("jiamei.wechat.appid") );
		setAppSecret( AESHandler.decrypt( System.getProperty("jiamei.wechat.as") ) );
		setDevice( "gzh" );
		
		setMchId( AESHandler.decrypt( System.getProperty("jiamei.wp.mid") ) );
		setMchSecret( AESHandler.decrypt( System.getProperty("jiamei.wp.as") ) );
		setNotifyCallback("http://jiamei.nongph.cn/mvc/close/pay/wechat/notify");
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
