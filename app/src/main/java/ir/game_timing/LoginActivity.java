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

import com.orhanobut.hawk.Hawk;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import ir.game_timing.customViews.MyEditText;
import ir.game_timing.models.MyResponse;
import ir.game_timing.models.UserModel;
import ir.game_timing.retrofit.GameTimingInterface;
import ir.game_timing.retrofit.GameTimingService;
import ir.game_timing.retrofit.SMSInterface;
import ir.game_timing.retrofit.SMSService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    MyEditText login_mobile, login_code;
    String PhoneNumber = "";
    Button btn_login, btn_sendCode, btn_again;
    TextView txt_sign_up;
    static String random = null;
    LinearLayout visible;
    ProgressDialog progressDialog;
    String fullName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bind();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setTitle("ارتباط با سرور");
                progressDialog.setMessage("لطفا منتظر بمانید");
                progressDialog.show();
                loginUser();
            }
        });
        btn_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        btn_sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkCode();
            }
        });
        txt_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goRegister();
            }
        });

    }

    public void bind() {
        login_mobile = findViewById(R.id.login_mobile);
        txt_sign_up = findViewById(R.id.txt_sign_up);
        btn_login = findViewById(R.id.btn_login);
        btn_sendCode = findViewById(R.id.btn_submit);
        login_code = findViewById(R.id.login_code);
        btn_again = findViewById(R.id.btn_again);
        visible = findViewById(R.id.visible);
        visible.setVisibility(View.GONE);


    }

    void sendCode() {
        /*   invisible.setVisibility(View.VISIBLE);*/
        login_code.setText("");
        PhoneNumber = login_mobile.text();

        if (!checkMobile()) {
            PublicMethods.showToast("شماره همراه شما درست نیست");
        } else {

            try {
                random();
                Log.i("randon", "random: " + random);
                Log.i("randon", "PhoneNumber: " + PhoneNumber);
                SMSInterface smsInterface = SMSService.getClient().create(SMSInterface.class);
                Call<Void> call = smsInterface.SendCode("GameTiming", PhoneNumber, random);

                PublicMethods.showToast("کد فعالسازی برای شما SMS میشود");


                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.i("randon", "response: " + response.toString());
                        if (response.code() == 200) {
                            progressDialog.dismiss();
                            Log.d("visii", "200: ");
                            btn_login.setVisibility(View.GONE);
                            visible.setVisibility(View.VISIBLE);
                            PublicMethods.showToast(" لطفا کد SMS شده را در قسمت کد فعالسازی ثبت نام وارد نمایید و دکمه فعالسازی ثبت نام را بزنید");
                            /*  invisible.setVisibility(View.VISIBLE);*/

                        } else {
                            progressDialog.dismiss();
                            PublicMethods.showToast("خطا در برقراری ارتباط با سرور");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        PublicMethods.showToast("پاسخی از سرور دریافت نشد");

                    }
                });
            } catch (Exception e) {


            }

        }
    }

    void checkCode() {
        PublicMethods.showToast("اطلاعات در حال ارسال است لطفا منتظر باشید");

        Log.d("randon", "code: " + login_code.text());
        Log.d("randon", "random: " + random.toString());

        int code = Integer.parseInt(login_code.text());
        int rand = Integer.parseInt(random);
        if (code == rand) {
            PublicMethods.setValue("user_mobile", login_mobile.text());
            String message = " ورود با موفقیت انجام شد" + " " + fullName;
            PublicMethods.showToast(message);
            goMain();

        } else {
            PublicMethods.showToast("کد فعالسازی شما با کد ارسالی مطابقت ندارد");
        }
    }


    private void random() {
        int range = (9999 - 1000) + 1;
        random = Integer.toString((int) (Math.random() * range) + 1000);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                loginUser();
                break;

            case R.id.txt_sign_up:
                goRegister();

        }

    }

    private void goRegister() {

        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private boolean checkMobile() {
        String mobile = login_mobile.text().trim();
        if (mobile.startsWith("09") && mobile.length() == 11 && !mobile.equals(""))
            return true;
        else
            return false;
    }

    private void loginUser() {
        PublicMethods.showToast("شماره شما ارسال شد منتظر دریافت اطلاعات باشید");
        PhoneNumber = login_mobile.text();
        String mobile = Hawk.get("userMobile", "09128450301");
        Log.d("hawkk", "onClick: " + PhoneNumber);
        if (PhoneNumber.length() == 11 && !PhoneNumber.equals("") && PhoneNumber.startsWith("09")) {

            GameTimingInterface gameTimingInterface = GameTimingService.getClient().create(GameTimingInterface.class);
            Call<MyResponse<UserModel>> call = gameTimingInterface.LoginUser(PhoneNumber);
            call.enqueue(new Callback<MyResponse<UserModel>>() {
                @Override
                public void onResponse(Call<MyResponse<UserModel>> call, Response<MyResponse<UserModel>> response) {
                    Log.d("hawkk", "onResponse: " + response.toString());
                    if (response.code() == 200) {
                        if (response.body().getResultCode() == 1) {
                            fullName = response.body().getResults().getFullName();
                            sendCode();
                        } else if (response.body().getResultCode() == -1) {
                            PublicMethods.showToast(response.body().getMessage());
                            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                            startActivity(intent);


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
                    login_mobile.setText("");
                }
            });

        } else {
            PublicMethods.showToast("شماره شما اشتباه وارد شده یا وارد نشده");
            login_mobile.setText("");
        }
    }

    private void goMain() {

        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
    }


    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}