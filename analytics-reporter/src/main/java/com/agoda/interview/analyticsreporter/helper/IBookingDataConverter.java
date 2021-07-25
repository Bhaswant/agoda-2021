package com.agoda.interview.analyticsreporter.helper;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.agoda.interview.analyticsreporter.exception.InvalidDataException;
import com.agoda.interview.analyticsreporter.exception.UnsupportedFormatException;
import com.agoda.interview.analyticsreporter.model.BookingData;

/**
 * 
 * @author Bhaswant
 *
 */
public interface IBookingDataConverter {
	
	Optional<List<BookingData>> convert(final String input) throws UnsupportedFormatException, IOException, InvalidDataException;
}
