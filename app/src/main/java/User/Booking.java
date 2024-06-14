package User;

import com.google.firebase.Timestamp;

public class Booking {
    private String bookingId;
    private String serviceName;
    private String customerName;
    private String servicerName;
    private String status;
    private Timestamp timestamp; // Add timestamp field

    public Booking() {
        // Default constructor required for Firestore serialization
    }

    // Include timestamp in the constructor
    public Booking(String serviceName, String customerName, String status, Timestamp timestamp) {
        this.serviceName = serviceName;
        this.customerName = customerName;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getServicerName() {
        return servicerName;
    }

    public void setServicerName(String servicerName) {
        this.servicerName = servicerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
