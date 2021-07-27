DROP TABLE IF EXISTS BOOKING_DATA;
CREATE TABLE BOOKING_DATA (
hotelId INTEGER,
bookingId INTEGER,
customerId VARCHAR,
sellingPriceLocalCurrency DOUBLE,
currency VARCHAR,
toUsdExchangeEate DOUBLE,
PRIMARY KEY(bookingId)
)