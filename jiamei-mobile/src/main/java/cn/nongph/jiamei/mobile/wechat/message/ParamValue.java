package cn.nongph.jiamei.mobile.wechat.message;

public class ParamValue {
	private String value;
	private String color = "#173177";
	
	public ParamValue(String value){
		this.value = value;
	}
	
	public ParamValue(String value, String color){
		this.value = value;
		this.color = color;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
}
