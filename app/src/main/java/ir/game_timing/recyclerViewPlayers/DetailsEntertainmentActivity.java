package ir.game_timing.recyclerViewPlayers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.zarinpal.ewallets.purchase.OnCallbackRequestPaymentListener;
import com.zarinpal.ewallets.purchase.OnCallbackVerificationPaymentListener;
import com.zarinpal.ewallets.purchase.PaymentRequest;
import com.zarinpal.ewallets.purchase.ZarinPal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ir.game_timing.AboutUsActivity;
import ir.game_timing.BaseActivity;
import ir.game_timing.EditPlayerActivity;
import ir.game_timing.HomeActivity;
import ir.game_timing.PublicMethods;
import ir.game_timing.R;
import ir.game_timing.models.MyResponse;
import ir.game_timing.models.Percent;
import ir.game_timing.models.PlayerModel;
import ir.game_timing.retrofit.GameTimingInterface;
import ir.game_timing.retrofit.GameTimingService;
import ir.game_timing.schools.SchoolActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DetailsEntertainmentActivity extends BaseActivity implements View.OnClickListener, ListPlayersAdapter.CallBackInterface {

    TextView percent, cost;
    Button btn_pay, btn_discount;
    int percentage, payment;
    int num = 0;
    Button btn_home, btn_about, btn_school;
    RecyclerView recycle_list;
    ListPlayersAdapter adapter;
    List<PlayerModel> ListChildren;
    Percent modelPercent;
    String Tag = "DetailsEntertainmentActivityy";
    public String priceInt;
    int TicketId;


    @Override
    protected void onResume() {
        super.onResume();
        PublicMethods.showToast("onResume");
        getPlayers(Hawk.get("TicketId",0), Hawk.get("user_mobile","09121234567"));
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        PublicMethods.showToast("onResume");
        getPlayers(Hawk.get("TicketId",0), Hawk.get("user_mobile","09121234567"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_entertainment);
        ListChildren = new ArrayList<>();
        TicketId = getIntent().getIntExtra("TicketId", 0);
        Hawk.put("TicketId",TicketId);
        String PhoneNumber = PublicMethods.getValue("user_mobile", "");
        getPlayers(Hawk.get("TicketId",0), PhoneNumber);
        Uri data = getIntent().getData();
        ZarinPal.getPurchase(mContext).verificationPayment(data, new OnCallbackVerificationPaymentListener() {
            @Override
            public void onCallbackResultVerificationPayment(boolean isPaymentSuccess, String refID, PaymentRequest paymentRequest) {
        if(isPaymentSuccess)
        {
            PublicMethods.showToast("پرداخت با موفقیت انجام شد");
            Log.d("12345", "onCallbackResultVerificationPayment: "+refID);
        }

        else
        {
            PublicMethods.showToast("پرداخت با موفقیت صورت نگرفت");
        }
            }
        });

        bind();
        Log.d("perrr", "onCreate: " + ListChildren.size());
        btn_home.setOnClickListener(this);
        btn_about.setOnClickListener(this);
        btn_school.setOnClickListener(this);
        btn_discount.setOnClickListener(this);
        btn_pay.setOnClickListener(this);


    }

    private void getDiscount(String Number) {
        Log.d("newcostt", "firstClick: ");
        String PhoneNumber = PublicMethods.getValue("user_mobile", "09128450301");
        /// TODO: 9/17/2018  chanfe phone number
        GameTimingInterface gameTimingInterface = GameTimingService.getClient().create(GameTimingInterface.class);
        Call<MyResponse<Percent>> call = gameTimingInterface.GetDiscount(PhoneNumber, Number);
        call.enqueue(new Callback<MyResponse<Percent>>() {
            @Override
            public void onResponse(Call<MyResponse<Percent>> call, Response<MyResponse<Percent>> response) {
                Log.d("newcostt", "onResponse: " + response.code());
                if (response.code() == 200) {
                    Log.d("newcostt", "onResponse: " + response.body().getResultCode());
                    if (response.body().getResultCode() == 1) {
                        Log.d("newcostt", "onResponse: " + response.body().getResultCode());
                        int discount = response.body().getResults().getDiscount();
                        int lastcost = Integer.parseInt(cost.getText().toString());
                        Log.d("newcostt", "onResponse: " + lastcost);
                        final int newCost = (lastcost - (lastcost * discount) / 100);

                        /*   cost.setText(lastcost -((lastcost*discount)/100)+"");*/
                   /*     runOnUiThread(new Runnable() {
                            public void run() {
                                cost.setText(newCost+"");

                            }
                        });*/
                        cost.setText(newCost + "");
                        Log.d("newcostt", "onResponse: " + newCost + "");

                    }
                    if (response.body().getResultCode() == -1) {
                        Log.d("newcostt", "getResultCode: " + response.body().getResultCode());
                        Log.d("newcostt", "onResponse: " + response.body().getMessage());
                    }

                } else if (response.code() == 303) {

                    if (response.errorBody() != null) {
                        try {
                            String meesage = response.errorBody().string();
                            Log.d(Tag, "onResponse: errorBody" + meesage);
                            PublicMethods.showToast(meesage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else
                    PublicMethods.showToast("در برنامه خطایی رخ داده است!");
            }

            @Override
            public void onFailure(Call<MyResponse<Percent>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    private void getCost(int Size) {
        Log.d("perrr", "getCost: " + Size);

        GameTimingInterface gameTimingInterface = GameTimingService.getClient().create(GameTimingInterface.class);
        Call<MyResponse<Percent>> call = gameTimingInterface.GetPercent(Size);
        call.enqueue(new Callback<MyResponse<Percent>>() {
            @Override
            public void onResponse(Call<MyResponse<Percent>> call, Response<MyResponse<Percent>> response) {
                if (response.code() == 200) {
                    if (response.body().getResultCode() == 1) {
                        modelPercent = new Percent();
                        modelPercent.setPercent(response.body().getResults().getPercent());
                        Log.d("perrr", "onResponse: " + response.body().getResults().getPercent() + "");
                        percent.setText(modelPercent.getPercent() + "%");
                        String ggg = ListChildren.get(0).getPrice();
                        Log.d("020202", "onResponse: " + ggg);
                        String priceee = ggg.toString();

                        Log.d("020202", "onResponse: " + Double.valueOf(priceee).intValue());

                        int money = Double.valueOf(priceee).intValue();
                        Log.d("020202", "money: " + money);
                        int per = modelPercent.getPercent();
                        cost.setText((money - (money * per) / 100) + "");
                        Log.d("020202", "per: " + per);
                        Log.d("020202", "cost: " + cost.getText().toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse<Percent>> call, Throwable t) {

            }
        });

    }

    private void getPlayers(int ticketId, String phoneNumber) {
        Log.d(Tag, "getPlayers: ");
       /* ticketId = Hawk.get("TicketId");*/
        String PhoneNumber = PublicMethods.getValue("user_mobile", "");
        Log.d("asdqwe", "getPlayers: " + phoneNumber + " " + ticketId);
        if (ticketId > 0 && !phoneNumber.equals("")) {

            GameTimingInterface gameTimingInterface = GameTimingService.getClient().create(GameTimingInterface.class);
            Call<MyResponse<List<PlayerModel>>> call = gameTimingInterface.GetAllPlayers(ticketId, phoneNumber);

            Log.d(Tag, "before Code: " + phoneNumber + " " + ticketId);
            //todo change phone number

            call.enqueue(new Callback<MyResponse<List<PlayerModel>>>() {
                @Override
                public void onResponse(Call<MyResponse<List<PlayerModel>>> call, Response<MyResponse<List<PlayerModel>>> response) {

                    Log.d(Tag, "onResponse Code: " + response.code());
                    if (response.code() == 200) {
                        Log.d(Tag, "onResponse: ");
                        if (response.body().getResultCode() == 1) {
                            ////list was
                            for (int i = 0; i < response.body().getResults().size(); i++) {
                                PlayerModel model = new PlayerModel();
                                PlayerModel get = response.body().getResults().get(i);
                                Log.d(Tag, "onResponse: " + get.getFullName());
                                model.setFullName(get.getFullName());
                                model.setId(get.getId());
                                model.setAge(get.getAge());
                                model.setGenderId(get.getGenderId());
                                model.setTicketId(get.getTicketId());
                                model.setUserId(get.getUserId());
                                model.setPrice(get.getPrice());
                                ListChildren.add(model);

                                Log.d("0202", "onResponse: " + priceInt);

                            }


                            adapter = new ListPlayersAdapter(ListChildren, mContext);
                            LinearLayoutManager li = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                            recycle_list.setLayoutManager(li);
                            recycle_list.setAdapter(adapter);
                            Log.d("perrr", "onResponse: " + ListChildren.size());
                            getCost(ListChildren.size());


                        }
                    }
                }

                @Override
                public void onFailure(Call<MyResponse<List<PlayerModel>>> call, Throwable t) {

                }
            });

        } else {
            PublicMethods.showToast("اطلاعات برای ارسال کامل نیست");
        }

    }

 /*   private void getList() {
        list = Hawk.get("showPlayersList", null);
        if (list != null) {
            String name = null;

            Log.d("ddd", "getList: " + list.size());
            for (int i = 0; i < list.size(); i++) {
                if (i == 0)
                    user1.setText(list.get(i).getChild_fullName());
                if (i == 1)
                    user2.setText(list.get(i).getChild_fullName());
                if (i == 2)
                    user3.setText(list.get(i).getChild_fullName());
                if (i == 3)
                    user4.setText(list.get(i).getChild_fullName());
                if (i == 4)
                    user5.setText(list.get(i).getChild_fullName());
            }
        }
    }*/

    private void bind() {

        recycle_list = findViewById(R.id.recycle_list);
        percent = findViewById(R.id.percent);
        cost = findViewById(R.id.cost);
        btn_pay = findViewById(R.id.btn_pay);
        btn_home = findViewById(R.id.btn_home);
        btn_school = findViewById(R.id.btn_school);
        btn_about = findViewById(R.id.btn_about);
        btn_discount = findViewById(R.id.btn_discount);
        btn_pay = findViewById(R.id.btn_pay);

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

            case R.id.btn_pay:
                goZaripal();
                break;

            case R.id.btn_discount:
                getDiscount("1234####");
                /// TODO: 9/17/2018  change it
                break;
        }
    }

    private void goZaripal() {
        Log.d("12345", "goZaripal: ");
        ZarinPal purchase = ZarinPal.getPurchase(mContext);
        PaymentRequest payment = ZarinPal.getPaymentRequest();

        payment.setMerchantID("281e2438-f902-11e7-8d5e-005056a205be");
        payment.setAmount(100);
        payment.setDescription("گیم تایمینگ");
        payment.setCallbackURL("return://zarinpalpayment");

        purchase.startPayment(payment, new OnCallbackRequestPaymentListener() {
            @Override
            public void onCallbackResultPaymentRequest(int status, String authority, Uri paymentGatewayUri, Intent intent) {
                if (status == 100) {

                } else {
                    PublicMethods.showToast("خطا در ایجاد درخواست پرداخت");
                }
            }
        });

    }

    /*private void actionSend() {
        String getedt = cost.getText().toString();
        getedt = getedt.replaceAll(",", "");
        Retrofit client = GameTimingService.getClient(ID, Long.parseLong(getedt), new GameTimingInterface.Get_CreatePaymentOrder() {
            @SuppressLint("SetJavaScriptEnabled")
            @Override
            public void getResponse(Response<CreatePaymentOrder> Response) {
                if (Response.body() != null) {
                    CreatePaymentOrder createPaymentOrder = Response.body();
                    Log.d("", "getResponse: " + createPaymentOrder.toString());

                    WebView webview = findViewById(R.id.webview);
                    webview.setWebViewClient(new WebViewClient());
                    postWay0(Response.body().getBankRefrence(), Response.body().getMobileAppPaymentPage());

                    orderRefrence = createPaymentOrder.getBankRefrence();
                    ReferToPage = true;
                }
            }

            @Override
            public void error(String error) {
                Log.d(TAG, "error: " + error);
            }
        });
    }*/

   /* private void postWay0(String RefID, String AppPaymentUrl) {

        String url = AppPaymentUrl+"?orderUniqueId="+RefID;
        Log.d(TAG, "postWay0: "+url );
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }*/


    private void goSchool() {
        Intent intent = new Intent(DetailsEntertainmentActivity.this, SchoolActivity.class);
        startActivity(intent);
    }

    private void goAbout() {
        Intent intent = new Intent(DetailsEntertainmentActivity.this, AboutUsActivity.class);
        startActivity(intent);
    }

    private void goHome() {
        Intent intent = new Intent(DetailsEntertainmentActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void sendPosition(int position) {
        PlayerModel model = ListChildren.get(position);
        String Fullname = model.getFullName();
        int Age = model.getAge();
        int Gender = model.getGenderId();
        int id = model.getId();
        int userId = model.getUserId();
        int ticketid = model.getTicketId();

        Intent intent = new Intent(DetailsEntertainmentActivity.this, EditPlayerActivity.class);

        intent.putExtra("fullName", Fullname);
        intent.putExtra("age", Age);
        intent.putExtra("gender", Gender);
        intent.putExtra("id", id);
        intent.putExtra("userId", userId);
        intent.putExtra("TicketId", ticketid);
        startActivity(intent);


        Log.d("sendPosition", "Fullname: " + Fullname);
        Log.d("sendPosition", "Age: " + Age);
        Log.d("sendPosition", "Gender: " + Gender);
        Log.d("sendPosition", "id: " + id);
        Log.d("sendPosition", "userId: " + userId);
        Log.d("sendPosition", "ticketid: " + ticketid);
    }
}
