package ir.game_timing;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import ir.game_timing.customViews.MyEditText;
import ir.game_timing.models.MyResponse;
import ir.game_timing.models.UserModel;
import ir.game_timing.recyclerViewChildren.RecyclerRegisterActivity;
import ir.game_timing.retrofit.GameTimingInterface;
import ir.game_timing.retrofit.GameTimingService;
import ir.game_timing.retrofit.SMSInterface;
import ir.game_timing.retrofit.SMSService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    MyEditText register_first_name, register_last_name;
    MyEditText register_mobile, register_code;
    TextView txt_top, txt_bottom, txt_login;
    String FullName, ConfrimCode, name, mobile, firstName, lastName;
    static String random = null;
    private String PhoneNumber;
    LinearLayout confirm;
    Button btn_sendCode;
    int id;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        bind();
    }


    public void bind() {
        Log.d("randon", "bind: " + random);

        confirm = findViewById(R.id.confirm);
        register_last_name = findViewById(R.id.register_last_name);
        register_first_name = findViewById(R.id.register_first_name);
        register_mobile = findViewById(R.id.register_mobile);
        register_code = findViewById(R.id.register_code);
        txt_top = findViewById(R.id.txt_top);
        txt_bottom = findViewById(R.id.txt_bottom);
        txt_login = findViewById(R.id.txt_login);
        findViewById(R.id.btn_register).setOnClickListener(this);
        btn_sendCode = findViewById(R.id.btn_sendCode);
        btn_sendCode.setOnClickListener(this);
        confirm.setVisibility(View.GONE);
        txt_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_register:
                registerUser();
                break;
            case R.id.btn_sendCode:
                sendCode();
                break;
            case R.id.txt_login:
                loginUser();
        }

    }


    void sendCode() {

        register_code.setText("");
        PhoneNumber = register_mobile.text();
        firstName = register_first_name.text();
        lastName = register_last_name.text();
        FullName = firstName + " " + lastName;

        Log.d("visii", "sendCode: " + PhoneNumber + " " + FullName);
        if (!checkMobile()) {
            PublicMethods.showToast("شماره همراه شما درست نیست");
        } else {
            if (FullName.equals("")) {
                PublicMethods.showToast("لطفا نام و نام خانوادگی خود را وارد نمایید");

            } else {
                try {
                    progressDialog = new ProgressDialog(RegisterActivity.this);
                    progressDialog.setMessage("لطفا منتظر بمانید...");
                    progressDialog.setTitle("ارتباط با سرور");
                    progressDialog.show();
                    random();
                    Log.i("randon", "try: " + random);
                    SMSInterface smsInterface = SMSService.getClient().create(SMSInterface.class);
                    Call<Void> call = smsInterface.SendCode("GameTiming", PhoneNumber, random);

                    PublicMethods.showToast("کد فعالسازی برای شما SMS میشود");
                    btn_sendCode.setEnabled(false);
                    delayButton();


                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Log.i("randon", "response: " + response.toString());
                            if (response.code() == 200) {
                                Log.d("visii", "200: ");
                                PublicMethods.showToast(" لطفا کد SMS شده را در قسمت کد فعالسازی ثبت نام وارد نمایید و دکمه فعالسازی ثبت نام را بزنید");
                                confirm.setVisibility(View.VISIBLE);
                                progressDialog.dismiss();

                            } else {
                                PublicMethods.showToast("خطا در برقراری ارتباط با سرور");
                                progressDialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            PublicMethods.showToast("پاسخی از سرور دریافت نشد");
                            progressDialog.dismiss();

                        }
                    });
                } catch (Exception e) {


                }

            }
        }
    }

    private void delayButton() {
        Timer buttonTimer = new Timer();
        buttonTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        btn_sendCode.setEnabled(true);
                    }
                });
            }
        }, 10000);
    }

    void registerUser() {
        PublicMethods.showToast("اطلاعات در حال ارسال است لطفا منتظر باشید");
        FullName = register_first_name.text() + " " + register_last_name.text();
        ConfrimCode = register_code.text();


        Log.d("regii", "registerUser: " + ConfrimCode + " " + random);
        Log.d("regii", "registerUser: " + FullName + " " + PhoneNumber);

        if (ConfrimCode.equals(random)) {

            GameTimingInterface gameTimingInterface = GameTimingService.getClient().create(GameTimingInterface.class);
            Call<MyResponse<UserModel>> call = gameTimingInterface.RegisterUser2(FullName, PhoneNumber);
            call.enqueue(new Callback<MyResponse<UserModel>>() {
                @Override
                public void onResponse(Call<MyResponse<UserModel>> call, Response<MyResponse<UserModel>> response) {

                    if (response.code() == 200) {
                        if (response.body().getResultCode() == 1) {
                            Log.d("messr", "onResponseRegister: "+response.body().getResultCode()+"");
                            name = response.body().getResults().getFullName();
                            id = response.body().getResults().getId();
                            mobile = response.body().getResults().getPhoneNumber();
                            RegisterMessage(register_mobile.text());
                            Log.d("regii", "insert: " + id + " " + name + " " + mobile);
                            PublicMethods.setValue("userID", id + "");
                            PublicMethods.setValue("userFullname", name);
                            PublicMethods.setValue("user_mobile", mobile);
                            PublicMethods.showToast("اطلاعات" + name + "با موفقیت ثبت شد");
                            Intent intent = new Intent(RegisterActivity.this, RecyclerRegisterActivity.class);
                            startActivity(intent);
                        } else if (response.body().getResultCode() == -1) {
                            String message = "شماره شما قبلا ثبت شده است میتوانید از قسمت لاگین وارد اپ شوید";
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            PublicMethods.showToast(message);

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
        } else {
            PublicMethods.showToast("کد فعالسازی شما با کد ارسالی مطابقت ندارد");
        }
    }

     void RegisterMessage(String mobile) {
         Log.d("messr", "RegisterMessagemobile: "+mobile);
        GameTimingInterface gameTimingInterface = GameTimingService.getClient().create(GameTimingInterface.class);
        Call<MyResponse<Void>> call = gameTimingInterface.RegisterMessage(mobile);
        call.enqueue(new Callback<MyResponse<Void>>() {
            @Override
            public void onResponse(Call<MyResponse<Void>> call, Response<MyResponse<Void>> response) {
                if (response.code() == 200) {
                    if (response.body().getResultCode() == 1) {
                        Log.d("messr", "onResponse: ");
                    } else if (response.body().getResultCode() == -1) {
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
            public void onFailure(Call<MyResponse<Void>> call, Throwable t) {
                PublicMethods.showToast(PublicMethods.noConnection);
            }
        });
    }

    void loginUser() {

        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);

    }

    private boolean checkMobile() {
        String mobile = register_mobile.text().trim();
        if (mobile.startsWith("09") && mobile.length() == 11 && !mobile.equals(""))
            return true;
        else
            return false;
    }

    private void random() {
        int range = (9999 - 1000) + 1;
        random = Integer.toString((int) (Math.random() * range) + 1000);
    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
