package ir.game_timing.schools;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import ir.game_timing.R;
import ir.game_timing.models.BaseModel;

public class PagerAdapter extends android.support.v4.view.PagerAdapter {
    Activity activity;
    List<BaseModel> images;
    Context mContext;
    LayoutInflater inflater;


   /* public PagerAdapter(Activity activity, List<String> images, Context mContext) {
        this.activity = activity;
        this.images = images;
        this.mContext = mContext;
    }
*/
    public PagerAdapter(Activity activity, List<BaseModel> images) {
        Log.d("schoollll", "PagerAdapter: "+images.size()+"");
        this.activity = activity;
        this.images = images;
    }



    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_schools, container, false);
        ImageView img_school;
        img_school = itemView.findViewById(R.id.img_school);
        DisplayMetrics dis = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dis);
        int height = dis.heightPixels;
        int width = dis.widthPixels;
        img_school.setMinimumHeight(height);
        img_school.setMinimumWidth(width);
        try {

            Glide.with(activity.getApplicationContext()).load(images.get(position).getPath()).placeholder(R.drawable.schools).into(img_school);



            Log.d("schoollll", "instantiateItem: "+images.get(position).toString());
        } catch (Exception ex) {

        }
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((View)object);
    }
}
