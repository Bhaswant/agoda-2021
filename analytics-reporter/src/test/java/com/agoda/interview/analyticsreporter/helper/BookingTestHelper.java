package com.agoda.interview.analyticsreporter.helper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

/**
 * 
 * @author Bhaswant
 *
 */
public class BookingTestHelper {

	public static InputStream fetchTestDataAsStream() {
		return BookingTestHelper.class.getClassLoader().getResourceAsStream("./BookingData.csv");
	}
	
	public static String fetchTestData() throws IOException {
		InputStream inStream = fetchTestDataAsStream();
		return IOUtils.toString(inStream, StandardCharsets.UTF_8);
	}
}
