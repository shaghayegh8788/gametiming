package ir.game_timing.message;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;

import java.util.List;

import ir.game_timing.BaseActivity;
import ir.game_timing.MainActivity;
import ir.game_timing.PublicMethods;
import ir.game_timing.R;
import ir.game_timing.models.MessageModel;
import ir.game_timing.models.MyResponse;
import ir.game_timing.retrofit.GameTimingInterface;
import ir.game_timing.retrofit.GameTimingService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class dialog extends Dialog implements View.OnClickListener {
    public Activity c;
    public Dialog d;
    public Button gotIt, no;
MessageAdapter adapter;
    public dialog(Activity a) {
        super(a);
        this.c = a;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_message);
        TextView topicMessage, textMessage;
        Button back, noView;
        Context mContext;

        MessageAdapter adapter;
        topicMessage = findViewById(R.id.topicMessage);
        textMessage = findViewById(R.id.textMessage);
        back = findViewById(R.id.back);
        noView = findViewById(R.id.noView);
        back.setOnClickListener(this);
        noView.setOnClickListener(this);
        String title = Hawk.get("title");
        String content = Hawk.get("content");
        topicMessage.setText(title);
        textMessage.setText(content);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.back:
                dismiss();
                break;

            case R.id.noView:
                PublicMethods.showToast("لطفا منتظر بمانید ...");
                String ID = Hawk.get("ID");
                Log.d("diaaa", "onResponse: "+ID);
                GameTimingInterface gameTimingInterface = GameTimingService.getClient().create(GameTimingInterface.class);
                Call<MyResponse<MessageModel>> call = gameTimingInterface.SetMessage(ID);
                call.enqueue(new Callback<MyResponse<MessageModel>>() {
                    @Override
                    public void onResponse(Call<MyResponse<MessageModel>> call, Response<MyResponse<MessageModel>> response) {
                        if (response.code() == 200) {
                            if (response.body().getResultCode() == 1) {
                                MessageModel model = new MessageModel();
                                model.setId(response.body().getResults().getId());
                                model.setTiltle(response.body().getResults().getTiltle());
                                model.setContent(response.body().getResults().getContent());
                                int id = response.body().getResults().getId();
                                String title = response.body().getResults().getTiltle();
                                String content = response.body().getResults().getContent();
                                Log.d("diaaa", "onResponse: "+id+" "+title+" "+content);
                                PublicMethods.showToast("عملیات با موفقیت انجام شد.");
                                List<MessageModel> MessageList = Hawk.get("messageModelList");
                             MessageList.remove(model);



                                Intent intent = new Intent(getContext(),MainActivity.class);
                                getContext().startActivity(intent);

                                dismiss();
                            }
                        }
                    }



                    @Override
                    public void onFailure(Call<MyResponse<MessageModel>> call, Throwable t) {

                    }
                });

        }
        dismiss();
    }


}
