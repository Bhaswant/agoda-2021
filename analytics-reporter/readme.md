Usage Guide:
1. Swagger has been enabled for this project. Please visit http://<hostName>:<port>/swagger-ui.html for the details of the APIs exposed

Assumptions:
1. While the API_3 was asked to be a POST request, since the requirement is to fetch the data and not to create it, converted it to a GET request with payload.
2. In API_2 if hotel asked for is not part of the BookingsData, returning a default object. Since, a hotel can exist and no bookings available for it. As we don't have handle to hotel DB as of now, returning empty object

```
{
"hotelId": "2",
"numberOfBookings": 0,
"totalPriceUsd": 0
}
```
