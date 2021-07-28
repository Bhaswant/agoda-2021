package com.agoda.interview.analyticsreporter.service;

import java.util.List;

import com.agoda.interview.analyticsreporter.exception.InvalidInputDataException;
import com.agoda.interview.analyticsreporter.exception.ThreadExecutionException;
import com.agoda.interview.analyticsreporter.model.CustomerSummary;

/**
 * Customer Service offers APIs to interact with customer Data related operations
 * 
 * @author Bhaswant
 *
 */
public interface CustomerService {

	/**
	 * 
	 * @param customerId
	 * @return
	 * @throws ThreadExecutionException
	 * @throws InvalidInputDataException
	 */
	public List<CustomerSummary> getCustomerSummary(final String customerId) throws ThreadExecutionException, InvalidInputDataException;
	
	/**
	 * 
	 * @param customerId
	 * @return
	 * @throws ThreadExecutionException
	 */
	public List<CustomerSummary> getCustomerSummary(final List<String> customerId) throws ThreadExecutionException;
}
