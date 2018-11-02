package ir.game_timing;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ir.game_timing.recyclerViewDay.RecyclerDayAdapter;
import ir.game_timing.recyclerViewDay.SelectDayActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TicketReservationActivity extends BaseActivity implements View.OnClickListener {
Button btn_read,btn_home,btn_about,btn_school;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_reservation);
        btn_read = findViewById(R.id.btn_read);
        btn_home = findViewById(R.id.btn_home);
        btn_school = findViewById(R.id.btn_school);
        btn_about = findViewById(R.id.btn_about);
        btn_read.setOnClickListener(this);
        btn_home.setOnClickListener(this);
        btn_about.setOnClickListener(this);
        btn_school.setOnClickListener(this);
    }
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btn_read:
                readContent();
                break;

            case  R.id.btn_home:
                goHome();
                break;

            case  R.id.btn_about:
                goAbout();
                break;

            case  R.id.btn_school:
                goSchool();
                break;

        }

    }

    private void goSchool() {
        Intent intent = new Intent(TicketReservationActivity.this,SelectDayActivity.class);
        startActivity(intent);
    }

    private void goAbout() {
        Intent intent = new Intent(TicketReservationActivity.this,AboutUsActivity.class);
        startActivity(intent);
    }

    private void goHome() {
        Intent intent = new Intent(TicketReservationActivity.this,HomeActivity.class);
        startActivity(intent);
    }

    private void readContent() {
        Intent intent = new Intent(TicketReservationActivity.this,SelectDayActivity.class);
        startActivity(intent);
    }
}
