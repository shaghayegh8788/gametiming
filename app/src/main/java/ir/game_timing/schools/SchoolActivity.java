package ir.game_timing.schools;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.readystatesoftware.viewbadger.BadgeView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import ir.game_timing.AboutUsActivity;
import ir.game_timing.HomeActivity;
import ir.game_timing.PublicMethods;
import ir.game_timing.R;
import ir.game_timing.drawer.children.EditChildrenActivity;
import ir.game_timing.drawer.user.EditUserActivity;
import ir.game_timing.message.ShowMessageActivity;
import ir.game_timing.models.BaseModel;
import ir.game_timing.models.MessageModel;
import ir.game_timing.models.MyResponse;
import ir.game_timing.models.UserModel;
import ir.game_timing.pictureVideo.PicActivity;
import ir.game_timing.retrofit.GameTimingInterface;
import ir.game_timing.retrofit.GameTimingService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SchoolActivity extends AppCompatActivity implements View.OnClickListener {
    ViewPager viewPager_school;
    Button btn_home, btn_about, btn_school, btn_aboutDrawer, btn_gallery, btn_support, btn_edit, btn_child, btn_shop, btn_ticket;
    List<BaseModel> lstSchool;
    List<MessageModel> messageModelList;
    DrawerLayout drawer_menu;
    ImageView setting, email;
    TextView txt_info;
    LinearLayout SliderDots;
    private int dotscount;
    private ImageView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school);


        bind();
        click();
        getInfo();
        checkMessage();
        getBase();
        btn_home.setOnClickListener(this);
        btn_about.setOnClickListener(this);
        btn_school.setOnClickListener(this);

  /*      Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimeTask(), 2000, 4000);*/

    }

    public void bind() {
        btn_home = findViewById(R.id.btn_home);
        btn_school = findViewById(R.id.btn_school);
        btn_about = findViewById(R.id.btn_about);
        viewPager_school = findViewById(R.id.viewPager_school);
        SliderDots = findViewById(R.id.SliderDots);


        email = findViewById(R.id.emailSchool);
        btn_aboutDrawer = findViewById(R.id.btn_aboutDrawer);
        btn_support = findViewById(R.id.btn_support);
        btn_gallery = findViewById(R.id.btn_gallery);
        btn_edit = findViewById(R.id.btn_edit);
        btn_child = findViewById(R.id.btn_child);
        btn_shop = findViewById(R.id.btn_shop);
        btn_ticket = findViewById(R.id.btn_ticket);
        txt_info = findViewById(R.id.txt_info);
        drawer_menu = findViewById(R.id.drawerSchool);
        setting = findViewById(R.id.settingSchool);
    }


    private void click() {

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.settingSchool) {
                    if (!drawer_menu.isDrawerOpen(Gravity.START)) {
                        drawer_menu.openDrawer(Gravity.START);
                    }
                }
            }
        });

        btn_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SchoolActivity.this, EditChildrenActivity.class));
            }
        });

        btn_aboutDrawer.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                startActivity(new Intent(SchoolActivity.this, AboutUsActivity.class));
            }

        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SchoolActivity.this, EditUserActivity.class));
            }
        });
        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SchoolActivity.this, PicActivity.class));
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("showww", "onClick: " + messageModelList.size());
                if (messageModelList.size() > 0) {
                    Intent intent = new Intent(SchoolActivity.this, ShowMessageActivity.class);
                    Hawk.put("MessageList", messageModelList);
                    for (int i = 0; i < messageModelList.size(); i++) {
                        Log.d("121212", "onClick: " + messageModelList.get(i).getId() + " " + messageModelList.get(i).getTiltle());
                    }
                    startActivity(intent);


                } else {
                    PublicMethods.showToast("پیامی برای شما موجود نیست");
                }
            }
        });
    }

    private void checkMessage() {
        String phoneNumber = PublicMethods.getValue("user_mobile", "09128450301");//// TODO: 9/22/2018
        Log.d("messs", "checkMessage: ");
        Log.d("messs", "checkMessage: " + phoneNumber);
        GameTimingInterface gameTimingInterface = GameTimingService.getClient().create(GameTimingInterface.class);
        Call<MyResponse<List<MessageModel>>> call = gameTimingInterface.getMessage2(phoneNumber);
        call.enqueue(new Callback<MyResponse<List<MessageModel>>>() {

            @Override
            public void onResponse(Call<MyResponse<List<MessageModel>>> call, Response<MyResponse<List<MessageModel>>> response) {
                if (response.code() == 200) {
                    if (response.body().getResultCode() == 1) {
                        messageModelList = new ArrayList<>();
                        for (int i = 0; i < response.body().getResults().size(); i++) {
                            MessageModel model = new MessageModel();
                            MessageModel get = response.body().getResults().get(i);
                            model.setId(get.getId());
                            model.setTiltle(get.getTiltle());
                            model.setContent(get.getContent());
                            messageModelList.add(model);
                            Log.d("messs", "onResponse: " + model.getTiltle() + " " + model.getContent());
                        }
                        if (messageModelList.size() > 0) {
                            Hawk.put("messageModelList", messageModelList);
                            BadgeView badge = new BadgeView(SchoolActivity.this, email);
                            badge.setText(messageModelList.size() + "");
                            badge.show();
                            badge.setBadgeMargin(15);

                        }

                    }
                    if (response.body().getResultCode() == -1) {
                        PublicMethods.showToast(response.body().getMessage());
                    }

                } else PublicMethods.showToast("خطا در برقراری ارتباط با سرور");
            }

            @Override
            public void onFailure(Call<MyResponse<List<MessageModel>>> call, Throwable t) {
                PublicMethods.showToast("خطا در برقراری ارتباط با سرور");
            }
        });
    }


    private void getInfo() {
        String PhoneNumber = PublicMethods.getValue("user_mobile", "09128450301");/// TODO: 9/20/2018 change number
        Log.d("1111", "getInfo: +" + PhoneNumber);
        GameTimingInterface gameTimingInterface = GameTimingService.getClient().create(GameTimingInterface.class);
        Call<MyResponse<UserModel>> call = gameTimingInterface.GetInfo(PhoneNumber);
        call.enqueue(new Callback<MyResponse<UserModel>>() {
            @Override
            public void onResponse(Call<MyResponse<UserModel>> call, Response<MyResponse<UserModel>> response) {
                Log.d("1111", "onResponse: " + response.body().getMessage());
                Log.d("1111", "onResponse: " + response.body().getResultCode());
                if (response.code() == 200) {
                    if (response.body().getResultCode() == 1) {
                        UserModel user = new UserModel();
                        String Fullname = (response.body().getResults().getFullName());
                        Log.d("", "onResponse: " + user.getPhoneNumber() + " " + user.getFullName());
                        txt_info.setText(Fullname);
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

    public class MyTimeTask extends TimerTask {

        @Override
        public void run() {

            SchoolActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager_school.getCurrentItem() == 0) {
                        viewPager_school.setCurrentItem(1);
                    } else if (viewPager_school.getCurrentItem() == 1) {
                        viewPager_school.setCurrentItem(2);
                    } else {
                        viewPager_school.setCurrentItem(0);
                    }
                }
            });
        }
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
        Intent intent = new Intent(SchoolActivity.this, SchoolActivity.class);
        startActivity(intent);
    }

    private void goAbout() {
        Intent intent = new Intent(SchoolActivity.this, AboutUsActivity.class);
        startActivity(intent);
    }

    private void goHome() {
        Intent intent = new Intent(SchoolActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    public void getBase() {

        GameTimingInterface gameTimingInterface = GameTimingService.getClient().create(GameTimingInterface.class);
        Call<MyResponse<List<BaseModel>>> call = gameTimingInterface.GetListSchool();
        call.enqueue(new Callback<MyResponse<List<BaseModel>>>() {
            @Override
            public void onResponse(Call<MyResponse<List<BaseModel>>> call, Response<MyResponse<List<BaseModel>>> response) {
                if (response.code() == 200) {
                    Log.d("baseee", "onResponse: " + response.code() + "");
                    Log.d("baseee", "onResponse: " + response.body().getResultCode() + "");
                    if (response.body().getResultCode() == 1) {
                        lstSchool = new ArrayList<>();
                        Log.d("baseee", "onResponse: " + response.body().getResultCode() + "");
                        for (BaseModel get : response.body().getResults()) {
                            BaseModel model = new BaseModel();
                            model.setId(get.getId());
                            model.setPath(get.getPath());
                            Log.d("baseee", "onResponse: " + get.getId());
                            Log.d("baseee", "onResponse: " + get.getPath());
                            lstSchool.add(model);
                        }
                        Log.d("baseee", "onResponse: " + lstSchool.size() + "");
                        PagerAdapter adapter = new PagerAdapter(SchoolActivity.this, lstSchool);
                        viewPager_school.setAdapter(adapter);
                        dotscount = adapter.getCount();
                        dots = new ImageView[dotscount];

                        for (int i = 0; i < dotscount; i++) {

                            dots[i] = new ImageView(SchoolActivity.this);
                            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));

                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                            params.setMargins(8, 0, 8, 0);

                            SliderDots.addView(dots[i], params);

                        }

                        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

                        viewPager_school.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                            }

                            @Override
                            public void onPageSelected(int position) {

                                for (int i = 0; i < dotscount; i++) {
                                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                                }

                                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {

                            }
                        });


                    } else if (response.body().getResultCode() == -1) {
                        PublicMethods.showToast(response.body().getMessage());
                    } else {
                        try {
                            PublicMethods.showToast(response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse<List<BaseModel>>> call, Throwable t) {
                Log.d("basemodell", "onFailure: " + t.toString());
                PublicMethods.showToast(PublicMethods.noConnection);
            }
        });


    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
