package ir.game_timing.models;

import com.google.gson.annotations.SerializedName;

public class LoginData {
@SerializedName("UserName")
    private String UserName;
    @SerializedName("Password")
    private String Password;

    public LoginData(String userName, String password) {
        UserName = userName;
        Password = password;
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
}
