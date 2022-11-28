package test;

import base_urls.HerOkuAppBaseUrl;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.testng.annotations.Test;
import pojos.Booking;
import pojos.BookingIds;
import pojos.Bookingdates;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static utils.Authentication.generateToken;

public class BookingTest extends HerOkuAppBaseUrl {

    Bookingdates bookingdates;
    Booking booking;
    Response response;
    BookingIds actualPojo;

    @Test(priority = 1)

    public void testCreateBooking() {

        herOku.pathParam("first", "booking");

        bookingdates = new Bookingdates("2021-06-01", "2021-07-01");
        booking = new Booking("Bob", "Marley", 250, true, bookingdates, "Breakfast with white tea");

        response = given().spec(herOku).contentType(ContentType.JSON).body(booking).when().post("/{first}");

        actualPojo = response.as(BookingIds.class);

        assertEquals(200, response.getStatusCode());
        assertEquals(booking.getFirstname(), actualPojo.getBooking().getFirstname());
        assertEquals(booking.getLastname(), actualPojo.getBooking().getLastname());
        assertEquals(booking.getTotalprice(), actualPojo.getBooking().getTotalprice());
        assertEquals(booking.getDepositpaid(), actualPojo.getBooking().getDepositpaid());
        assertEquals(booking.getBookingdates().getCheckin(), actualPojo.getBooking().getBookingdates().getCheckin());
        assertEquals(booking.getBookingdates().getCheckout(), actualPojo.getBooking().getBookingdates().getCheckout());
        assertEquals(booking.getAdditionalneeds(), actualPojo.getBooking().getAdditionalneeds());


    }

    @Test(priority = 2)
    public void testGetBookingId() {

        herOku.pathParams("first", "booking", "second", actualPojo.getBookingid());
        bookingdates = new Bookingdates("2021-06-01", "2021-07-01");
        booking = new Booking("Bob", "Marley", 250, true, bookingdates, "Breakfast with white tea");

        response = given().spec(herOku).when().get("/{first}/{second}");
        Booking actualPojo = response.as(Booking.class);

        assertEquals(200, response.getStatusCode());
        assertEquals(booking.getFirstname(), actualPojo.getFirstname());
        assertEquals(booking.getLastname(), actualPojo.getLastname());
        assertEquals(booking.getTotalprice(), actualPojo.getTotalprice());
        assertEquals(booking.getDepositpaid(), actualPojo.getDepositpaid());
        assertEquals(bookingdates.getCheckin(), actualPojo.getBookingdates().getCheckin());
        assertEquals(bookingdates.getCheckout(), actualPojo.getBookingdates().getCheckout());
        assertEquals(booking.getAdditionalneeds(), actualPojo.getAdditionalneeds());



   }

    @Test(priority = 3)
    public void testPartialUpdateBooking() {
        herOku.pathParams("first", "booking", "second", actualPojo.getBookingid());
        Map<String, Object> patchRequestMap = new HashMap<>();
        patchRequestMap.put("firstname", "James");
        patchRequestMap.put("lastname", "Bond");

        response = given().spec(herOku).contentType(ContentType.JSON)
                   .header("Cookie", "token= " + generateToken())
                   .body(patchRequestMap)
                   .when().patch("/{first}/{second}");

        response.then()
                .assertThat().statusCode(200)
                .contentType(ContentType.JSON)
                .body("firstname", is("James"), "lastname", is("Bond"),
                        "totalprice", is(250),
                        "depositpaid", is(true),
                        "bookingdates.checkin", is("2021-06-01"),
                        "bookingdates.checkout", is("2021-07-01"),
                        "additionalneeds", is("Breakfast with white tea"));

    }

    @Test(priority = 4)
    public void testGetBookingIdsWithParameters() {
        herOku.pathParam("first", "booking")
                .queryParams("firstname","James", "lastname","Bond");
         Response response = given().spec(herOku).when().get("/{first}");
                             response.then().assertThat().statusCode(200);
                             assertTrue(response.asString().contains("bookingid"));

    }

    @Test(priority = 5)
    public void testDeleteBooking() {
        herOku.pathParams("first", "booking", "second", actualPojo.getBookingid());
        Response response = given().spec(herOku).contentType(ContentType.JSON)
                            .header("Cookie", "token= " + generateToken())
                            .when().delete("/{first}/{second}");
                             response.then().assertThat().statusCode(201);
    }

    @Test(priority = 6)
    public void testGetBookingIdsWithoutParameters() {
        herOku.pathParam("first", "booking");
        Response response = given().spec(herOku)
                           .when().get("/{first}");
                            response.then().assertThat().statusCode(200);
        JsonPath json = response.jsonPath();
        List<Integer> bookingList = json.getList("bookingid");
        int deletedId = actualPojo.getBookingid();
        Assert.assertFalse(bookingList.contains(deletedId));





    }
      }