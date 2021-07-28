package com.agoda.interview.analyticsreporter.service.test;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

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
import com.agoda.interview.analyticsreporter.exception.ThreadExecutionException;
import com.agoda.interview.analyticsreporter.helper.TestDataFile;
import com.agoda.interview.analyticsreporter.model.CustomerSummary;
import com.agoda.interview.analyticsreporter.service.CustomerService;
import com.google.gson.Gson;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AnalyticsReporterConfiguration.class)
public class CustomerServiceTest {

	@Autowired
	private CustomerService customerService;

	@Rule
	public TestDataFile positiveCases;

	@Rule
	public TestDataFile positiveCaseResults;

	@Rule
	public TestDataFile negativeCases;

	@Rule
	public TestDataFile negativeCaseResults;

	@Rule
	public TestDataFile invalidFormatCase;

	private static final Gson gson = new Gson();

	private Logger logger = LoggerFactory.getLogger(TestDataFile.class);

	public CustomerServiceTest() {
		try {
			positiveCases = new TestDataFile("./CustomerTestData1.json");
			positiveCaseResults = new TestDataFile("CustomerSummaryResult1.json");
			negativeCases = new TestDataFile("./CustomerTestData2.json");
			negativeCaseResults = new TestDataFile("CustomerSummaryResult2.json");
			invalidFormatCase = new TestDataFile("CustomerSummaryResult3.json");
		} catch (IOException e) {
			logger.error("Exception occurred in reading test data, tests will fail", e);
		}
	}

	@Test
	public void testGetSummaryPositiveCase() {
		testGetSummary(positiveCases, positiveCaseResults, 2);
	}

	@Test
	public void testGetSummaryNegativeCase() {
		testGetSummary(negativeCases, negativeCaseResults, 3);
	}

	@Test
	public void testGetSummaryInvalidFormatCase() {
		try {
			customerService.getCustomerSummary(invalidFormatCase.getTestDataAsString());
		} catch (InvalidDataException e) {
			return;
		} catch (ThreadExecutionException e) {
			fail(String.format("Test failed: {0}", e.getLocalizedMessage()));
		}
		fail("Expected Invalid Data exception");
	}

	public void testGetSummary(final TestDataFile testCase, final TestDataFile results, final int resultCount) {
		List<CustomerSummary> customerSummary = Collections.emptyList();
		try {
			customerSummary = customerService.getCustomerSummary(testCase.getTestDataAsString());
		} catch (ThreadExecutionException | InvalidDataException e) {
			fail(String.format("Test failed: {0}", e.getLocalizedMessage()));
		}
		Assert.assertEquals(resultCount, customerSummary.size());
		Assert.assertEquals(gson.toJson(customerSummary), results.getTestDataAsString());
	}
}
