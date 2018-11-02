package ir.game_timing.recyclerViewDay;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ir.game_timing.R;

public class RecyclerDayAdapter extends RecyclerView.Adapter<RecyclerDayAdapter.ViewHolder> {
    List<DayModel> DayList;
    Context mContext;
    private CallBackInterface mCallback;
 private final int layout1 = 0;
 private final int layout2 = 1;


    public interface CallBackInterface {
        void sendModel(int position);

    }

    @Override
    public int getItemViewType(int position) {


        if (position % 2 == 0 || position == 0 )
        {
            Log.d("54123", "layout1: ");
            return layout1;
        }
        else
        {    Log.d("54123", "layout2: ");
            return  layout2;
        }

    }

    public RecyclerDayAdapter(List<DayModel> dayList, Context mContext) {
        DayList = dayList;
        Log.d("dayy", "RecyclerDayAdapter: " + DayList.size());

        this.mContext = mContext;
        mCallback = (CallBackInterface) mContext;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView placeName, placeAddress, stringDay, dayWeek, dateTime;
        LinearLayout WholeDay;

        public ViewHolder(View itemView) {
            super(itemView);
            placeName = itemView.findViewById(R.id.placeName);
            placeAddress = itemView.findViewById(R.id.placeAddress);
            stringDay = itemView.findViewById(R.id.stringDay);
            dayWeek = itemView.findViewById(R.id.dayWeek);
            dateTime = itemView.findViewById(R.id.dateTime);
            WholeDay = itemView.findViewById(R.id.WholeDay);


            WholeDay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Log.d("modell", "onClick: " + position);
                    mCallback.sendModel(position);
                }
            });

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
View row;
ViewHolder holder = null;
        Log.d("54123", "onCreateViewHolder: "+viewType+"");
        if(viewType == layout1)
        {
          row = LayoutInflater.from(mContext).inflate(R.layout.item_day_selected_yellow, parent, false);
             holder = new ViewHolder(row);
        }

        else if ((viewType == layout2))
        {
            row = LayoutInflater.from(mContext).inflate(R.layout.item_day_selected_yellow2, parent, false);
             holder = new ViewHolder(row);
        }

        return  holder;


    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.dateTime.setText(DayList.get(position).getPersianDate());
        holder.dayWeek.setText(DayList.get(position).getDayOfWeek());
        holder.stringDay.setText(DayList.get(position).getDay());
        holder.placeAddress.setText(DayList.get(position).getPlaceAddress());
        holder.placeName.setText(DayList.get(position).getPlaceName());

    }

    @Override
    public int getItemCount() {
        return DayList.size();
    }

}
