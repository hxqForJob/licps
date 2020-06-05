package com.hxq.exception;

public class NoLoginException extends RuntimeException {

	public NoLoginException(String msString)
	{
		super(msString);
	}
	
	public NoLoginException()
	{
		super();
	}
}
