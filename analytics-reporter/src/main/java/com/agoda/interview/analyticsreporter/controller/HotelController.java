package com.agoda.interview.analyticsreporter.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agoda.interview.analyticsreporter.exception.InvalidInputDataException;
import com.agoda.interview.analyticsreporter.model.HotelSummary;
import com.agoda.interview.analyticsreporter.service.HotelService;

/**
 * 
 * Rest controller for Hotel related APIs. Currently supporting:
 * 1. Get hotel summary for a given customer.
 * 
 * @author Bhaswant
 *
 */
@RestController
@RequestMapping(value = "/v1/hotel")
public class HotelController {

	@Autowired
	private HotelService hotelService;

	@GetMapping("/summary")
	public ResponseEntity<HotelSummary> getSummary(@RequestParam String hotelId,
			@RequestParam(required = false) Double exchangeRate) {
		HotelSummary summary;
		try {
			summary = hotelService.getHotelSummary(hotelId, Optional.ofNullable(exchangeRate));
		} catch (InvalidInputDataException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(summary, HttpStatus.OK);
	}
}
