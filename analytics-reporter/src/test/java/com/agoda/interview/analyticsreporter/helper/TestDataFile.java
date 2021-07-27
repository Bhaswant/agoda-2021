package com.agoda.interview.analyticsreporter.helper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestDataFile extends ExternalResource {

	private final String testData;
	
	private final InputStream inStream;
	
	private Logger logger = LoggerFactory.getLogger(TestDataFile.class);
	
	public TestDataFile(final String resourceFile) throws IOException {
		this.inStream = this.getClass().getClassLoader().getResourceAsStream(resourceFile);
		testData = IOUtils.toString(inStream, StandardCharsets.UTF_8);
	}
	
	public InputStream getTestDataAsStream() {
		return inStream;
	}
	
	public String getTestDataAsString() {
		return testData;
	}

	@Override
	public void after() {
		try {
			inStream.close();
		} catch (IOException e) {
			logger.error("Error closing stream");
		}
		super.after();
	}
}
