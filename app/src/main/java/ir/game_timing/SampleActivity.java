package ir.game_timing;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class SampleActivity extends BaseActivity {
Button button4;
DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        drawer.closeDrawer(Gravity.END);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        drawer = findViewById(R.id.drawer);

    }
}
