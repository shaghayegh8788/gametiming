package ir.game_timing.recyclerViewPlayers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ir.game_timing.AboutUsActivity;
import ir.game_timing.BaseActivity;
import ir.game_timing.HomeActivity;
import ir.game_timing.PublicMethods;
import ir.game_timing.R;
import ir.game_timing.customViews.MyEditText;
import ir.game_timing.recyclerViewChildren.ChildrenModel;
import ir.game_timing.schools.SchoolActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static ir.game_timing.R.id;


public class PlayersActivity extends BaseActivity implements View.OnClickListener, PlayersAdapter.CallBackInterface {
    PlayersAdapter adapter;
    List<ChildrenModel> playersList;
    RecyclerView recycler_players;
    RelativeLayout layout_count;
    MyEditText count_players;
    String countString = "0";
    int countInt = 1;
    TextView over, valencyString, percentString, costString, txt_info;
    Button btn_submit;
    int percent = 3;
    int cost = 35000;
    int pay = 0;
    Button btn_home, btn_about, btn_school;
    String user_mobile;
    int TicketId;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
        user_mobile = PublicMethods.getValue("user_mobile", "09121234567");
        TicketId = getIntent().getIntExtra("TicketId", 0);
        bind();
        /*
        Shamsi_Date();
        String shamsi = Shamsi_Date();
        Log.d("Shamsi_Date", "Shamsi_Date: " + shamsi);*/
      /*  count_players.requestFocus();*/
      /*  dialog di = new dialog(PlayersActivity.this);
        di.show();*/

    /*    btn_home.setOnClickListener(this);
        btn_about.setOnClickListener(this);
        btn_school.setOnClickListener(this);*/

