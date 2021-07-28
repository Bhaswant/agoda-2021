package com.agoda.interview.analyticsreporter.model;

import lombok.Data;

/**
 * Hotel Summary output Pojo.
 * @author Bhaswant
 *
 */
@Data
public class HotelSummary {

	private final String hotelId;
	
	private final int numberOfBookings;
	
	private final double totalPriceUsd;

}
