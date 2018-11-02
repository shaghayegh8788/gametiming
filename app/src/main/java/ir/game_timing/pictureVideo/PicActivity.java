package ir.game_timing.pictureVideo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import ir.game_timing.AboutUsActivity;
import ir.game_timing.BaseActivity;
import ir.game_timing.HomeActivity;
import ir.game_timing.R;
import ir.game_timing.schools.SchoolActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class PicActivity extends BaseActivity implements View.OnClickListener {
    ViewPager viewPager;
    TabLayout tabIn;
    ViewPagerAdapter adapter;
    Button btn_home,btn_school,btn_about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic);
        viewPager = findViewById(R.id.viewPager);
        tabIn = findViewById(R.id.tabIn);
        btn_home = findViewById(R.id.btn_home);
        btn_school = findViewById(R.id.btn_school);
        btn_about = findViewById(R.id.btn_about);
        btn_home.setOnClickListener(this);
        btn_about.setOnClickListener(this);
        btn_school.setOnClickListener(this);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new FragmentPi(),"");
        adapter.AddFragment(new VideoFragment(),"");
        viewPager.setAdapter(adapter);
        tabIn.setupWithViewPager(viewPager);

        tabIn.getTabAt(0).setIcon(R.drawable.ic_camera_alt_black_24dp);
        tabIn.getTabAt(1).setIcon(R.drawable.ic_ondemand_video_black_24dp);

    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {

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
        Intent intent = new Intent(PicActivity.this,SchoolActivity.class);
        startActivity(intent);
    }

    private void goAbout() {
        Intent intent = new Intent(PicActivity.this,AboutUsActivity.class);
        startActivity(intent);
    }

    private void goHome() {
        Intent intent = new Intent(PicActivity.this,HomeActivity.class);
        startActivity(intent);
    }

}
