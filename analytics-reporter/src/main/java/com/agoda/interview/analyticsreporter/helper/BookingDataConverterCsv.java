package com.agoda.interview.analyticsreporter.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.agoda.interview.analyticsreporter.exception.InvalidInputDataException;
import com.agoda.interview.analyticsreporter.model.BookingData;

/**
 * Implementation of {@link IBookingDataConverter} for CSV implementations
 * 
 * @author Bhaswant
 *
 */
@Component
public class BookingDataConverterCsv implements IBookingDataConverter {

	private Logger logger = LoggerFactory.getLogger(BookingDataConverterCsv.class);

	/**
	 * Expecting first line as headers. As part of pro-active validation, Ignoring
	 * complete data even if one column value is incorrect.
	 * 
	 * @throws IOException
	 * @throws InvalidInputDataException
	 */
	@Override
	public Optional<List<BookingData>> convert(final String input) throws IOException, InvalidInputDataException {
		List<BookingData> bookingData = null;

		CSVFormat format = CSVFormat.newFormat(',').withHeader();
		try (CSVParser parse = CSVParser.parse(input, format)) {
			bookingData = new ArrayList<>();
			for (CSVRecord record : parse.getRecords()) {
				try {
					int hotelId = Integer
							.parseInt(cleanString(record.get(addEnclosure(AnalyticsReporterConstants.HOTEL_ID_KEY))));
					int bookingId = Integer
							.parseInt(cleanString(record.get(addEnclosure(AnalyticsReporterConstants.BOOKING_ID_KEY))));
					String customerId = cleanString(
							record.get(addEnclosure(AnalyticsReporterConstants.CUSTOMER_ID_KEY)));
					double sellingPrice = cleanDouble(
							record.get(addEnclosure(AnalyticsReporterConstants.SELLING_PRICE)));
					String currency = cleanString(record.get(addEnclosure(AnalyticsReporterConstants.CURRENCY)));
					double exchangeRate = cleanDouble(
							record.get(addEnclosure(AnalyticsReporterConstants.USD_EXCHANGE_RATE)));
					bookingData
							.add(new BookingData(hotelId, bookingId, customerId, sellingPrice, currency, exchangeRate));
				} catch (NumberFormatException e) {
					logger.error(AnalyticsReporterLogs.INVALID_DATA, e);
					throw new InvalidInputDataException(AnalyticsReporterLogs.INVALID_DATA);
				}
			}
		}
		return Optional.ofNullable(bookingData);
	}

	/**
	 * For any larger verification it makes sense to have a factory and individual
	 * classes for each data type, following SOLID principles. However, given the
	 * existing use case that is not required. Hence a util method.
	 * 
	 * @return
	 */
	private double cleanDouble(String doubleVal) {
		doubleVal = stripEnclosure(doubleVal);
		return Double.valueOf(doubleVal);
	}

	/**
	 * 
	 * @param doubleVal
	 * @return
	 */
	private String cleanString(String doubleVal) {
		doubleVal = stripEnclosure(doubleVal);
		return String.valueOf(doubleVal);
	}

	/**
	 * Removes the double quotes, if there are any
	 * 
	 * @param val
	 * @return
	 */
	private String stripEnclosure(String val) {
		if (val.startsWith(AnalyticsReporterConstants.CSV_ATTRIBUTE_ENCLOSED_CHAR)) {
			val = val.substring(1);
		}
		if (val.endsWith(AnalyticsReporterConstants.CSV_ATTRIBUTE_ENCLOSED_CHAR)) {
			val = val.substring(0, val.length() - 1);
		}
		return val;
	}

	/**
	 * Adds double quotes when required.
	 * 
	 * @param key
	 * @return
	 */
	private String addEnclosure(String key) {
		StringBuilder keyBuffer = new StringBuilder();
		keyBuffer.append(AnalyticsReporterConstants.CSV_ATTRIBUTE_ENCLOSED_CHAR).append(key)
				.append(AnalyticsReporterConstants.CSV_ATTRIBUTE_ENCLOSED_CHAR);
		return keyBuffer.toString();
	}
}
