package com.agoda.interview.analyticsreporter.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Constants class for the application
 * 
 * @author Bhaswant
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AnalyticsReporterConstants {

	public static final String CSV_ATTRIBUTE_ENCLOSED_CHAR = "\"";

	public static final String HOTEL_ID_KEY = "hotel_id";

	public static final String BOOKING_ID_KEY = "booking_id";

	public static final String CUSTOMER_ID_KEY = "customer_id";

	public static final String SELLING_PRICE = "selling_price_local_currency";

	public static final String CURRENCY = "currency";

	public static final String USD_EXCHANGE_RATE = "to_usd_exchange_rate";

	public static final String BOOKING_DATA_FILE = "BookingData.csv";
}
