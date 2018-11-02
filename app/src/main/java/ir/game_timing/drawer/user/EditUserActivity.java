package ir.game_timing.drawer.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import ir.game_timing.MainActivity;
import ir.game_timing.PublicMethods;
import ir.game_timing.R;
import ir.game_timing.customViews.MyEditText;
import ir.game_timing.models.MyResponse;
import ir.game_timing.models.UserModel;
import ir.game_timing.retrofit.GameTimingInterface;
import ir.game_timing.retrofit.GameTimingService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class EditUserActivity extends AppCompatActivity {
    MyEditText register_first_name, register_last_name, register_mobile;
    Button btn_edit, btn_back;
    String TAG = "edittt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        bind();
        getInfo();
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditUserActivity.this, MainActivity.class));
            }
        });
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PublicMethods.showToast("لطفا منتظر بمانید ...");
                final String PhoneNumber = PublicMethods.getValue("user_mobile", "09121234567");/// TODO: 9/20/2018 change number
                final String Number = register_mobile.text();
                String firstName = register_first_name.text();
                String lastName = register_last_name.text();
                final String FullName = firstName + " " + lastName;

                GameTimingInterface gameTimingInterface = GameTimingService.getClient().create(GameTimingInterface.class);
                Call<MyResponse<UserModel>> call = gameTimingInterface.UpdateUser(PhoneNumber, Number, FullName);
                call.enqueue(new Callback<MyResponse<UserModel>>() {
                    @Override
                    public void onResponse(Call<MyResponse<UserModel>> call, Response<MyResponse<UserModel>> response) {
                        Log.d(TAG, "onResponse: " + response.body().getResultCode());
                        if (response.code() == 200) {
                            if (response.body().getResultCode() == 1) {
                                UserModel user = new UserModel();
                                user.setFullName(FullName);
                                user.setPhoneNumber(PhoneNumber);
                                PublicMethods.setValue("user_mobile", Number);
                                Log.d("getinfooo", "UpdateUser: " + FullName + " " + PhoneNumber);
                                PublicMethods.showToast("تغییرات با موفقیت ثبت شد.");
                                startActivity(new Intent(EditUserActivity.this, MainActivity.class));
                            }
                            if (response.body().getResultCode() == -1) {
                                PublicMethods.showToast(response.body().getMessage());
                                Log.d("getinfooo", "UpdateUser: " +response.body().getMessage());
                            }
                        } else {
                            try {
                                PublicMethods.showToast(response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MyResponse<UserModel>> call, Throwable t) {
                        PublicMethods.showToast(PublicMethods.noConnection);
                    }
                });

            }
        });
    }

    private void getInfo() {
        String PhoneNumber = PublicMethods.getValue("user_mobile", "09121234567");/// TODO: 9/20/2018 change number
        Log.d(TAG, "getInfo: +" + PhoneNumber);
        GameTimingInterface gameTimingInterface = GameTimingService.getClient().create(GameTimingInterface.class);
        Call<MyResponse<UserModel>> call = gameTimingInterface.GetInfo(PhoneNumber);
        call.enqueue(new Callback<MyResponse<UserModel>>() {
            @Override
            public void onResponse(Call<MyResponse<UserModel>> call, Response<MyResponse<UserModel>> response) {
                Log.d(TAG, "onResponse: " + response.body().getMessage());
                if (response.code() == 200) {
                    if (response.body().getResultCode() == 1) {
                        UserModel user = new UserModel();
                        String Fullname = (response.body().getResults().getFullName());
                        String[] name = Fullname.split(" ");
                        user.setFirstName(name[0]);
                        user.setLastName(name[1]);
                        user.setPhoneNumber(response.body().getResults().getPhoneNumber());
                        Log.d("getinfooo", "onResponse: " + user.getPhoneNumber() + " " + user.getFullName());

                        register_first_name.setText(user.getFirstName());
                        register_last_name.setText(user.getLastName());
                        register_mobile.setText(user.getPhoneNumber());
                    }
                    if (response.body().getResultCode() == -1) {
                        PublicMethods.showToast(response.body().getMessage());
                    }

                } else {
                    try {
                        PublicMethods.showToast(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<MyResponse<UserModel>> call, Throwable t) {
                PublicMethods.showToast("خطا در برقراری ارتباط با سرور");
            }
        });


    }

    private void bind() {
        register_first_name = findViewById(R.id.register_first_name);
        register_last_name = findViewById(R.id.register_last_name);
        register_mobile = findViewById(R.id.register_mobile);
        btn_edit = findViewById(R.id.btn_edit);
        btn_back = findViewById(R.id.btn_back);
    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
