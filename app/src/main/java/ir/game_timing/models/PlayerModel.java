package ir.game_timing.models;

import com.google.gson.annotations.SerializedName;

public class PlayerModel {

    @SerializedName("Id")
    private int Id;

    @SerializedName("TicketId")
    private int TicketId;

    @SerializedName("UserId")
    private int UserId;

    @SerializedName("Age")
    private int Age;

    @SerializedName("FullName")
    private String FullName;

    @SerializedName("GenderId")
    private int GenderId;

    @SerializedName("price")
    private String price;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getTicketId() {
        return TicketId;
    }

    public void setTicketId(int ticketId) {
        TicketId = ticketId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public int getGenderId() {
        return GenderId;
    }

    public void setGenderId(int genderId) {
        GenderId = genderId;
    }
}
