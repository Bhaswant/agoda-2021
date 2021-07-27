package com.agoda.interview.analyticsreporter.service;

import java.util.List;

import com.agoda.interview.analyticsreporter.exception.InvalidDataException;
import com.agoda.interview.analyticsreporter.exception.ThreadExecutionException;
import com.agoda.interview.analyticsreporter.model.CustomerSummary;

/**
 * 
 * @author Bhaswant
 *
 */
public interface CustomerService {

	public List<CustomerSummary> getCustomerSummary(final String customerId) throws ThreadExecutionException, InvalidDataException;
	
	public List<CustomerSummary> getCustomerSummary(final List<String> customerId) throws ThreadExecutionException;
}
