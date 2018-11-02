package ir.game_timing.customViews;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;

public class MyImageView extends AppCompatImageView {
    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void load(Context mContext, String url) {
        Glide.with(mContext).load(url).into(this);
    }

    public void load(Context mContext, Bitmap bitmap) {
        Glide.with(mContext).load(bitmap).into(this);
    }
    public void load(Context mContext, int drawable) {
        Glide.with(mContext).load(drawable).into(this);
    }
}
