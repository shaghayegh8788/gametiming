package ir.game_timing.models;

import com.google.gson.annotations.SerializedName;

public class UserModel {

    int Id;
    @SerializedName("FullName")
    String FullName;
    @SerializedName("PhoneNumber")
    String PhoneNumber;


    String lastName;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    String firstName;

    public UserModel() {
    }

    public UserModel(int id, String fullName, String phoneNumber) {
        Id = id;
        FullName = fullName;
        PhoneNumber = phoneNumber;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
