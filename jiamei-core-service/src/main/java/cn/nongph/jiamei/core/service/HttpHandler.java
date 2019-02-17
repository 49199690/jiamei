package cn.nongph.jiamei.core.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.LoggerFactory;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

@Service
public class HttpHandler {
	
	private org.slf4j.Logger logger = LoggerFactory.getLogger( HttpHandler.class );
	
	private CloseableHttpClient httpClient = null;
	
	@PostConstruct
	public void init(){
		HttpClientBuilder builder = HttpClients.custom();
		builder.setConnectionTimeToLive(60, TimeUnit.SECONDS);
		builder.setMaxConnTotal(10);
		httpClient = builder.build();
	}
	
	@PreDestroy
	public void close(){
		try {
			httpClient.close();
		} catch (IOException e) {
			logger.error( "exception on close httpClient", e );
		}
	}
	
	public <T> T doGet(String url, ResponseHandler<T> handler){
		HttpGet httpGet = new HttpGet(url);
		long start = System.currentTimeMillis();
		try {
			return httpClient.execute(httpGet, handler); 
		} catch (Exception e){
			logger.error( "error on call "+httpGet.getURI(), e);
			return null;
		} finally {
			logger.info("send request to: {}, execute time: {}ms", httpGet.getURI(), System.currentTimeMillis() - start);
		}
	}
	
	public <T> T doPost(String url, Map<String, String> params, ResponseHandler<T> handler){
		List<NameValuePair> nvps = new ArrayList <NameValuePair>();
		if( params!=null && !params.isEmpty() ) {
			params.forEach( (name, value)->{
								nvps.add( new BasicNameValuePair(name, value) );
							 } );
		}
		HttpPost httpPost = new HttpPost(url);
		long start = System.currentTimeMillis();
		try{
			httpPost.setEntity( new UrlEncodedFormEntity(nvps, "UTF-8") );
			return httpClient.execute(httpPost, handler); 
		} catch (Exception e){
			logger.error( "error on call "+httpPost.getURI(), e);
			return null;
		} finally {
			logger.info("send request to: {}, execute time: {}ms", httpPost.getURI(), System.currentTimeMillis() - start);
		}
	}
	
	public <T> T doPost(String url, String requestBody, ResponseHandler<T> handler){
		HttpPost httpPost = new HttpPost(url);
		long start = System.currentTimeMillis();
		try{
			httpPost.setEntity( new StringEntity(requestBody, "UTF-8") );
			return httpClient.execute(httpPost, handler); 
		} catch (Exception e){
			logger.error( "error on call "+httpPost.getURI(), e);
			return null;
		} finally {
			logger.info("send request to: {}, execute time: {}ms", httpPost.getURI(), System.currentTimeMillis() - start);
		}
	}
}
