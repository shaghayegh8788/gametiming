package ir.game_timing.recyclerViewDay;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import ir.game_timing.AboutUsActivity;
import ir.game_timing.BaseActivity;
import ir.game_timing.HomeActivity;
import ir.game_timing.R;
import ir.game_timing.schools.SchoolActivity;
import ir.game_timing.TicketActivity;
import ir.game_timing.models.MyResponse;
import ir.game_timing.retrofit.GameTimingInterface;
import ir.game_timing.retrofit.GameTimingService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SelectDayActivity extends BaseActivity implements View.OnClickListener, RecyclerDayAdapter.CallBackInterface {
    private RecyclerDayAdapter.CallBackInterface mCallback;
    FrameLayout frame;
    RecyclerView recycler_day;
    MenuItem menuItem;
    List<DayModel> DaysList;
    RecyclerDayAdapter adapter;
    LinearLayout view_recycler;
    Button btn_home, btn_about, btn_school;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_day);
        recycler_day = findViewById(R.id.recycler_day);
        view_recycler = findViewById(R.id.view_recycler);
        btn_home = findViewById(R.id.btn_home);
        btn_school = findViewById(R.id.btn_school);
        btn_about = findViewById(R.id.btn_about);
        btn_home.setOnClickListener(this);
        btn_about.setOnClickListener(this);
        btn_school.setOnClickListener(this);
        DaysList = new ArrayList<>();
        GameTimingInterface gameTimingInterface = GameTimingService.getClient().create(GameTimingInterface.class);
        Call<MyResponse<List<DayModel>>> sss = gameTimingInterface.getEntertainments();

        sss.enqueue(new Callback<MyResponse<List<DayModel>>>() {
            @Override
            public void onResponse(Call<MyResponse<List<DayModel>>> call, Response<MyResponse<List<DayModel>>> response) {
                Log.d("dayy", "onResponse start: ");
                if (response.code() == 200) {

                    Log.d("dayy", "onResponse: " + response.code());
                    Log.d("dayy", "onResponse: " + response.body());
                    if (response.body().getResultCode() == 1) {
                        Log.d("dayy", "getResultCode: " + response.body());

                        for (int i = 0; i < response.body().getResults().size(); i++) {
                            DayModel day = new DayModel();
                            DayModel get = response.body().getResults().get(i);
                            day.setPlaceName(get.getPlaceName());
                            String placeAdress = shortAddress(get.getPlaceAddress());
                            day.setPlaceAddress(placeAdress);
                            day.setEnterId(get.getEnterId());
                            String index = convertPosition(i);
                            day.setDay(index);
                            day.setDayOfWeek(get.getDayOfWeek());
                            day.setPersianDate(get.getPersianDate());
                            DaysList.add(day);
                            Log.d("dayy", "Index: " + index);
                            Log.d("dayy", "getPlaceName: " + get.getPlaceName());
                            Log.d("dayy", "getEnterId: " +  get.getEnterId());
                            Log.d("dayy", "getWeekDay: " + get.getDayOfWeek());
                            Log.d("dayy", "getDateTime: " + get.getPersianDate());
                            Log.d("dayy", "getPlaceAddress: " + get.getPlaceAddress());
                        }
                        adapter = new RecyclerDayAdapter(DaysList, mContext);
                        Log.d("dayy", "adapter: " + DaysList.size());
                        LinearLayoutManager li = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                        recycler_day.setAdapter(adapter);
                        recycler_day.setLayoutManager(li);
                    }

                }
            }

            @Override
            public void onFailure(Call<MyResponse<List<DayModel>>> call, Throwable t) {

            }


        });




    }

    private String convertPosition(int i) {
        String convert;
        Integer number = i + 1;
        if (i < 10) {
            convert = "0" + number;
        } else {
            convert = number + "";
        }
        return convert;
    }

    public String shortAddress(String placeAddress) {
        String[] newAdress = placeAddress.split(" ");

        String address = newAdress[0] + " " + newAdress[1] + " " + newAdress[2] + " " + newAdress[3] + " " + newAdress[4];
        Log.d("dayy", "shortAddress: " + address);

        return address;

    }


    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    public void sendModel(int position) {
        Log.d("modell", "sendModelActivity: "+position);
        DayModel model = DaysList.get(position);
        Intent intent = new Intent(SelectDayActivity.this, TicketActivity.class);
        intent.putExtra("PersianDate", model.getPersianDate());
        intent.putExtra("EnterId", model.getEnterId());
        Log.d("modell", "sendModel: " + model.getPersianDate());
        Log.d("modell", "getEnterId: " + model.getEnterId());
        startActivity(intent);
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
        Intent intent = new Intent(SelectDayActivity.this, SchoolActivity.class);
        startActivity(intent);
    }

    private void goAbout() {
        Intent intent = new Intent(SelectDayActivity.this, AboutUsActivity.class);
        startActivity(intent);
    }

    private void goHome() {
        Intent intent = new Intent(SelectDayActivity.this, HomeActivity.class);
        startActivity(intent);
    }

}
