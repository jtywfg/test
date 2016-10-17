package com.jty.utils.exception;

/**
 * 业务异常
 * @author xxb05
 *
 */
public class BusinessException extends BaseException {
	private static final long serialVersionUID = 1L;

	public BusinessException() {
	}

	public BusinessException(String messageKey, Object arg) {
		this(messageKey, new Object[] { arg });
	}

	public BusinessException(String messageKey, Object[] args) {
		ExceptionCause cause = new ExceptionCause(messageKey, args);
		addCause(cause);
	}

	/**
	  * @Description：抛出业务异常
	  * @author aijian
	  * @Version: V1.00 
	  * @Create Date: 2014-7-3
	  * @Parameters：
	 */
	public BusinessException(String messageKey, boolean isResource) {
		super(messageKey);
		ExceptionCause cause = new ExceptionCause(messageKey, isResource);
		addCause(cause);
	}
	/**
	  * @Description：抛出业务异常
	  * @author aijian
	  * @Version: V1.00 
	  * @Create Date: 2014-7-3
	  * @Parameters：
	 */
	public BusinessException(String messageKey,Integer status, boolean isResource) {
		super(messageKey,status);
		ExceptionCause cause = new ExceptionCause(messageKey, isResource);
		addCause(cause);
	}
}