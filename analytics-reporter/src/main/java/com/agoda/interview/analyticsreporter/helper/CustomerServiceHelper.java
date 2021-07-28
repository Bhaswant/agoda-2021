package com.agoda.interview.analyticsreporter.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.agoda.interview.analyticsreporter.model.BookingData;
import com.agoda.interview.analyticsreporter.model.CustomerSummary;
import com.agoda.interview.analyticsreporter.repository.BookingDataRepository;
import com.agoda.interview.analyticsreporter.service.CustomerService;

/**
 * Helper class for {@link CustomerService}. It deals with identifying summary
 * per customer Id.
 * 
 * @author Bhaswant
 *
 */
public class CustomerServiceHelper implements Callable<CustomerSummary> {

	private BookingDataRepository repository;

	private final String customerId;

	public CustomerServiceHelper(final BookingDataRepository repository, final String customerId) {
		this.repository = repository;
		this.customerId = customerId;
	}

	/**
	 * Fetches summary per customer Id
	 * @return
	 */
	private CustomerSummary getSummary() {
		List<BookingData> bookings = repository.findAllByCustomerId(customerId).orElse(new ArrayList<>());
		int numberOfBookings = bookings.size();
		double totalPriceUsd = 0;
		for (final BookingData booking : bookings) {
			totalPriceUsd = totalPriceUsd + (booking.getSellingPriceLocalCurrency() * booking.getToUsdExchangeEate());
		}
		return new CustomerSummary(customerId, numberOfBookings, totalPriceUsd);
	}

	@Override
	public CustomerSummary call() throws Exception {
		return getSummary();
	}

}
