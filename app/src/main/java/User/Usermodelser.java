package User;

import com.google.firebase.Timestamp;

public class Usermodelser {
    private String username;
    private String phonenumber;
    private Timestamp createdTimestamp;
    private String address;
    private String worhexperience;
    private String userID;

    public Usermodelser() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWorhexperience() {
        return worhexperience;
    }

    public void setWorhexperience(String worhexperience) {
        this.worhexperience = worhexperience;
    }

    public Usermodelser(String username, String phonenumber, Timestamp createdTimestamp, String userID,String address,String workexperience) {
        this.username = username;
        this.phonenumber = phonenumber;
        this.createdTimestamp = createdTimestamp;
        this.userID=userID;
        this.address=address;
        this.worhexperience=workexperience;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }



}
