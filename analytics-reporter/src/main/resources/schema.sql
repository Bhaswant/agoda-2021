DROP TABLE IF EXISTS BOOKING_DATA;
CREATE TABLE BOOKING_DATA (
hotelId VARCHAR,
bookingId VARCHAR,
customerId VARCHAR,
sellingPriceLocalCurrency DOUBLE,
currency VARCHAR,
toUsdExchangeEate DOUBLE,
PRIMARY KEY(bookingId)
)