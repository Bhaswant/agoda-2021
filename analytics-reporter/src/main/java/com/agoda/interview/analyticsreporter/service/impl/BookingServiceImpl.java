package com.agoda.interview.analyticsreporter.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agoda.interview.analyticsreporter.exception.InvalidInputDataException;
import com.agoda.interview.analyticsreporter.exception.UnsupportedFormatException;
import com.agoda.interview.analyticsreporter.helper.AnalyticsReporterConstants;
import com.agoda.interview.analyticsreporter.helper.AnalyticsReporterLogs;
import com.agoda.interview.analyticsreporter.helper.BookingDataConverterFactory;
import com.agoda.interview.analyticsreporter.helper.FileFormat;
import com.agoda.interview.analyticsreporter.helper.IBookingDataConverter;
import com.agoda.interview.analyticsreporter.model.BookingData;
import com.agoda.interview.analyticsreporter.repository.BookingDataRepository;
import com.agoda.interview.analyticsreporter.service.BookingService;

/**
 * Implementation of {@link BookingService}
 * 
 * @author i0b00j8
 *
 */
@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingDataConverterFactory factory;

	@Autowired
	private BookingDataRepository repository;

	private Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

	@Override
	public void createBooking(final FileFormat format, final String bookingData)
			throws UnsupportedFormatException, IOException, InvalidInputDataException {
		long startTime = System.currentTimeMillis();
		logger.debug("Received a create booking request");
		IBookingDataConverter converter = factory.getConverter(format);

		// Ignoring empty data
		Optional<List<BookingData>> bulkInput = converter.convert(bookingData);
		if (!bulkInput.isPresent()) {
			logger.warn(AnalyticsReporterLogs.EMPTY_RECORDS);
		} else {
			repository.saveAll(bulkInput.get());
		}
		logger.debug(AnalyticsReporterLogs.INSERTION_LOG, (System.currentTimeMillis() - startTime));
	}

	@Override
	public void createBooking(final String format, final String bookingData)
			throws UnsupportedFormatException, IOException, InvalidInputDataException {
		FileFormat fileFormat = null;
		try {
			fileFormat = FileFormat.valueOf(format.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new UnsupportedFormatException(
					String.format(AnalyticsReporterLogs.UNSUPPORTED_FORMAT_EXCEPTION, format));
		}
		createBooking(fileFormat, bookingData);
	}

	@Override
	public void createBooking(final FileFormat format, final InputStream inStream)
			throws IOException, UnsupportedFormatException, InvalidInputDataException {
		String bookingData = IOUtils.toString(inStream, StandardCharsets.UTF_8);
		createBooking(format, bookingData);
	}

	@PostConstruct
	private void importSeedData() {
		try {
			InputStream inStream = this.getClass().getClassLoader()
					.getResourceAsStream(AnalyticsReporterConstants.BOOKING_DATA_FILE);
			this.createBooking(FileFormat.CSV, inStream);
			logger.info(AnalyticsReporterLogs.SEED_LOADING_SUCCESSFUL);
		} catch (UnsupportedFormatException | IOException | InvalidInputDataException e) {
			logger.error(AnalyticsReporterLogs.ERROR_IN_FILE_LOADING, e.getLocalizedMessage());
		}
	}

}
