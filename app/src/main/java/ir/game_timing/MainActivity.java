package ir.game_timing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.readystatesoftware.viewbadger.BadgeView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ir.game_timing.drawer.children.EditChildrenActivity;
import ir.game_timing.drawer.user.EditUserActivity;
import ir.game_timing.message.ShowMessageActivity;
import ir.game_timing.models.MessageModel;
import ir.game_timing.models.MyResponse;
import ir.game_timing.models.UserModel;
import ir.game_timing.pictureVideo.PicActivity;
import ir.game_timing.retrofit.GameTimingInterface;
import ir.game_timing.retrofit.GameTimingService;
import ir.game_timing.schools.SchoolFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends BaseActivity {
    DrawerLayout drawer_menu;
    ImageView setting, email;
    private BottomNavigationView bottomNavigationView;
    FrameLayout container;
    Button btn_about, btn_gallery, btn_support, btn_edit, btn_child, btn_shop, btn_ticket;
    TextView txt_info;
    List<MessageModel> messageModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind();

        getInfo();

        click();
        checkMessage();




        Menu menu = bottomNavigationView.getMenu();
        selectedFragment(menu.getItem(0));


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectedFragment(item);
                return false;
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
                            Hawk.put("messageModelList",messageModelList);
                            BadgeView badge = new BadgeView(MainActivity.this, email);
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

    private void click() {

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.setting2) {
                    if (!drawer_menu.isDrawerOpen(Gravity.START)) {
                        drawer_menu.openDrawer(Gravity.START);
                    }
                }
            }
        });

        btn_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EditChildrenActivity.class));
            }
        });

        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                PublicMethods.showToast("about us");
                startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
            }

        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EditUserActivity.class));
            }
        });
        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, PicActivity.class));
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("showww", "onClick: " + messageModelList.size());
                if (messageModelList.size() > 0) {
                    Intent intent = new Intent(MainActivity.this, ShowMessageActivity.class);
                    Hawk.put("MessageList",messageModelList);
                    for(int i =0;i<messageModelList.size();i++)
                    {
                        Log.d("121212", "onClick: "+messageModelList.get(i).getId()+" "+messageModelList.get(i).getTiltle());
                    }
                    startActivity(intent);


                } else {
                    PublicMethods.showToast("پیامی برای شما موجود نیست");
                }
            }
        });
    }

    private void bind() {

        email = findViewById(R.id.email);
        btn_about = findViewById(R.id.btn_about);
        btn_support = findViewById(R.id.btn_support);
        btn_gallery = findViewById(R.id.btn_gallery);
        btn_edit = findViewById(R.id.btn_edit);
        btn_child = findViewById(R.id.btn_child);
        btn_shop = findViewById(R.id.btn_shop);
        btn_ticket = findViewById(R.id.btn_ticket);
        txt_info = findViewById(R.id.txt_info);
        drawer_menu = findViewById(R.id.drawer_menu);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        container = findViewById(R.id.container);
        setting = findViewById(R.id.setting2);
    }

    private void selectedFragment(MenuItem item) {
        item.setChecked(true);
        int id = item.getItemId();
        switch (id) {

            case R.id.nav_home:

                setFragment(new HomeFragment());

                break;
            case R.id.nav_about_us:

                setFragment(new AboutUsFragment());
                break;
            case R.id.nav_schools:

                setFragment(new SchoolFragment());
                break;

        }


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

    private void setFragment(Fragment fragment) {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
