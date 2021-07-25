package com.agoda.interview.analyticsreporter.model;

import lombok.Data;

/**
 * 
 * @author i0b00j8
 *
 */
@Data
public class HotelSummary {

	private final String hotelId;
	
	private final int numberOfBookings;
	
	private final double totalPriceUsd;

}
