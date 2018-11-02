package ir.game_timing.models;

import com.google.gson.annotations.SerializedName;

public class TicketModel {

    @SerializedName("Id")
    private int Id;
    @SerializedName("PersianDate")
    private int PersianDate;

    @SerializedName("Price")
    private int Price;

    @SerializedName("Time")
    private String Time;

    @SerializedName("Capacity")
    private int Capacity;

    @SerializedName("GenderId")
    private int GenderId;

    @SerializedName("Name")
    private String Name;

    @SerializedName("Address")
    private String Address;

    private int StartAge;

    private int EndAge;

    @SerializedName("Age")
    private String GameAge;

    private  String GenderConvert;

    public String getGenderConvert() {
        return GenderConvert;
    }

    public void setGenderConvert(String genderConvert) {
        GenderConvert = genderConvert;
    }

    public int getStartAge() {
        return StartAge;
    }

    public void setStartAge(int startAge) {
        StartAge = startAge;
    }

    public int getEndAge() {
        return EndAge;
    }

    public void setEndAge(int endAge) {
        EndAge = endAge;
    }

    public String getGameAge() {
        return GameAge;
    }

    public void setGameAge(String gameAge) {
        GameAge = gameAge;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getPersianDate() {
        return PersianDate;
    }

    public void setPersianDate(int persianDate) {
        PersianDate = persianDate;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public int getCapacity() {
        return Capacity;
    }

    public void setCapacity(int capacity) {
        Capacity = capacity;
    }

    public int getGenderId() {
        return GenderId;
    }

    public void setGenderId(int genderId) {
        GenderId = genderId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}

