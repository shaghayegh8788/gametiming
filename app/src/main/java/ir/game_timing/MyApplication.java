package ir.game_timing;

import android.app.Application;
import android.graphics.Typeface;

import com.orhanobut.hawk.Hawk;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

import static ir.game_timing.R.attr.fontPath;

public class MyApplication extends  Application{

    public static MyApplication applicationObject;
    public static Typeface normalTypeFace;


    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/ir_sans.ttf")
                .setFontAttrId(fontPath)
                .build()
        );
        applicationObject = this;
        Hawk.init(this).build();

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}

