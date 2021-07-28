package com.agoda.interview.analyticsreporter.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.agoda.interview.analyticsreporter.exception.InvalidInputDataException;
import com.agoda.interview.analyticsreporter.model.BookingData;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

/**
 * Implementation of {@link IBookingDataConverter} for Json implementations
 * 
 * @author Bhaswant
 *
 */
@Component
public class BookingDataConverterJson implements IBookingDataConverter {

	private static final Gson gson = new Gson();

	private Logger logger = LoggerFactory.getLogger(BookingDataConverterJson.class);

	@Override
	public Optional<List<BookingData>> convert(final String input) throws InvalidInputDataException {
		List<BookingData> bookingData = null;
		try {
			bookingData = new ArrayList<>();
			JsonObject inputObj = gson.fromJson(input, JsonObject.class);
			JsonArray inputArr = inputObj.get("bookings").getAsJsonArray();
			for (int i = 0; i < inputArr.size(); i++) {
				JsonObject bookingObj = inputArr.get(i).getAsJsonObject();
				int hotelId = bookingObj.get(AnalyticsReporterConstants.HOTEL_ID_KEY).getAsInt();
				int bookingId = bookingObj.get(AnalyticsReporterConstants.BOOKING_ID_KEY).getAsInt();
				String customerId = bookingObj.get(AnalyticsReporterConstants.CUSTOMER_ID_KEY).getAsString();
				double sellingPrice = bookingObj.get(AnalyticsReporterConstants.SELLING_PRICE).getAsDouble();
				String currency = bookingObj.get(AnalyticsReporterConstants.CURRENCY).getAsString();
				double exchangeRate = bookingObj.get(AnalyticsReporterConstants.USD_EXCHANGE_RATE).getAsDouble();
				bookingData.add(new BookingData(hotelId, bookingId, customerId, sellingPrice, currency, exchangeRate));
			}
		} catch (JsonSyntaxException e) {
			logger.error(AnalyticsReporterLogs.INVALID_DATA, e);
			throw new InvalidInputDataException(AnalyticsReporterLogs.INVALID_DATA);
		}
		return Optional.ofNullable(bookingData);
	}

}
