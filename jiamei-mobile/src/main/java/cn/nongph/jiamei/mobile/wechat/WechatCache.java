package cn.nongph.jiamei.mobile.wechat;

public class WechatCache {
	
	private static AccessToken lastAccessToken = null;

	public static void cacheAccessToken(AccessToken token) {
		lastAccessToken = token;
	}

	public static AccessToken getAccessToken() {
		if( lastAccessToken!=null && lastAccessToken.isExpires() ) {
			lastAccessToken = null;
		}
		return lastAccessToken;
	}

}
