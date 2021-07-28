package com.agoda.interview.analyticsreporter.helper;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.agoda.interview.analyticsreporter.exception.InvalidInputDataException;
import com.agoda.interview.analyticsreporter.exception.UnsupportedFormatException;
import com.agoda.interview.analyticsreporter.model.BookingData;

/**
 * Interface guiding the converter class.
 * @author Bhaswant
 *
 */
public interface IBookingDataConverter {
	
	/**
	 * 
	 * @param input
	 * @return
	 * @throws UnsupportedFormatException
	 * @throws IOException
	 * @throws InvalidInputDataException
	 */
	Optional<List<BookingData>> convert(final String input) throws UnsupportedFormatException, IOException, InvalidInputDataException;
}
