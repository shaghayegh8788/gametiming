package ir.game_timing.message;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ir.game_timing.R;
import ir.game_timing.models.MessageModel;
import ir.game_timing.recyclerViewChildren.RecyclerRegisterAdapter;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    Context mContext;
    List<MessageModel> MessageList;
    private CallBackInterface mCallBack;

    public interface CallBackInterface {

        void sendPosition(int position);
    }

    public MessageAdapter(Context mContext, List<MessageModel> messageList) {
        this.mContext = mContext;
        MessageList = messageList;
        mCallBack = (CallBackInterface) mContext;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tilte, content;
        Button btn_view;

        public ViewHolder(View itemView) {
            super(itemView);
            Log.d("siii", "ViewHolder: ");
            tilte = itemView.findViewById(R.id.titleMessage);
            content = itemView.findViewById(R.id.contentMessage);
            btn_view = itemView.findViewById(R.id._btn_view);

            btn_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("gettt", "onClick: "+getAdapterPosition()+"");
                    mCallBack.sendPosition(getAdapterPosition());

                }
            });

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View row = LayoutInflater.from(mContext).inflate(R.layout.item_message, parent, false);
        Log.d("showw", "onCreateViewHolder: " + row.toString());
        ViewHolder holder = new ViewHolder(row);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MessageModel model = (MessageModel) MessageList.get(position);
        Log.d("showw", "onBindViewHolder: ");
        holder.tilte.setText("*"+" "+model.getTiltle()+" "+"*");
        holder.content.setText(model.getContent());

    }


    @Override
    public int getItemCount() {
        return MessageList.size();
    }


}
