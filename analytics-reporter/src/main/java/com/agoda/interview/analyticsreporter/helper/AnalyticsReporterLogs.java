package com.agoda.interview.analyticsreporter.helper;

/**
 * Constants file, mainly used for log messages.
 * 
 * @author Bhaswant
 *
 */
public class AnalyticsReporterLogs {

	public static final String SUPPORTED_FILE_FORMATS = "Json, Csv";

	public static final String UNSUPPORTED_FORMAT_EXCEPTION = "Provided format {0} in headers is not valid, please provide one of "
			+ SUPPORTED_FILE_FORMATS;

	public static final String SUCCESS_MSG = "Successfully imported booking data";

	public static final String ERROR_IN_FILE_LOADING = "Error occured in loading seed data {}, ignoring for now since there is a provision to upload new data";

	public static final String SEED_LOADING_SUCCESSFUL = "Successfully imported seed data";

	public static final String EMPTY_RECORDS = "Found empty records to input";
	
	public static final String INVALID_DATA = "Found invalid data in the input";
}
