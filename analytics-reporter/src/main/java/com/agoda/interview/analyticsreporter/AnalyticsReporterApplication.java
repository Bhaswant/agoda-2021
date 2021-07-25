package com.agoda.interview.analyticsreporter;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.agoda.interview.analyticsreporter.exception.InvalidDataException;
import com.agoda.interview.analyticsreporter.exception.UnsupportedFormatException;
import com.agoda.interview.analyticsreporter.helper.AnalyticsReporterLogs;
import com.agoda.interview.analyticsreporter.helper.FileFormat;
import com.agoda.interview.analyticsreporter.service.BookingService;

@SpringBootApplication
public class AnalyticsReporterApplication {

	private static Logger logger = LoggerFactory.getLogger(AnalyticsReporterApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(AnalyticsReporterApplication.class, args);
		importSeedData(context);
	}

	private static void importSeedData(ConfigurableApplicationContext context) {
		BookingService service = context.getBean(BookingService.class);
		try {
			InputStream inStream = service.getClass().getClassLoader().getResourceAsStream("./BookingData.csv");
			service.createBooking(FileFormat.CSV, inStream);
			logger.info(AnalyticsReporterLogs.SEED_LOADING_SUCCESSFUL);
		} catch (UnsupportedFormatException | IOException | InvalidDataException e) {
			logger.error(AnalyticsReporterLogs.ERROR_IN_FILE_LOADING, e.getLocalizedMessage());
		}
	}
}
