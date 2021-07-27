package com.agoda.interview.analyticsreporter.service;

import java.util.Optional;

import com.agoda.interview.analyticsreporter.exception.InvalidDataException;
import com.agoda.interview.analyticsreporter.model.HotelSummary;

/**
 * Hotel Service offers APIs to interact with Hotel related operations
 * @author Bhaswant
 *
 */
public interface HotelService {

	/**
	 * Assuming that provided conversion rate (if any) will be applicable to all
	 * prices across the currencies.
	 * 
	 * @param hotelId
	 * @param conversionRate
	 * @return
	 * @throws InvalidDataException 
	 */
	public HotelSummary getHotelSummary(final String hotelId, final Optional<Double> conversionRate) throws InvalidDataException;
}
