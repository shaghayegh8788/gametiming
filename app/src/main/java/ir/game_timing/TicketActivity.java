package ir.game_timing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.game_timing.models.MyResponse;
import ir.game_timing.models.TicketModel;
import ir.game_timing.recyclerViewPlayers.PlayersActivity;
import ir.game_timing.retrofit.GameTimingInterface;
import ir.game_timing.retrofit.GameTimingService;
import ir.game_timing.schools.SchoolActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TicketActivity extends AppCompatActivity implements View.OnClickListener {

    FrameLayout frame;
    LinearLayout content;
    MenuItem menuItem;
    Button btn_home, btn_about, btn_school;
    RelativeLayout sansOne, sansTwo, sansThree;
    String TicketDate, EnterId;
    int enterId,TicketId;
    List<TicketModel> TicketList;
    TextView play_one1,play_two1,play_three1,play_gender1,play_age1,play_sans1,
            play_one2,play_two2,play_three2,play_gender2,play_age2,play_sans2,
            play_one3,play_two3,play_three3,play_gender3,play_age3,play_sans3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        TicketDate = getIntent().getStringExtra("PersianDate");
        EnterId = getIntent().getStringExtra("EnterId");
        enterId = Integer.parseInt(EnterId);
        TicketList = new ArrayList<>();

        Log.d("tii", "onCreate: " + TicketDate + " " + EnterId);


        GameTimingInterface gameTimingInterface = GameTimingService.getClient().create(GameTimingInterface.class);
        Call<MyResponse<List<TicketModel>>> sss = gameTimingInterface.getTickets(EnterId, TicketDate);

        sss.enqueue(new Callback<MyResponse<List<TicketModel>>>() {
            @Override
            public void onResponse(Call<MyResponse<List<TicketModel>>> call, Response<MyResponse<List<TicketModel>>> response) {
                Log.d("tii", "onResponse start: " + response.code());
                if (response.code() == 200) {

                    Log.d("tii", "onResponse: " + response.code());
                    if (response.body().getResultCode() == 1) {
                        Log.d("tii", "getResultCode: " + response.body().getResultCode());

                        for (int i = 0; i < response.body().getResults().size(); i++) {
                            TicketModel ticket = new TicketModel();
                            TicketModel get = response.body().getResults().get(i);

                            ticket.setId(get.getId());
                            ticket.setTime(get.getTime());
                            ticket.setGenderId(get.getGenderId());
                            int gender = get.getGenderId();
                            String convert = ConvertGender(gender);
                            ticket.setGameAge(get.getGameAge());
                            Log.d("lii", "afterconve: "+convert);
                            ticket.setGenderConvert(convert);
                            ticket.setTime(get.getTime());

                            TicketList.add(ticket);



                            Log.d("lii", "getId: " + get.getId());
                            Log.d("lii", "getPersianDate: " + get.getPersianDate());
                            Log.d("lii", "getGenderId: " + get.getGenderId());
                            Log.d("lii", "ticketModel: " + ticket.getGenderConvert());
                            Log.d("lii", "getTime: " + get.getTime());
                            Log.d("lii", "getGameAge: " + get.getGameAge());
                        }
                        Log.d("liii", "TicketList.size: "+ TicketList.size());
                  /*      TicketModel model0 = TicketList.get(0);
                        TicketModel model1 = TicketList.get(1);
                        TicketModel model2 = TicketList.get(2);*/



                       /* play_one1.setText("نقاشی");
                        play_two1.setText("g");
                        play_three1.setText("g");*/

                        String gender1 = TicketList.get(0).getGenderConvert();
                        play_gender1.setText(gender1);
                        Log.d("liii", "play_gender1: " + gender1);

                        String age1 = TicketList.get(0).getGameAge();
                        play_age1.setText(age1);
                        Log.d("liii", "play_age1: " + age1);

                        String sans1 = TicketList.get(0).getTime();
                        play_sans1.setText(sans1);
                        Log.d("liii", "play_sans1: " + sans1);

                   /*     play_one2.setText("f");
                        play_two2.setText("f");
                        play_three2.setText("f");*/
                        String gender2 = TicketList.get(1).getGenderConvert();
                        play_gender2.setText(gender2);
                        Log.d("liii", "play_gender2: " + gender2);
                        String age = TicketList.get(1).getGameAge();
                        play_age2.setText(age);
                        Log.d("liii", "play_age2: " + age);
                        String time = TicketList.get(1).getTime();
                        play_sans2.setText(time);
                        Log.d("liii", "play_sans2: " + time);

                  /*      play_one3.setText("v");
                        play_two3.setText("v");
                        play_three3.setText("v");*/
                        play_gender3.setText(TicketList.get(2).getGenderConvert());
                        Log.d("liii", "play_gender3: " + TicketList.get(2).getGenderConvert());
                        play_age3.setText(TicketList.get(2).getGameAge());
                        Log.d("liii", "play_gender3: " + TicketList.get(2).getGameAge());
                        play_sans3.setText(TicketList.get(2).getTime());
                        Log.d("liii", "play_gender3: " + TicketList.get(2).getTime());



                    }


                }
            }

            @Override
            public void onFailure(Call<MyResponse<List<TicketModel>>> call, Throwable t) {

            }


        });


        bind();


        sansOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TicketActivity.this, PlayersActivity.class);
                int id = TicketList.get(0).getId();
                Log.d("pplppl", "intent1: "+ id+"");
                intent.putExtra("TicketId", id);
                startActivity(intent);
            }
        });

        sansTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TicketActivity.this, PlayersActivity.class);
                int id = TicketList.get(1).getId();
                Log.d("ppl", "intent1: "+ id+"");
                intent.putExtra("TicketId",id);
                startActivity(intent);
            }
        });
        sansThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TicketActivity.this, PlayersActivity.class);
                int id = TicketList.get(2).getId();
                Log.d("ppl", "intent1: "+ id+"");
                intent.putExtra("TicketId",id);
                startActivity(intent);
            }
        });


        menuItem = null;

        frame = findViewById(R.id.frame);
        content = findViewById(R.id.content);


    }

    private String ConvertGender(int genderId) {
        Log.d("lii", "ConvertGender: "+genderId);
        String gender = "";
        if(genderId == 0)
        {
            gender = "فقط پسر";
        }
        if (genderId == 1)
        {
            gender = "فقط دختر";
        }
        if (genderId == 2)
        {
            gender = " دختر و پسر";
        }
        /*switch (genderId) {
            case 0:
                gender = "فقط پسر";
                break;

            case 1:
                gender = "فقط دختر";
                break;

            case 2:

                break;
        }*/
        Log.d("lii", "ConvertGenderEnd: "+gender);
        return gender;

    }

    private void bind() {
        sansOne = findViewById(R.id.sansOne);
        sansTwo = findViewById(R.id.sansTwo);
        sansThree = findViewById(R.id.sansThree);
        btn_home = findViewById(R.id.btn_home);
        btn_school = findViewById(R.id.btn_school);
        btn_about = findViewById(R.id.btn_about);
        play_one1 = findViewById(R.id.play_one1);
        play_two1 = findViewById(R.id.play_two1);
        play_three1 = findViewById(R.id.play_three1);
        play_age1 = findViewById(R.id.play_age1);
        play_sans1 = findViewById(R.id.play_sans1);
        play_gender1 = findViewById(R.id.play_gender1);

        play_one2 = findViewById(R.id.play_one2);
        play_two2 = findViewById(R.id.play_two2);
        play_three2 = findViewById(R.id.play_three2);
        play_age2 = findViewById(R.id.play_age2);
        play_sans2 = findViewById(R.id.play_sans2);
        play_gender2 = findViewById(R.id.play_gender2);

        play_one3 = findViewById(R.id.play_one3);
        play_two3 = findViewById(R.id.play_two3);
        play_three3 = findViewById(R.id.play_three3);
        play_age3 = findViewById(R.id.play_age3);
        play_sans3 = findViewById(R.id.play_sans3);
        play_gender3 = findViewById(R.id.play_gender3);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });
        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goAbout();
            }
        });
        btn_school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSchool();
            }
        });
    }


    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_home:
                goHome();
                break;

            case R.id.btn_about:
                goAbout();
                break;

            case R.id.btn_school:
                goSchool();
                break;
        }
    }

    private void goSchool() {
        Intent intent = new Intent(TicketActivity.this, SchoolActivity.class);
        startActivity(intent);
    }

    private void goAbout() {
        Intent intent = new Intent(TicketActivity.this, AboutUsActivity.class);
        startActivity(intent);
    }

    private void goHome() {
        Intent intent = new Intent(TicketActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}
