package ir.game_timing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import ir.game_timing.models.MyResponse;
import ir.game_timing.models.PlayerModel;
import ir.game_timing.recyclerViewPlayers.DetailsEntertainmentActivity;
import ir.game_timing.retrofit.GameTimingInterface;
import ir.game_timing.retrofit.GameTimingService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class EditPlayerActivity extends AppCompatActivity {

    PlayerModel model;
    EditText FullName, Age;
    RadioGroup genderPlayer;
    String fullName, phoneNumber, newFullname;
    RadioButton femail, mail;
    Button btn_register, btn_back;
    int age, gender, childId, TicketId, userId, newAge, newGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_player);
        bind();
        fullName = getIntent().getStringExtra("fullName");
        age = getIntent().getIntExtra("age", 0);
        gender = getIntent().getIntExtra("gender", 0);
        childId = getIntent().getIntExtra("id", 0);
        TicketId = getIntent().getIntExtra("TicketId", 0);
        userId = getIntent().getIntExtra("userId", 0);
        newFullname = fullName;
        newAge = age;
        phoneNumber = PublicMethods.getValue("user_mobile", "09121234567");/// TODO: 9/16/2018  change default number

        FullName.setText(fullName);
        Age.setText(age + "");

        if (gender == 0) {
            mail.setChecked(true);
        } else {
            femail.setChecked(true);
        }

        FullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                /* newFullname = fullName;*/
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    PublicMethods.showToast("نام فرزند نمیتواند خالی باشد");
                } else
                    newFullname = s.toString();
                Age.requestFocus();
            }
        });


        Age.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("chh", "beforeTextChanged: ");

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("chh", "onTextChanged: ");

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals(""))
                {
                    int AgeEdit = Integer.parseInt(s.toString());

                    if (s.toString().equals("")) {
                        Age.setText("");
                        PublicMethods.showToast("مقدار وارد شده نمی تواند خالی باشد");
                    }
                    if (AgeEdit > 2 && AgeEdit < 13) {
                        String ageEditText = s.toString();
                        newAge = Integer.parseInt(ageEditText);
                    } else {
                        PublicMethods.showToast("مقدار وارد شده صحیح نمی باشد.");
                        Age.setText("");
                        PublicMethods.showToast("مقدار وارد شده نمی تواند خالی باشد");
                    }
                }



            }
        });

        genderPlayer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                newGender = mail.isChecked() == true ? 0 : 1;
            }
        });

        Log.d("sendPosition", "onCreate: " + fullName);
        Log.d("sendPosition", "onCreate: " + age);
        Log.d("sendPosition", "onCreate: " + gender);


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("11111", "onClick: ");
                GameTimingInterface gameTimingInterface = GameTimingService.getClient().create(GameTimingInterface.class);
                Log.d("11111", "onClick: " + phoneNumber + " " + newFullname + " " + newAge + " " + newGender + " " + childId + " " + TicketId + " " + userId);
                Call<MyResponse<PlayerModel>> call = gameTimingInterface.UpdatePlayer(phoneNumber, newFullname, newAge, newGender, childId, TicketId, userId);
                call.enqueue(new Callback<MyResponse<PlayerModel>>() {
                    @Override
                    public void onResponse(Call<MyResponse<PlayerModel>> call, Response<MyResponse<PlayerModel>> response) {
                        Log.d("11111", "onResponse: " + response.code());
                        if (response.code() == 200) {

                            if (response.body().getResultCode() == 1) {
                                Log.d("11111", "onResponse: " + response.body().getResultCode());
                                PublicMethods.showToast("اطلاعات " + newFullname + " " + "ثبت شد");
                                startActivity(new Intent(EditPlayerActivity.this, DetailsEntertainmentActivity.class));
                            }
                        } else {
                            /* PublicMethods.showToast(response.body().toString());*/
                            /// TODO: 9/16/2018 get message error
                        }
                    }

                    @Override
                    public void onFailure(Call<MyResponse<PlayerModel>> call, Throwable t) {

                    }
                });


            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditPlayerActivity.this, DetailsEntertainmentActivity.class));
            }
        });

    }

    private void bind() {
        FullName = findViewById(R.id.FullName);
        Age = findViewById(R.id.age);
        genderPlayer = findViewById(R.id.genderPlayer);
        femail = findViewById(R.id.radio_femailP);
        mail = findViewById(R.id.radio_mailP);
        btn_back = findViewById(R.id.btn_back);
        btn_register = findViewById(R.id.btn_register);

    }
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
