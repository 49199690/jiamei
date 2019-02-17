package cn.nongph.jiamei.mobile.util;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONHandler {
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	public static Map<String, Object> toMap(String json) throws Exception{
		return mapper.readValue( json, Map.class );
	}
}
