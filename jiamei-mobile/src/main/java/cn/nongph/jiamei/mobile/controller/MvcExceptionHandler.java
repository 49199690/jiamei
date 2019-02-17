package cn.nongph.jiamei.mobile.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.nongph.jiamei.core.vo.UniversalResult;

@ControllerAdvice
public class MvcExceptionHandler {
	
	private Logger logger = LoggerFactory.getLogger( MvcExceptionHandler.class );
	
	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public UniversalResult handleIOException(RuntimeException ex) {
		logger.error(ex.getMessage(), ex);
		return UniversalResult.createErrorResult(500).setBody( ex.toString() );
	}
}
