package com.agoda.interview.analyticsreporter.exception;

/**
 * Thread Execution exception, generally thrown when there is an exception in
 * thread execution
 * 
 * @author Bhaswant
 *
 */
public class ThreadExecutionException extends Exception {

	private static final long serialVersionUID = 3531512344189645658L;

	public ThreadExecutionException(final String msg) {
		super(msg);
	}
}
