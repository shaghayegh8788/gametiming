package ir.game_timing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    protected boolean _active = true;
    protected int _splashTime = 6000;
    String user_mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        user_mobile = PublicMethods.getValue("user_mobile", "");
        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (_active && (waited < _splashTime)) {
                        sleep(100);
                        if (_active) {
                            waited += 100;
                        }
                    }
                } catch (Exception e) {

                } finally {
                   if(!PublicMethods.getValue("user_mobile","").equals(""))
                   {
                       startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                       finish();
                   }
                   else
                   {
                       startActivity(new Intent(SplashActivity.this, AfterActivity.class));
                       finish();
                   }
                }
            }
        };

        splashTread.start();
    }
}
