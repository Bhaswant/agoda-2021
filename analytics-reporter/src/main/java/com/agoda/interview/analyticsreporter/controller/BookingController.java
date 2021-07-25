package com.agoda.interview.analyticsreporter.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agoda.interview.analyticsreporter.exception.InvalidDataException;
import com.agoda.interview.analyticsreporter.exception.UnsupportedFormatException;
import com.agoda.interview.analyticsreporter.helper.AnalyticsReporterLogs;
import com.agoda.interview.analyticsreporter.service.BookingService;

@RestController
@RequestMapping(value = "/v1/booking")
public class BookingController {

	@Autowired
	private BookingService bookingService;

	@PostMapping
	public ResponseEntity<String> booking(@RequestHeader("format") String format, @RequestBody String bookingData) {

		try {
			bookingService.createBooking(format, bookingData);
		} catch (UnsupportedFormatException | InvalidDataException e) {
			return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		} catch (IOException e) {
			return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(AnalyticsReporterLogs.SUCCESS_MSG, HttpStatus.CREATED);
	}
}
