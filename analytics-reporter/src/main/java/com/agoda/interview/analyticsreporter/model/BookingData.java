package com.agoda.interview.analyticsreporter.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Booking Data Model. Each object indicates each row in BookingData table
 * @author i0b00j8
 *
 */
@Entity
@Table
@AllArgsConstructor
public class BookingData {

	@Getter
	@Column
	private String hotelId;
	
	@Getter
	@Id
	private String bookingId;
	
	@Getter
	@Column
	private String customerId;
	
	@Getter
	@Column
	private double sellingPriceLocalCurrency;
	
	@Getter
	@Column
	private String currency;
	
	// Setter is required to update the exchange rates.
	@Getter
	@Setter
	@Column
	private double toUsdExchangeEate;
	
	public BookingData() {		
	}
}
