package ir.game_timing.recyclerViewPlayers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import ir.game_timing.R;

import java.util.List;


import ir.game_timing.models.PlayerModel;

public class ListPlayersAdapter extends RecyclerView.Adapter<ListPlayersAdapter.ViewHolder> {


    List<PlayerModel> ListChildren;
    Context mContext;
    String TAG = "ListPlayersAdapterr";
    private CallBackInterface mCallBack;

    public interface CallBackInterface {
        void sendPosition(int position);
    }

    public ListPlayersAdapter(List<PlayerModel> listChildren, Context mContext) {
        Log.d(TAG, "ListPlayersAdapter: " + listChildren.size());
        ListChildren = listChildren;
        this.mContext = mContext;
        mCallBack = (CallBackInterface) mContext;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_fullName, txt_number, edit;


        public ViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "ViewHolder: ");
            txt_fullName = itemView.findViewById(R.id.txt_fullName);
            txt_number = itemView.findViewById(R.id.txt_number);
            edit = itemView.findViewById(R.id.edit);


            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   int position = getAdapterPosition();
                   mCallBack.sendPosition(position);

                }
            });


        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        View row = LayoutInflater.from(mContext).inflate(R.layout.item_show_players, parent, false);
        ViewHolder holder = new ViewHolder(row);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");
        PlayerModel model = ListChildren.get(position);
        String index = Integer.toString(position + 1);
        holder.txt_number.setText(index);
        holder.txt_fullName.setText(ListChildren.get(position).getFullName());
        Log.d(TAG, "fullname: " + ListChildren.get(position).getFullName());
        /*  holder.number.setText(position+1+"");*/

    }


    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + ListChildren.size());
        return ListChildren.size();
    }


}
