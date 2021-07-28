package com.agoda.interview.analyticsreporter.exception;

/**
 * Unsupported format exception generally thrown when the format requested is
 * not supported by the application
 * 
 * @author Bhaswant
 *
 */
public class UnsupportedFormatException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2533228003660057808L;

	public UnsupportedFormatException(final String errorMsg) {

		super(errorMsg);
	}
}
