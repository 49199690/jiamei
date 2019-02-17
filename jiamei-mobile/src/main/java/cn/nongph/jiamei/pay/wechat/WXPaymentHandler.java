package cn.nongph.jiamei.pay.wechat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class WXPaymentHandler {
	private static final Logger log = LoggerFactory.getLogger(WXPaymentHandler.class);
	private static final String UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	private static final String ORDER_STATUS_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
	
	/*
	public Map<String, String> createUnifiedOrder(String notifyDomain, String ip, String openId, CoreOrder order, WechatPayConfig pc) {
    	String body = order.getOrderItemList().get(0).getSku().getName() + "...";
    	int money = order.getShouldPayAmount()*100;
        UnifiedOrder unifiedOrder = new UnifiedOrder();
        unifiedOrder.setAppid( pc.getAppId() );
        unifiedOrder.setMch_id( pc.getMchId() );
        unifiedOrder.setDevice_info( pc.getDevice() );
        unifiedOrder.setOut_trade_no(order.getOrderNumber());
        unifiedOrder.setTotal_fee(money);
        unifiedOrder.setBody(body.trim());
        unifiedOrder.setSpbill_create_ip( ip );
        unifiedOrder.setNotify_url( "http://"+notifyDomain+pc.getNotifyCallback() );
        unifiedOrder.setTrade_type("JSAPI");
        unifiedOrder.setOpenid(openId);
        
        return requestCreateUnifiedOrder(unifiedOrder, pc);
    }
    */
	private Map<String, String> requestCreateUnifiedOrder(UnifiedOrder unifiedOrder, WechatPayConfig pc){
	    Map<String, String> requestParam = BeanUtils.bean2Map(unifiedOrder);
	    Map<String, String> response = doRequest(UNIFIEDORDER_URL, requestParam, pc);
	    if( response!=null ) {
	    	Map<String, String> wcPayMap = new HashMap<String, String>();
	    	wcPayMap.put("appId", response.get("appid"));
	    	wcPayMap.put("timeStamp", Long.toString(System.currentTimeMillis() / 1000L));
	    	wcPayMap.put("nonceStr", RandomStringGenerator.getRandomStringByLength(32));
	    	wcPayMap.put("package", "prepay_id=" + response.get("prepay_id"));
	    	wcPayMap.put("signType", "MD5");
	    	wcPayMap.put("paySign", Sign.getSign(wcPayMap, pc));
		    return wcPayMap;
	    } else {
	    	throw new RuntimeException("request create unified order fail");
	    }
	}
	
	 public Map<String, String> queryWechatPayStatus(String orderNumber, WechatPayConfig pc) {
        OrderQueryRequest request = new OrderQueryRequest();
        request.setAppid( pc.getAppId() );
        request.setMch_id( pc.getMchId() );
        request.setOut_trade_no(orderNumber);
        
        Map<String, String> requestParam = BeanUtils.bean2Map(request);
	    return doRequest(ORDER_STATUS_URL, requestParam, pc);
    }
		    
	private Map<String, String> doRequest(String url, Map<String, String> requestParam, WechatPayConfig pc){
		requestParam.put("nonce_str", RandomStringGenerator.getRandomStringByLength(32));
		requestParam.put("sign", Sign.getSign(requestParam, pc));
	    String xmlParam = XmlMapper.map2Xml(requestParam);
	    Map<String, String> result = httpsXmlRequest(url, "POST", xmlParam);
	    if( result!=null ) {
	    	if( result.get("return_code").equals("SUCCESS") ){
	    		if( result.get("result_code").equals("SUCCESS") ) {
	    			log.info("Wechatpay request success");
	    			return result;
	    		} else {
	    			log.info("Wechatpay business error : err_code {}, err_code_des {}", result.get("err_code"), result.get("err_code_des"));
	    		}
	    	} else {
	    		log.info("Wechatpay communication error : {}", result.get("return_msg"));
	    	}
	    } else {
	    	log.info("Wechatpay error : can not request api");
	    }
	    return null;
	}
	  
    private Map<String, String> httpsXmlRequest(String requestUrl, String requestMethod, String outputStr){
        StringBuilder buffer = new StringBuilder();
	    try{
	      TrustManager[] tm = { new CommonX509TrustManager() };
	      
	      System.setProperty("https.protocols", "TLSv1");
	      SSLContext sslContext = SSLContext.getInstance("TLSv1");
	      
	      sslContext.init(null, tm, new SecureRandom());
	      
	      SSLSocketFactory ssf = sslContext.getSocketFactory();
	      
	      HttpsURLConnection httpUrlConn = (HttpsURLConnection)new URL(requestUrl).openConnection();
	      httpUrlConn.setSSLSocketFactory(ssf);
	      
	      httpUrlConn.setDoOutput(true);
	      httpUrlConn.setDoInput(true);
	      httpUrlConn.setUseCaches(false);
	      
	      httpUrlConn.setRequestMethod(requestMethod);
	      if( "GET".equalsIgnoreCase(requestMethod) ) {
	        httpUrlConn.connect();
	      }
	      if( outputStr!=null ){
	        OutputStream outputStream = httpUrlConn.getOutputStream();
	        outputStream.write(outputStr.getBytes("UTF-8"));
	        outputStream.close();
	      }
	      InputStream inputStream = httpUrlConn.getInputStream();
	      InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
	      BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	      String str;
	      while ((str = bufferedReader.readLine()) != null) {
	        buffer.append(str);
	      }
	      bufferedReader.close();
	      inputStreamReader.close();
	      
	      inputStream.close();
	      httpUrlConn.disconnect();
	      return XmlMapper.xml2Map(buffer.toString());
	    } catch (ConnectException e){
	      log.error("ConnectException :{}", e);
	    } catch (Exception e) {
	      log.error("https request error:{}", e);
	    }
	    return null;
	}
}
