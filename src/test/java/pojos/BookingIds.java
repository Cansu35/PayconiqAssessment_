package pojos;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingIds {

    private Integer bookingid;
    private Booking booking;

    public BookingIds(Integer bookingid, Booking booking) {
        this.bookingid = bookingid;
        this.booking = booking;
    }
    public BookingIds() {
    }

    public Integer getBookingid() {
        return bookingid;
    }
    public void setBookingid(Integer bookingid) {
        this.bookingid = bookingid;
    }
    public Booking getBooking() {
        return booking;
    }
    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    @Override
    public String toString() {
        return "BookingResponseBodyPojo{" +
                "bookingid=" + bookingid +
                ", booking=" + booking +
                '}';
    }
}
