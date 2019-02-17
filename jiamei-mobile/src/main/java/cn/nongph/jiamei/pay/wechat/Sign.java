package cn.nongph.jiamei.pay.wechat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import cn.nongph.jiamei.core.utils.Digests;

public class Sign {
	
	public static String getSign(Map<String, String> map, WechatPayConfig pc){
	    List<String> list = new ArrayList<String>();
	    for (Map.Entry<String, String> entry : map.entrySet()) {
	      if( StringUtils.isNotEmpty(entry.getValue() ) ) {
	        list.add((String)entry.getKey() + "=" + entry.getValue() + "&");
	      }
	    }
	    int size = list.size();
	    String[] arrayToSort = (String[])list.toArray(new String[size]);
	    Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
	    
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < size; i++) {
	    	sb.append(arrayToSort[i]);
	    }
	    
	    String result = sb.toString();
	    result = result + "key=" + pc.getMchSecret();
	    result = Digests.md5Hex(result).toUpperCase();
	    return result;
	}
}
