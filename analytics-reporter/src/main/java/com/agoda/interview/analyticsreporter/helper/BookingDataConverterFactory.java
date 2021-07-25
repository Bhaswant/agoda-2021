package com.agoda.interview.analyticsreporter.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agoda.interview.analyticsreporter.exception.UnsupportedFormatException;

/**
 * Factory class which enables various converters
 * 
 * @author Bhaswant
 *
 */
@Component
public class BookingDataConverterFactory {

	@Autowired
	private IBookingDataConverter bookingDataConverterCsv;

	@Autowired
	private IBookingDataConverter bookingDataConverterJson;

	/**
	 * 
	 * @param format
	 * @return
	 * @throws UnsupportedFormatException
	 */
	public IBookingDataConverter getConverter(final FileFormat format) throws UnsupportedFormatException {
		switch (format) {
		case CSV:
			return bookingDataConverterCsv;
		case JSON:
			return bookingDataConverterJson;
		default:
			throw new UnsupportedFormatException(String.format(AnalyticsReporterLogs.UNSUPPORTED_FORMAT_EXCEPTION, format));
		}
	}
}
