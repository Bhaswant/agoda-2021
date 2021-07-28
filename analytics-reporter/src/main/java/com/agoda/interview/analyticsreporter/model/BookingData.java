package com.agoda.interview.analyticsreporter.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Booking Data Model. Each object indicates each row in BookingData table
 * @author Bhaswant
 *
 */
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class BookingData {

	@Getter
	@Column
	private int hotelId;
	
	@Getter
	@Id
	private int bookingId;
	
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
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