        count_players.setText("1");
        countInt = Integer.parseInt(count_players.text());
        updateRegisterList(countInt);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlayersActivity.this, DetailsEntertainmentActivity.class);

                pay = cost - ((cost * percent) / 100);
                Log.d("perrr", "onClick: " + pay);
                intent.putExtra("payment", pay);
                intent.putExtra("percent", percent);
                intent.putExtra("TicketId", TicketId);
                startActivity(intent);

            }
        });

        count_players.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String count = count_players.text();
                if (count.equals("")) {
                    count = "1";

                }
                int countInt = Integer.parseInt(count);
                if (countInt > 0 && countInt <= 5) {
                    updateRegisterList(countInt);
                    /*  adapter.notifyDataSetChanged()*/
                    ;
                } else {
                    playersList.clear();

                    PublicMethods.showToast("تعداد فرزندان قابل قبول تا 5 نفر می باشد");
                }
            }
        });
    }

    private void changeCosts() {

        getCosts(playersList.size());
        bargain(playersList.size());
        percentString.setText(percent + "" + "%");
        getCosts(playersList.size());
        costString.setText(cost + "");
    }

    private int getCosts(int size) {
        if (size > 1) {
            switch (size) {
                case 1:
                    cost = 35000;
                    break;

                case 2:
                    cost = 70000;
                    break;

                case 3:
                    cost = 105000;
                    break;

                case 4:
                    cost = 140000;
                    break;

                case 5:
                    cost = 175000;
                    break;
            }

        }

        return cost;
    }

    private int bargain(int size) {
        if (size > 1) {
            switch (size) {
                case 1:
                    percent = 3;
                    break;

                case 2:
                    percent = 5;
                    break;

                case 3:
                    percent = 7;
                    break;

                case 4:
                    percent = 9;
                    break;

                case 5:
                    percent = 11;
                    break;
            }

        }
        return percent;
    }


    private void updateRegisterList(int countInt) {
        playersList = new ArrayList<>();
        playersList.clear();
        for (int i = 1; i <= countInt; i++) {
            ChildrenModel model = new ChildrenModel();
            model.setChild_firstName("");
            model.setChild_lastName("");
            model.setChild_gender("");
            model.setChildAge("");
            playersList.add(model);

        }
        LinearLayoutManager li = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        Log.d("ppl", "updateRegisterLisActivityt: " + TicketId);
        adapter = new PlayersAdapter(mContext, playersList, TicketId);
        recycler_players.setLayoutManager(li);
        recycler_players.setAdapter(adapter);
        int size = playersList.size();
        if (size > 1) {
            String number = convertNumber(size);
            PublicMethods.showToast(number + " " + "فرم برای ثبت نام برای شما ساخته شد.");
        }

        changeCosts();
    }

    private String convertNumber(int number) {
        String echo = "";
        switch (number) {
            case 2:
                echo = "دو";
                break;

            case 3:
                echo = "سه";
                break;

            case 4:
                echo = "چهار";
                break;

            case 5:
                echo = "پنج";
                break;
        }
        return echo;
    }

    private void bind() {
        recycler_players = findViewById(id.recycler_players);
        count_players = findViewById(id.count_players);
        costString = findViewById(id.cost);
        btn_submit = findViewById(id.btn_submit);
        valencyString = findViewById(id.valency);
        percentString = findViewById(id.percent);
        btn_home = findViewById(id.btn_home);
        btn_school = findViewById(id.btn_school);
        btn_about = findViewById(id.btn_about);
        layout_count = findViewById(id.layout_count);
        txt_info = findViewById(id.txt_info);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case id.btn_home:
                goHome();
                break;

            case id.btn_about:
                goAbout();
                break;

            case id.btn_school:
                goSchool();
                break;
        }
    }

    private void goSchool() {
        Intent intent = new Intent(PlayersActivity.this, SchoolActivity.class);
        startActivity(intent);
    }

    private void goAbout() {
        Intent intent = new Intent(PlayersActivity.this, AboutUsActivity.class);
        startActivity(intent);
    }

    private void goHome() {
        Intent intent = new Intent(PlayersActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    public static String Shamsi_Date() {

        Calendar cal = Calendar.getInstance();
        int Day = cal.get(Calendar.DAY_OF_MONTH);
        int Month = cal.get(Calendar.MONTH) + 1;
        int Year = cal.get(Calendar.YEAR);
        int Day_Of_Year = cal.get(Calendar.DAY_OF_YEAR);

        //---------------------------------------------

        if (Day_Of_Year <= 80)
            Year -= 622;
        else
            Year -= 621;

        switch (Month) {
            case 1:
                if (Day < 21) {
                    Month = 10;
                    Day += 10;
                } else {
                    Month = 11;
                    Day -= 20;
                }
                break;

            case 2:
                if (Day < 20) {
                    Month = 11;
                    Day += 11;
                } else {
                    Month = 12;
                    Day -= 19;
                }
                break;

            case 3:
                if (Day < 21) {
                    Month = 12;
                    Day += 9;
                } else {
                    Month = 1;
                    Day -= 20;
                }
                break;

            case 4:
                if (Day < 21) {
                    Month = 1;
                    Day += 11;
                } else {
                    Month = 2;
                    Day -= 20;
                }
                break;

            case 5:
            case 6:
                if (Day < 22) {
                    Month -= 3;
                    Day += 10;
                } else {
                    Month -= 2;
                    Day -= 21;
                }
                break;

            case 7:
            case 8:
            case 9:
                if (Day < 23) {
                    Month -= 3;
                    Day += 9;
                } else {
                    Month -= 2;
                    Day -= 22;
                }
                break;

            case 10:
                if (Day < 23) {
                    Month = 7;
                    Day += 8;
                } else {
                    Month = 8;
                    Day -= 22;
                }
                break;

            case 11:
            case 12:
                if (Day < 22) {
                    Month -= 3;
                    Day += 9;
                } else {
                    Month -= 2;
                    Day -= 21;
                }
                break;
        }
        return String.valueOf(Year) + "/" + String.valueOf(Month) + "/" + String.valueOf(Day);

    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void EndRegister() {
        layout_count.setVisibility(View.GONE);
        txt_info.setVisibility(View.GONE);
    }
}
