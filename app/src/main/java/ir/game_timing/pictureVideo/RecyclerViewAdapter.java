package ir.game_timing.pictureVideo;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import ir.game_timing.R;
import ir.game_timing.models.BaseModel;
import ir.game_timing.models.picModel;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context mContext;
    List<BaseModel> lstPic;
    Dialog myDialog;

    public RecyclerViewAdapter(Context mContext, List<BaseModel> lstPic) {
        Log.d("resss", "RecyclerViewAdapter: "+lstPic.size());
        this.mContext = mContext;
        this.lstPic = lstPic;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_picture, parent, false);
        final MyViewHolder holder = new MyViewHolder(v);

        myDialog = new Dialog(mContext);
        myDialog.setContentView(R.layout.dialog_pic);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageView dialog_img = myDialog.findViewById(R.id.dialog_img);

                Glide.with(mContext).load(lstPic.get(holder.getAdapterPosition()).getPath()).placeholder(R.drawable.ic_camera).into(dialog_img);
                myDialog.show();
            }
        });
        return holder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        /*       holder.desc.setText(lstPic.get(position).getName());*/
        Glide.with(mContext).load(lstPic.get(position).getPath()).placeholder(R.drawable.ic_camera).into(holder.img);
        Log.d("resss", "onBindViewHolder: "+lstPic.get(position).getPath());

    }


    @Override
    public int getItemCount() {
        return lstPic.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        /*       TextView desc;*/
        ImageView img;
        CardView card;

        public MyViewHolder(View itemView) {
            super(itemView);
            /*    desc = itemView.findViewById(R.id.desc);*/
            img = itemView.findViewById(R.id.img);
            card = itemView.findViewById(R.id.card);
        }
    }
}
