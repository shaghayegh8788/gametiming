package ir.game_timing;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ToolbarActivity extends AppCompatActivity {
ImageView setting;
DrawerLayout drawer2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);
        setting = findViewById(R.id.setting);
        drawer2 = findViewById(R.id.drawer2);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.setting){
                    if(!drawer2.isDrawerOpen(Gravity.START)){
                        drawer2.openDrawer(Gravity.START);
                    }}
            }
        });



    }
}
