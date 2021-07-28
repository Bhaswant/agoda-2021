package com.agoda.interview.analyticsreporter.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agoda.interview.analyticsreporter.exception.InvalidInputDataException;
import com.agoda.interview.analyticsreporter.exception.ThreadExecutionException;
import com.agoda.interview.analyticsreporter.model.CustomerSummary;
import com.agoda.interview.analyticsreporter.service.CustomerService;

/**
 * 
 * Rest controller for Customer related APIs. Currently supporting:
 * 1. Get booking summary for a given customer(s)
 * 
 * @author Bhaswant
 *
 */
@RestController
@RequestMapping(value = "/v1/customer")
public class CustomerController {
	@Autowired
	private CustomerService customerService;

	@GetMapping("/summary")
	public ResponseEntity<List<CustomerSummary>> getSummary(@RequestBody String payload) {
		List<CustomerSummary> summary = Collections.emptyList();
		try {
			summary = customerService.getCustomerSummary(payload);
		} catch (ThreadExecutionException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (InvalidInputDataException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(summary, HttpStatus.OK);
	}
}
