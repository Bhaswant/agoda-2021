package com.agoda.interview.analyticsreporter.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.agoda.interview.analyticsreporter.model.BookingData;

/**
 * Repository interface working on Booking data
 * @author i0b00j8
 *
 */
public interface BookingDataRepository extends CrudRepository<BookingData, String> {
	
	public Optional<List<BookingData>> findAllByHotelId(String hotelId);
}
