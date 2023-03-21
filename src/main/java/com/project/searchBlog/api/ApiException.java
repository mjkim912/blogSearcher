package com.project.searchBlog.api;

public class ApiException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7919265419329943927L;

	
	public ApiException(int statusCode, String msg) {
		System.err.println("StatusCode : " + statusCode);
		System.err.println("Message : " + msg);
	}

	
}
