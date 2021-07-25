package com.agoda.interview.analyticsreporter.model;

import lombok.Data;

@Data
public class CustomerSummary {
	
	private final String customerId;
	
	private final String numberOfBookings;
	
	private final String totalPriceUsd;
}
