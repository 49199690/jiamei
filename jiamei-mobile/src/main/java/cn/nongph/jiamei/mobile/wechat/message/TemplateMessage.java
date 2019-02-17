package cn.nongph.jiamei.mobile.wechat.message;

import java.util.Map;

public class TemplateMessage {
	private String touser;  
    private String template_id;  
    private String url = "http://m.nongph.cn/profile.html";  
    private Map<String, ParamValue> data;
    
    private TemplateMessage(String to, String tid){
    	this.touser = to;
    	this.template_id = tid;
    }
    
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Map<String, ParamValue> getData() {
		return data;
	}
	public void setData(Map<String, ParamValue> data) {
		this.data = data;
	}  
	
	public static TemplateMessage createOutMessage(String toUser){
    	return new TemplateMessage(toUser, "1R731iOTeKVgIeuKGQQo_7oiqlzYVne7V_1CDUXPfDg");
    }
	
	public static TemplateMessage createAssignMessage(String toUser){
		return new TemplateMessage(toUser, "lgpDa78OuotTFPViR1Y2NM1H_z-06AMDz0Kz1UIJ_1g");
	}
	
	public static TemplateMessage createDeliveryMessage(String toUser){
		return new TemplateMessage(toUser, "zM2Iz3DnW8iIgVT_YAxkEOHgbjuAXMTkCyCPsezYWIo");
	}
}
