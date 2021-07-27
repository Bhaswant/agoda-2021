package com.agoda.interview.analyticsreporter.service.test;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agoda.interview.analyticsreporter.exception.InvalidDataException;
import com.agoda.interview.analyticsreporter.exception.UnsupportedFormatException;
import com.agoda.interview.analyticsreporter.helper.AnalyticsReporterConstants;
import com.agoda.interview.analyticsreporter.helper.BookingDataConverterFactory;
import com.agoda.interview.analyticsreporter.helper.TestDataFile;
import com.agoda.interview.analyticsreporter.helper.FileFormat;
import com.agoda.interview.analyticsreporter.helper.IBookingDataConverter;
import com.agoda.interview.analyticsreporter.model.BookingData;
import com.agoda.interview.analyticsreporter.repository.BookingDataRepository;
import com.agoda.interview.analyticsreporter.service.BookingService;
import com.agoda.interview.analyticsreporter.service.impl.BookingServiceImpl;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

/**
 * Unit tests for {@link BookingService}
 * @author Bhaswant
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class BookingServiceTest {
	
	@Rule
	public TestDataFile validResource;
	
	@Rule
	public TestDataFile invalidResource;
	
	@Mock
	private IBookingDataConverter converter;

	@Mock
	private BookingDataConverterFactory factory;
	
	@Mock
	private BookingDataRepository repository;
	
	@InjectMocks
	private final BookingService bookingService;
	
	private static final Gson gson = new Gson();
	
	private Logger logger = LoggerFactory.getLogger(TestDataFile.class);
	
	public BookingServiceTest() {
		try {
			validResource = new TestDataFile("./BookingTestData.json");
			invalidResource = new TestDataFile("BookingTestDataMalformed.json");
		} catch (IOException e) {
			logger.error("Exception occurred in reading test data, tests will fail", e);
		}
		bookingService = new BookingServiceImpl();
	}
	
	@Test
	public void testCreateBookingString() {
		mockConverter();
		try {
			bookingService.createBooking(FileFormat.JSON, validResource.getTestDataAsString());
			verify(converter).convert(Mockito.anyString());
			verify(repository).saveAll(Mockito.any());
		} catch (UnsupportedFormatException | IOException | InvalidDataException e) {
			fail(String.format("Test failed: {0}", e.getLocalizedMessage()));
		}
	}
	
	@Test
	public void testCreateBookingStream() {
		mockConverter();
		try {
			bookingService.createBooking(FileFormat.JSON, validResource.getTestDataAsStream());
			verify(converter).convert(Mockito.anyString());
			verify(repository).saveAll(Mockito.any());
		} catch (UnsupportedFormatException | IOException | InvalidDataException e) {
			fail(String.format("Test failed: {0}", e.getLocalizedMessage()));
		}
	}

	@Test
	public void testCreateEmptyBooking() {
		try {
			when(factory.getConverter(Mockito.any())).thenReturn(converter);
			when(converter.convert(Mockito.anyString())).thenReturn(Optional.empty());
			bookingService.createBooking(FileFormat.JSON, validResource.getTestDataAsStream());
			verify(converter).convert(Mockito.anyString());
			verify(repository, never()).saveAll(Mockito.any());
		} catch (UnsupportedFormatException | IOException | InvalidDataException e) {
			fail(String.format("Test failed: {0}", e.getLocalizedMessage()));
		}
	}
	private void mockConverter() {
		try {
			when(factory.getConverter(Mockito.any())).thenReturn(converter);
			when(converter.convert(Mockito.anyString())).thenReturn(getValidBookingData(validResource.getTestDataAsString()));
		} catch (UnsupportedFormatException | IOException | InvalidDataException e) {
			fail("Data preparation failed");
		}
	}
	
	private Optional<List<BookingData>> getValidBookingData(final String inData) {
		List<BookingData> bookingData = null;
		try {
			bookingData = new ArrayList<>();
			JsonObject inputObj = gson.fromJson(inData, JsonObject.class);
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
			fail("Data preparation failed");
		}
		return Optional.ofNullable(bookingData);
	}
}
