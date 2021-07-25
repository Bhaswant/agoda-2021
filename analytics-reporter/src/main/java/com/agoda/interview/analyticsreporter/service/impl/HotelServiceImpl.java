package com.agoda.interview.analyticsreporter.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agoda.interview.analyticsreporter.helper.AnalyticsReporterLogs;
import com.agoda.interview.analyticsreporter.model.BookingData;
import com.agoda.interview.analyticsreporter.model.HotelSummary;
import com.agoda.interview.analyticsreporter.repository.BookingDataRepository;
import com.agoda.interview.analyticsreporter.service.HotelService;

/**
 * Implementation of {@link HotelService}
 * @author i0b00j8
 *
 */
@Service
public class HotelServiceImpl implements HotelService {

	@Autowired
	private BookingDataRepository repository;

	private Logger logger = LoggerFactory.getLogger(HotelServiceImpl.class);
	
	@Override
	public HotelSummary getHotelSummary(final String hotelId, final Optional<Double> conversionRate) {
		long startTime = System.currentTimeMillis();
		Optional<List<BookingData>> hotelBookings = repository.findAllByHotelId(hotelId);
		logger.debug(AnalyticsReporterLogs.FETCH_LOG, (System.currentTimeMillis()-startTime));
		List<BookingData> bookings = hotelBookings.orElse(new ArrayList<>());
		int numberOfBookings = bookings.size();
		double totalPriceUsd = 0;
		for(final BookingData booking  : bookings) {
			double rate = conversionRate.orElse(booking.getToUsdExchangeEate());
			totalPriceUsd = totalPriceUsd + (booking.getSellingPriceLocalCurrency() * rate);
		}
		return new HotelSummary(hotelId, numberOfBookings, totalPriceUsd);
	}

}
