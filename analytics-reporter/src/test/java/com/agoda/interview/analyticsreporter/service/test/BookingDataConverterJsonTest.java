package com.agoda.interview.analyticsreporter.service.test;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agoda.interview.analyticsreporter.exception.InvalidInputDataException;
import com.agoda.interview.analyticsreporter.exception.UnsupportedFormatException;
import com.agoda.interview.analyticsreporter.helper.AnalyticsReporterConstants;
import com.agoda.interview.analyticsreporter.helper.BookingDataConverterJson;
import com.agoda.interview.analyticsreporter.helper.IBookingDataConverter;
import com.agoda.interview.analyticsreporter.helper.TestDataFile;
import com.agoda.interview.analyticsreporter.model.BookingData;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

/**
 *
 * Test {@link BookingDataConverterJson}
 * @author i0b00j8
 *
 */
public class BookingDataConverterJsonTest {
	
	@Rule
	public TestDataFile validResource;
	
	@Rule
	public TestDataFile invalidResource;

	private static final Gson gson = new Gson();

	private Logger logger = LoggerFactory.getLogger(BookingDataConverterJsonTest.class);
	
	public BookingDataConverterJsonTest() {
		try {
			validResource = new TestDataFile("./BookingTestData.json");
			invalidResource = new TestDataFile("BookingTestDataMalformed.json");
		} catch (IOException e) {
			logger.error("Exception occurred in reading test data, tests will fail", e);
		}
	}
	
	@Test
	public void convert() {
		IBookingDataConverter converter = new BookingDataConverterJson();
		StringBuilder actualOutput = new StringBuilder();
		try {
			actualOutput.append(converter.convert(validResource.getTestDataAsString()).get().toString());
		} catch (UnsupportedFormatException | IOException | InvalidInputDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertEquals(actualOutput.toString(), getValidOutput(validResource.getTestDataAsString()).get().toString());
	}
	
	public Optional<List<BookingData>> getValidOutput(final String input) {
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
			fail("Found invalid json");
		}
		return Optional.ofNullable(bookingData);
	}
}
