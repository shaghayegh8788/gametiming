package ir.game_timing.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ir.game_timing.recyclerViewDay.DayModel;

public class getDay {

    @SerializedName("ListResponse")
    private List<MyResponse<DayModel>> ListResponse;

    public List<MyResponse<DayModel>> getListResponse() {
        return ListResponse;
    }

    public void setListResponse(List<MyResponse<DayModel>> listResponse) {
        ListResponse = listResponse;
    }
}
