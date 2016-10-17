package com.jty.utils.exception;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseException extends RuntimeException{
	protected Integer status = 0;
	private static final long serialVersionUID = 1L;
	private List<ExceptionCause> causeList = new ArrayList<ExceptionCause>();

	public BaseException() {
	}

	public BaseException(String message) {
		super(message);
	}
	public BaseException(String message,Integer status) {
		super(message);
		this.status = status;
	}

	public void addCause(ExceptionCause exceptionCause) {
		this.causeList.add(exceptionCause);
	}

	public List<ExceptionCause> getCauseList() {
		return this.causeList;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}