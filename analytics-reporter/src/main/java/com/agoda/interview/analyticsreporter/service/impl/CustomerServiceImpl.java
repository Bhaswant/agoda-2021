package com.agoda.interview.analyticsreporter.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.agoda.interview.analyticsreporter.exception.InvalidDataException;
import com.agoda.interview.analyticsreporter.exception.ThreadExecutionException;
import com.agoda.interview.analyticsreporter.helper.AnalyticsReporterLogs;
import com.agoda.interview.analyticsreporter.helper.CustomerServiceHelper;
import com.agoda.interview.analyticsreporter.model.CustomerSummary;
import com.agoda.interview.analyticsreporter.repository.BookingDataRepository;
import com.agoda.interview.analyticsreporter.service.CustomerService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

/**
 * Implementation of {@link CustomerService}
 * @author Bhaswant
 *
 */
@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private Gson gson;

	@Autowired
	private BookingDataRepository repository;

	@Value("${thread-size}")
	private int threadSize;

	private Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Override
	public List<CustomerSummary> getCustomerSummary(final String payload)
			throws ThreadExecutionException, InvalidDataException {
		List<String> customerIds = getCustomerIds(payload);
		return getCustomerSummary(customerIds);
	}

	@Override
	public List<CustomerSummary> getCustomerSummary(final List<String> customerIds) throws ThreadExecutionException {
		ExecutorService executorService = Executors.newFixedThreadPool(threadSize);
		List<CustomerSummary> customerSummaryOutput = new ArrayList<>();
		List<Future<CustomerSummary>> customerFutures = new ArrayList<>();
		for (String customerId : customerIds) {
			customerFutures.add(executorService.submit(new CustomerServiceHelper(repository, customerId)));
		}
		for (Future<CustomerSummary> future : customerFutures) {
			try {
				customerSummaryOutput.add(future.get());
			} catch (InterruptedException | ExecutionException e) {
				// Throwing the exception as we consider this response as either full or none.
				throw new ThreadExecutionException(
						String.format(AnalyticsReporterLogs.THREAD_EXECTUION_EXCEPTION, e.getLocalizedMessage()));
			}
		}
		return customerSummaryOutput;
	}

	/**
	 * Given a valid payload, breaks the payload into multiple customer Ids
	 * 
	 * @param payload
	 * @return
	 * @throws InvalidDataException
	 */
	private List<String> getCustomerIds(final String payload) throws InvalidDataException {
		List<String> customerIds = new ArrayList<>();
		try {
			JsonArray customerIdsJson = gson.fromJson(payload, JsonObject.class).get("customer_ids").getAsJsonArray();
			customerIdsJson.iterator().forEachRemaining(customerId -> customerIds.add(customerId.getAsString()));
		} catch (JsonSyntaxException e) {
			logger.error(AnalyticsReporterLogs.INVALID_DATA, e);
			throw new InvalidDataException(AnalyticsReporterLogs.INVALID_DATA);
		}
		return customerIds;
	}
}
