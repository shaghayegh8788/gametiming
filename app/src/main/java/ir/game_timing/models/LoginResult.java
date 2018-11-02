package ir.game_timing.models;

import com.google.gson.annotations.SerializedName;

public class LoginResult {

    @SerializedName("UserName")
    private String UserName;
    @SerializedName("FullName")
    private String FullName;
    @SerializedName("ProfilePath")
    private String ProfilePath;
    @SerializedName("PhoneNumber")
    private String PhoneNumber;

    public  LoginResult loginResult;

    public LoginResult getLoginResult() {
        return loginResult;
    }

    public void setLoginResult(LoginResult loginResult) {
        this.loginResult = loginResult;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getProfilePath() {
        return ProfilePath;
    }

    public void setProfilePath(String profilePath) {
        ProfilePath = profilePath;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
