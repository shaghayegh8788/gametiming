package ir.game_timing.models;

import com.google.gson.annotations.SerializedName;

public class Percent {
    @SerializedName("Discount")
    private  int Discount;

    @SerializedName("code")
    private  String Code;
    @SerializedName("percent")
    private int percent;


    public int getDiscount() {
        return Discount;
    }

    public void setDiscount(int discount) {
        Discount = discount;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }
}
