package com.agoda.interview.analyticsreporter.service.test;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.agoda.interview.analyticsreporter.AnalyticsReporterConfiguration;
import com.agoda.interview.analyticsreporter.exception.InvalidDataException;
import com.agoda.interview.analyticsreporter.helper.TestDataFile;
import com.agoda.interview.analyticsreporter.model.HotelSummary;
import com.agoda.interview.analyticsreporter.service.HotelService;
import com.google.gson.Gson;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AnalyticsReporterConfiguration.class)
public class HotelServiceTest {

	@Autowired
	private HotelService hotelService;

	@Rule
	public TestDataFile withExchange;

	@Rule
	public TestDataFile withoutExchange;

	@Rule
	public TestDataFile emptyData;

	private static final Gson gson = new Gson();

	private Logger logger = LoggerFactory.getLogger(TestDataFile.class);

	public HotelServiceTest() {
		try {
			withExchange = new TestDataFile("./HotelSummaryResult1.json");
			withoutExchange = new TestDataFile("./HotelSummaryResult2.json");
			emptyData = new TestDataFile("./HotelSummaryResult3.json");
		} catch (IOException e) {
			logger.error("Exception occurred in reading test data, tests will fail", e);
		}
	}

	@Test
	public void testHotelWithExchange() {
		HotelSummary summary = null;
		try {
			summary = hotelService.getHotelSummary("1000134", Optional.of(Double.valueOf(3)));
		} catch (InvalidDataException e) {
			fail("Invalid data found");
		}
		Assert.assertEquals(gson.toJson(summary), withExchange.getTestDataAsString());
	}

	@Test
	public void testHotelWithoutExchange() {
		HotelSummary summary = null;
		try {
			summary = hotelService.getHotelSummary("1000134", Optional.empty());
		} catch (InvalidDataException e) {
			fail("Invalid data found");
		}
		Assert.assertEquals(gson.toJson(summary), withoutExchange.getTestDataAsString());
	}

	@Test
	public void testHotelWithEmpty() {
		HotelSummary summary = null;
		try {
			summary = hotelService.getHotelSummary("123", Optional.empty());
		} catch (InvalidDataException e) {
			fail("Invalid data found");
		}
		Assert.assertEquals(gson.toJson(summary), emptyData.getTestDataAsString());
	}
}
