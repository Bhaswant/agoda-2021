package com.agoda.interview.analyticsreporter.service.test;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.agoda.interview.analyticsreporter.exception.UnsupportedFormatException;
import com.agoda.interview.analyticsreporter.helper.BookingDataConverterCsv;
import com.agoda.interview.analyticsreporter.helper.BookingDataConverterFactory;
import com.agoda.interview.analyticsreporter.helper.BookingDataConverterJson;
import com.agoda.interview.analyticsreporter.helper.FileFormat;
import com.agoda.interview.analyticsreporter.helper.IBookingDataConverter;

/**
 * Tests {@link BookingDataConverterFactory}
 * @author i0b00j8
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class BookingDataConverterFactoryTest {
	
	@Spy
	private IBookingDataConverter bookingDataConverterCsv = new BookingDataConverterCsv();
	
	@Spy
	private IBookingDataConverter bookingDataConverterJson = new BookingDataConverterJson();
	
	@InjectMocks
	private BookingDataConverterFactory factory; 
	
	@Test
	public void testConverterJson() {
		try {
			IBookingDataConverter converter = factory.getConverter(FileFormat.JSON);
			if(!(converter instanceof BookingDataConverterJson)) {
				fail("Invalid instance returned");
			}
		} catch (UnsupportedFormatException e) {
			fail("Invalid format found");
		}
	}
	
	@Test
	public void testConverterCsv() {
		try {
			IBookingDataConverter converter = factory.getConverter(FileFormat.CSV);
			if(!(converter instanceof BookingDataConverterCsv)) {
				fail("Invalid instance returned");
			}
		} catch (UnsupportedFormatException e) {
			fail("Invalid format found");
		}
	}
}
