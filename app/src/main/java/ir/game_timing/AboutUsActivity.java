package ir.game_timing;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
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
import ir.game_timing.schools.SchoolActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AboutUsActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_home,btn_about,btn_school,btn_aboutDrawer, btn_gallery, btn_support, btn_edit, btn_child, btn_shop, btn_ticket;;
    List<MessageModel> messageModelList;
    DrawerLayout drawer_menu;
    ImageView setting, email;
    TextView txt_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        bind();
        click();
        getInfo();
        checkMessage();
        btn_home.setOnClickListener(this);
        btn_about.setOnClickListener(this);
        btn_school.setOnClickListener(this);
    }

    public void bind() {
        btn_home = findViewById(R.id.btn_homeAbout);
        btn_school = findViewById(R.id.btn_schoolAbout);
        btn_about = findViewById(R.id.btn_aboutAbout);

        email = findViewById(R.id.emailAbout);
        btn_aboutDrawer = findViewById(R.id.btn_aboutDrawer);
        btn_support = findViewById(R.id.btn_support);
        btn_gallery = findViewById(R.id.btn_gallery);
        btn_edit = findViewById(R.id.btn_edit);
        btn_child = findViewById(R.id.btn_child);
        btn_shop = findViewById(R.id.btn_shop);
        btn_ticket = findViewById(R.id.btn_ticket);
        txt_info = findViewById(R.id.txt_info);
        drawer_menu = findViewById(R.id.drawerAbout);
        setting = findViewById(R.id.settingAbout);
    }

    private void click() {

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.settingAbout) {
                    if (!drawer_menu.isDrawerOpen(Gravity.START)) {
                        drawer_menu.openDrawer(Gravity.START);
                    }
                }
            }
        });

        btn_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutUsActivity.this, EditChildrenActivity.class));
            }
        });

        btn_aboutDrawer.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                startActivity(new Intent(AboutUsActivity.this, AboutUsActivity.class));
            }

        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutUsActivity.this, EditUserActivity.class));
            }
        });
        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(AboutUsActivity.this, PicActivity.class));
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("showww", "onClick: " + messageModelList.size());
                if (messageModelList.size() > 0) {
                    Intent intent = new Intent(AboutUsActivity.this, ShowMessageActivity.class);
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
                            BadgeView badge = new BadgeView(AboutUsActivity.this, email);
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


    @Override
    public void onClick(View v) {

        switch (v.getId())
        {

            case  R.id.btn_homeAbout:
                goHome();
                break;

            case  R.id.btn_aboutAbout:
                goAbout();
                break;

            case  R.id.btn_schoolAbout:
                goSchool();
                break;

        }

    }

    private void goSchool() {
        Intent intent = new Intent(AboutUsActivity.this,SchoolActivity.class);
        startActivity(intent);
    }

    private void goAbout() {
        Intent intent = new Intent(AboutUsActivity.this,AboutUsActivity.class);
        startActivity(intent);
    }

    private void goHome() {
        Intent intent = new Intent(AboutUsActivity.this,HomeActivity.class);
        startActivity(intent);
    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
