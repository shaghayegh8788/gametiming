package ir.game_timing.models;

import com.google.gson.annotations.SerializedName;

 public class RegisterUser {



    @SerializedName("FullName")
    private String FullName;
    @SerializedName("PhoneNumber")
    private String PhoneNumber;

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

     public String getUserName() {
         return UserName;
     }

     public void setUserName(String userName) {
         UserName = userName;
     }

     public String getPassword() {
         return Password;
     }

     public void setPassword(String password) {
         Password = password;
     }

     @SerializedName("UserName")
    private String UserName;
    @SerializedName("Password")
    private String Password;

    public RegisterUser(String fullName, String phoneNumber, String userName, String password) {
        this.FullName = fullName;
       this.PhoneNumber = phoneNumber;
        this.UserName = userName;
        this.Password = password;
    }







}
