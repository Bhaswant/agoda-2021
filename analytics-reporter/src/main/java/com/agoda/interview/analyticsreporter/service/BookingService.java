package com.agoda.interview.analyticsreporter.service;

import java.io.IOException;
import java.io.InputStream;

import com.agoda.interview.analyticsreporter.exception.InvalidInputDataException;
import com.agoda.interview.analyticsreporter.exception.UnsupportedFormatException;
import com.agoda.interview.analyticsreporter.helper.FileFormat;

/**
 * Booking Service offers APIs to interact with Booking Data related operations
 * 
 * @author Bhaswant
 *
 */
public interface BookingService {

	/**
	 * Creates booking based on input format and booking data
	 * 
	 * @param format
	 * @param bookingData
	 * @throws UnsupportedFormatException
	 * @throws IOException
	 * @throws InvalidInputDataException
	 */
	void createBooking(final String format, final String bookingData)
			throws UnsupportedFormatException, IOException, InvalidInputDataException;

	/**
	 * Creates booking based on supported formats and booking data. Currently
	 * supporting Json and CSV.
	 * 
	 * @param format
	 * @param bookingData
	 * @throws UnsupportedFormatException
	 * @throws IOException
	 * @throws InvalidInputDataException
	 */
	void createBooking(final FileFormat format, final String bookingData)
			throws UnsupportedFormatException, IOException, InvalidInputDataException;

	/**
	 * Creates booking based on supported formats and booking data. Currently
	 * supporting Json and CSV.
	 * 
	 * @param format
	 * @param reader
	 * @throws IOException
	 * @throws UnsupportedFormatException
	 * @throws InvalidInputDataException
	 */
	void createBooking(final FileFormat format, final InputStream reader)
			throws IOException, UnsupportedFormatException, InvalidInputDataException;

}
