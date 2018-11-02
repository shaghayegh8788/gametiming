package ir.game_timing.message;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.orhanobut.hawk.Hawk;

import java.util.List;

import ir.game_timing.BaseActivity;
import ir.game_timing.R;
import ir.game_timing.models.MessageModel;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ShowMessageActivity extends BaseActivity implements  MessageAdapter.CallBackInterface{
    RecyclerView recycle_message;
    List<MessageModel> messageModelsList;
    MessageAdapter adapter;
    MessageModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_message);
        recycle_message = findViewById(R.id.recycle_message);
        messageModelsList = Hawk.get("MessageList");
        Log.d("showww", "onCreateShowMessageActivity: " + messageModelsList.size() + "");

        if (messageModelsList.size() > 0) {
            for(MessageModel model : messageModelsList)
            {
                Log.d("showwwx", "size: "+messageModelsList.size());
                Log.d("showwwx", "title: "+model.getTiltle());
                Log.d("showwwx", "content: "+model.getContent());
            }
            Log.d("showww", "if: " + messageModelsList.size() + "");
            adapter = new MessageAdapter(mContext, messageModelsList);
            LinearLayoutManager li = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            recycle_message.setAdapter(adapter);
            recycle_message.setLayoutManager(li);

        }

    }

    @Override
    public void sendPosition(int position) {
        Log.d("gettt", "sendPosition: "+position+"");
        List<MessageModel> MessageList = Hawk.get("MessageList");
        model = MessageList.get(position);
        Log.d("gettt", "sendPosition2: "+model.getTiltle() +" "+model.getTiltle());
        Hawk.put("ID",model.getId()+"");
        Hawk.put("title",model.getTiltle());
        Hawk.put("content",model.getContent());
        dialog dia = new dialog(ShowMessageActivity.this);
        dia.show();
    }
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
