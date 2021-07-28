package com.agoda.interview.analyticsreporter.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.agoda.interview.analyticsreporter.model.BookingData;

/**
 * Repository interface working on Booking data
 * @author Bhaswant
 *
 */
public interface BookingDataRepository extends CrudRepository<BookingData, String> {
	
	/**
	 * 
	 * @param hotelId
	 * @return
	 */
	public Optional<List<BookingData>> findAllByHotelId(int hotelId);
	
	/**
	 * Fetches customer details by Id
	 * @param customerId
	 * @return
	 */
	public Optional<List<BookingData>> findAllByCustomerId(String customerId);
}
