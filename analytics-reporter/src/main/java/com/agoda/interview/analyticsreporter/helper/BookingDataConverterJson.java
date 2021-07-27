package com.agoda.interview.analyticsreporter.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agoda.interview.analyticsreporter.exception.InvalidDataException;
import com.agoda.interview.analyticsreporter.model.BookingData;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

@Component
public class BookingDataConverterJson implements IBookingDataConverter {

	@Autowired
	private Gson gson;

	private Logger logger = LoggerFactory.getLogger(BookingDataConverterJson.class);

	@Override
	public Optional<List<BookingData>> convert(final String input) throws InvalidDataException {
		List<BookingData> bookingData = null;
		try {
			bookingData = new ArrayList<>();
			JsonObject inputObj = gson.fromJson(input, JsonObject.class);
			JsonArray inputArr = inputObj.get("bookings").getAsJsonArray();
			for (int i = 0; i < inputArr.size(); i++) {
				JsonObject bookingObj = inputArr.get(i).getAsJsonObject();
				String hotelId = bookingObj.get(AnalyticsReporterConstants.HOTEL_ID_KEY).getAsString();
				String bookingId = bookingObj.get(AnalyticsReporterConstants.BOOKING_ID_KEY).getAsString();
				String customerId = bookingObj.get(AnalyticsReporterConstants.CUSTOMER_ID_KEY).getAsString();
				double sellingPrice = bookingObj.get(AnalyticsReporterConstants.SELLING_PRICE).getAsDouble();
				String currency = bookingObj.get(AnalyticsReporterConstants.CURRENCY).getAsString();
				double exchangeRate = bookingObj.get(AnalyticsReporterConstants.USD_EXCHANGE_RATE).getAsDouble();
				bookingData.add(new BookingData(hotelId, bookingId, customerId, sellingPrice, currency, exchangeRate));
			}
		} catch (JsonSyntaxException e) {
			logger.error(AnalyticsReporterLogs.INVALID_DATA, e);
			throw new InvalidDataException(AnalyticsReporterLogs.INVALID_DATA);
		}
		return Optional.ofNullable(bookingData);
	}

}
