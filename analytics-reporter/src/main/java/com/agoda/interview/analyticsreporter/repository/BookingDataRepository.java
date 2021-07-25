package com.agoda.interview.analyticsreporter.repository;

import org.springframework.data.repository.CrudRepository;

import com.agoda.interview.analyticsreporter.model.BookingData;

/**
 * Repository interface working on Booking data
 * @author i0b00j8
 *
 */
public interface BookingDataRepository extends CrudRepository<BookingData, String> {

}
