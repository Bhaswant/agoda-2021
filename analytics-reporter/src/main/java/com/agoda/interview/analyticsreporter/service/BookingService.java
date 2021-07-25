package com.agoda.interview.analyticsreporter.service;

import java.io.IOException;
import java.io.InputStream;

import com.agoda.interview.analyticsreporter.exception.InvalidDataException;
import com.agoda.interview.analyticsreporter.exception.UnsupportedFormatException;
import com.agoda.interview.analyticsreporter.helper.FileFormat;

/**
 * Hotel Service offers APIs to interact with Booking Data related operations
 * 
 * @author Bhaswant
 *
 */
public interface BookingService {
	
	void createBooking(final String format, final String bookingData) throws UnsupportedFormatException, IOException, InvalidDataException;

	void createBooking(final FileFormat format, final String bookingData) throws UnsupportedFormatException, IOException, InvalidDataException;

	void createBooking(final FileFormat format, final InputStream reader) throws IOException, UnsupportedFormatException, InvalidDataException;

}
