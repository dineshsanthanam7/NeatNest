package User;

import com.google.firebase.Timestamp;

public class BookingRoom {

    private String clientUserId;
    private String clientUserName;
    private String servicerUserId;
    private String servicerUserName;
    private String serviceType;
    private String selectedDate;
    private String selectedTime;
    private String status;
    private Timestamp timestamp;

    public BookingRoom() {
        // Required empty public constructor
    }

    public BookingRoom(String clientUserId, String clientUserName, String servicerUserId, String servicerUserName,
                       String serviceType, String selectedDate, String selectedTime, String status) {
        this.clientUserId = clientUserId;
        this.clientUserName = clientUserName;
        this.servicerUserId = servicerUserId;
        this.servicerUserName = servicerUserName;
        this.serviceType = serviceType;
        this.selectedDate = selectedDate;
        this.selectedTime = selectedTime;
        this.status = status;
        this.timestamp = timestamp.now(); // This will be set when the object is saved to Firestore
    }

    // Getters and setters

    public String getClientUserId() {
        return clientUserId;
    }

    public void setClientUserId(String clientUserId) {
        this.clientUserId = clientUserId;
    }

    public String getClientUserName() {
        return clientUserName;
    }

    public void setClientUserName(String clientUserName) {
        this.clientUserName = clientUserName;
    }

    public String getServicerUserId() {
        return servicerUserId;
    }

    public void setServicerUserId(String servicerUserId) {
        this.servicerUserId = servicerUserId;
    }

    public String getServicerUserName() {
        return servicerUserName;
    }

    public void setServicerUserName(String servicerUserName) {
        this.servicerUserName = servicerUserName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }

    public String getSelectedTime() {
        return selectedTime;
    }

    public void setSelectedTime(String selectedTime) {
        this.selectedTime = selectedTime;
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
