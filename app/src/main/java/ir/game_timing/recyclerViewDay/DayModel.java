package ir.game_timing.recyclerViewDay;

import com.google.gson.annotations.SerializedName;


public class DayModel {

    @SerializedName("Id")
    private String EnterId;
    private String day;
    @SerializedName("DayOfWeek")
    private String DayOfWeek;
    @SerializedName("PersianDate")
    private String PersianDate;
    @SerializedName("Name")
    private String placeName;
    @SerializedName("Address")
    private String placeAddress;

    public String getEnterId() {
        return EnterId;
    }

    public void setEnterId(String enterId) {
        EnterId = enterId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDayOfWeek() {
        return DayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        DayOfWeek = dayOfWeek;
    }

    public String getPersianDate() {
        return PersianDate;
    }

    public void setPersianDate(String persianDate) {
        PersianDate = persianDate;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
    }

}
