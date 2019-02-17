package cn.nongph.jiamei.admin.utils;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONHandler {
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	public static Map toMap(String json) throws Exception{
		return mapper.readValue( json, Map.class );
	}
}
