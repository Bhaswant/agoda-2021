package com.agoda.interview.analyticsreporter.model;

import lombok.Data;

@Data
public class CustomerSummary {
	
	private final String customerId;
	
	private final int numberOfBookings;
	
	private final double totalPriceUsd;
	
	public static CustomerSummary emptyObject(final String customerId) {
		return new CustomerSummary(customerId, 0, 0);
	}
}
