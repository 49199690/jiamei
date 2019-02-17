package cn.nongph.jiamei.core.exception;

public class OperatorTypeNotExistException extends RuntimeException {

	private static final long serialVersionUID = -1368431464160660518L;
	
	public OperatorTypeNotExistException(String name){
		super( name + " operator type not exist");
	}

}
