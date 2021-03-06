package com.agoda.interview.analyticsreporter.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Constants file, mainly used for log messages.
 * 
 * @author Bhaswant
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AnalyticsReporterLogs {

	public static final String SUPPORTED_FILE_FORMATS = "Json, Csv";

	public static final String UNSUPPORTED_FORMAT_EXCEPTION = "Provided format {0} in headers is not valid, please provide one of "
			+ SUPPORTED_FILE_FORMATS;

	public static final String SUCCESS_MSG = "Successfully imported booking data";

	public static final String ERROR_IN_FILE_LOADING = "Error occured in loading seed data {}, ignoring for now since there is a provision to upload new data";

	public static final String SEED_LOADING_SUCCESSFUL = "Successfully imported seed data";

	public static final String EMPTY_RECORDS = "Found empty records to input";

	public static final String INVALID_DATA = "Found invalid data in the input";

	public static final String INSERTION_LOG = "Time taken to complete booking insert {}";

	public static final String FETCH_LOG = "Time taken to fetch records {}";

	public static final String THREAD_EXECTUION_EXCEPTION = "Exception occured while waiting for threads. {0}";
	
	public static final String FETCHING_SUMMARY_FOR = "Fetching summary for {}";
}
