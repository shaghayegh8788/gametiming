package ir.game_timing.retrofit;

import java.util.List;

import ir.game_timing.models.BaseModel;
import ir.game_timing.models.ChildModel;
import ir.game_timing.models.MessageModel;
import ir.game_timing.models.MyResponse;
import ir.game_timing.models.Percent;
import ir.game_timing.models.PlayerModel;
import ir.game_timing.models.TicketModel;
import ir.game_timing.models.UserModel;
import ir.game_timing.recyclerViewChildren.ChildrenModel;
import ir.game_timing.recyclerViewDay.DayModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface GameTimingInterface {

    @FormUrlEncoded
    @POST("user/login")
    Call<MyResponse<UserModel>> LoginUser(@Field("PhoneNumber") String PhoneNumber);


    @FormUrlEncoded
    @POST("User/RegisterUser")
    Call<MyResponse<UserModel>> RegisterUser2(@Field("fullName") String FullName, @Field("phoneNumber") String PhoneNumber);


    @Multipart
    @POST("User/RegisterChild")
    Call<MyResponse<ChildrenModel>> RegisterChild(
            @Part MultipartBody.Part body,
            @Part("childFullName") RequestBody fullName,
            @Part("nationalCode") RequestBody nationalCode,
            @Part("Gender") RequestBody gender,
            @Part("phoneNumber") RequestBody phoneNumber,
            @Part("birthDate") RequestBody birthDate,
            @Part("Relation") RequestBody relation
    );


    @Multipart
    @POST("User/UpdateChild")
    Call<MyResponse<ChildModel>> UpdateChild(
            @Part MultipartBody.Part body,
            @Part("childId")RequestBody IdBody,
            @Part("childFullName") RequestBody fullName,
            @Part("nationalCode") RequestBody nationalCode,
            @Part("Gender") RequestBody gender,
            @Part("phoneNumber") RequestBody phoneNumber,
            @Part("birthDate") RequestBody birthDate,
            @Part("Relation") RequestBody relation
    );

    @GET("Ticket/getAllTicketDays")
    Call<MyResponse<List<DayModel>>> getEntertainments();


    @POST("ticket/getAllSanses")
    @FormUrlEncoded
    Call<MyResponse<List<TicketModel>>> getTickets(@Field("EnterID") String EnterID, @Field("TicketDate") String TicketDate);


    @POST("player/RegisterPlayer")
    @FormUrlEncoded
    Call<MyResponse<ChildrenModel>> registerPlayer(@Field("fullName") String fullName, @Field("TicketId") int TicketId, @Field("age") int Age,
                                                   @Field("Gender") int gender, @Field("phoneNumber") String user_Mobile);

    @POST("player/GetAllPlayers")
    @FormUrlEncoded
    Call<MyResponse<List<PlayerModel>>> GetAllPlayers(@Field("TicketId") int ticketId, @Field("PhoneNumber") String phoneNumber);

    @POST("player/UpdatePlayer")
    @FormUrlEncoded
    Call<MyResponse<PlayerModel>> UpdatePlayer(@Field("PhoneNumber") String phoneNumber,
                                               @Field("childFullName") String fullName,
                                               @Field("Age") int age,
                                               @Field("Gender") int gender,
                                               @Field("childId") int childId,
                                               @Field("TicketId") int ticketId,
                                               @Field("childId") int userId);

    @POST("Percentage/GetPercentage")
    @FormUrlEncoded
    Call<MyResponse<Percent>> GetPercent(@Field("Size") int size);

    @POST("discount/GetDiscount")
    @FormUrlEncoded
    Call<MyResponse<Percent>> GetDiscount(@Field("PhoneNumber") String phoneNumber,@Field("Number") String number);

    @POST("user/showInfo")
    @FormUrlEncoded
    Call<MyResponse<UserModel>> GetInfo (@Field("PhoneNumber") String phoneNumber);

    @POST("user/EditInfo")
    @FormUrlEncoded
    Call<MyResponse<UserModel>> UpdateUser(@Field("PhoneNumber") String phoneNumber,@Field("Number") String number,@Field("FullName") String fullName);

    @POST("Message/GetMessages")
    @FormUrlEncoded
    Call<MyResponse<List<MessageModel>>> getMessage2(@Field("PhoneNumber") String phoneNumber);


    @POST("Message/SetMessage")
    @FormUrlEncoded
    Call<MyResponse<MessageModel>> SetMessage(@Field("ID") String id);

    @POST("Message/GetAllMessage")
    @FormUrlEncoded
    Call<MyResponse<List<MessageModel>>> getAllMessage(@Field("PhoneNumber") String phoneNumber);

    @POST("User/GetAllChildren")
    @FormUrlEncoded
    Call<MyResponse<List<ChildModel>>>  GetAllChildren(@Field("PhoneNumber") String PhoneNumber);


    @GET("User/GetAllSchools")
    Call<MyResponse<List<BaseModel>>> GetListSchool();


    @GET("User/GetAllPictures")
    Call<MyResponse<List<BaseModel>>> GetAllPictures();


    @GET("User/GetAllVideos")
    Call<MyResponse<List<BaseModel>>> GetAllVideos();

    @POST("Message/RegisterMessage")
    @FormUrlEncoded
    Call<MyResponse<Void>> RegisterMessage(@Field("phoneNumber") String mobile);

    @POST("User/GetChildId")
    @FormUrlEncoded
    Call<MyResponse<ChildModel>> GetChild(@Field("ChildId") int child_id);
}
