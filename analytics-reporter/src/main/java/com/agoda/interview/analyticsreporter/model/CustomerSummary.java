package com.agoda.interview.analyticsreporter.model;

import lombok.Data;

/**
 * Customer Summary output pojo
 * @author i0b00j8
 *
 */
@Data
public class CustomerSummary {
	
	private final String customerId;
	
	private final int numberOfBookings;
	
	private final double totalPriceUsd;
	
}
