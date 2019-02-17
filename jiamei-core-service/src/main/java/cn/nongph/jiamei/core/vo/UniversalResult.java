package cn.nongph.jiamei.core.vo;

public class UniversalResult {
	private Head head;
	
	private Object body;
	
	public UniversalResult(){
		head = new Head();
	}
	
	public Head getHead() {
		return head;
	}

	public void setHead(Head head) {
		this.head = head;
	}

	public Object getBody() {
		return body;
	}

	public UniversalResult setBody(Object body) {
		this.body = body;
		return this;
	}
	
	public static UniversalResult createSuccessResult(){
		return createSuccessResult(null);
	}
	
	public static UniversalResult createSuccessResult(Object data){
		UniversalResult result = new UniversalResult();
		result.getHead().setState(Head.STATE_SUCCESS);
		result.getHead().setCode( 0 );
		result.setBody(data);
		
		return result;
	}
	
	public static UniversalResult createErrorResult(int code){
		UniversalResult result = new UniversalResult();
		result.getHead().setState(Head.STATE_ERROR);
		result.getHead().setCode(code);
		return result;
	}
	
	public static class Head{
		public static final String STATE_SUCCESS = "success";
	    public static final String STATE_ERROR = "error";
	    
		private String state;
		private int code;
		
		public String getState() {
			return state;
		}
		public void setState(String result) {
			this.state = result;
		}
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
	}
}
