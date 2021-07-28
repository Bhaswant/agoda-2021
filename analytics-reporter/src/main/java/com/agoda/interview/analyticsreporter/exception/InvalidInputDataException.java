package com.agoda.interview.analyticsreporter.exception;

/**
 * Invalid data exception, generally thrown when the input data is invalid
 * @author Bhaswant
 *
 */
public class InvalidInputDataException extends Exception {

	private static final long serialVersionUID = 9185034185966451285L;

	public InvalidInputDataException(final String msg) {
		super(msg);
	}
}
