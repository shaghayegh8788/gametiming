package ir.game_timing.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SMSInterface {

    @GET("466C4F6A354E36694C74666C397353576B59614957773D3D/verify/lookup.json")
    Call<Void> SendCode(@Query("template") String template, @Query("receptor") String PhoneNumber, @Query("token") String token);

}
