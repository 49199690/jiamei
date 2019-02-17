package cn.nongph.jiamei.pay.wechat;

public abstract class WechatPayConfig {

	private String appId;
	    
	private String mchId;
	
	private String mchSecret;
	
	private String notifyCallback;
	
	private String device;
	
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getNotifyCallback() {
		return notifyCallback;
	}

	public void setNotifyCallback(String notifyCallback) {
		this.notifyCallback = notifyCallback;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getMchSecret() {
		return mchSecret;
	}

	public void setMchSecret(String mchSecret) {
		this.mchSecret = mchSecret;
	}
}
