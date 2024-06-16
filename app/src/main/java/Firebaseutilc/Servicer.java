package Firebaseutilc;

public class Servicer {
    private String username;
    private String phoneNumber;
    private String address;
    private String workExperience;

    public Servicer() {
        // Default constructor required for Firestore
    }

    public Servicer(String username, String phoneNumber, String address, String workExperience) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.workExperience = workExperience;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
    }
}
