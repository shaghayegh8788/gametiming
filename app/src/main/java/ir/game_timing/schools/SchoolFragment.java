package ir.game_timing.schools;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ir.game_timing.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SchoolFragment extends Fragment {
    View v;
    List<String> lstString;
    ViewPager viewPager_school;
    Activity activity;

    public SchoolFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        lstString = new ArrayList<>();
        lstString.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT_PYQ1TUVX4hNzX_JF_KAzOOvXiphyyds2z-ItiGppDH-HpfQAWw");
        lstString.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRBO_2HWyq7wlL01PxYeqh9A2hecS6FEpKZ_t1EOtaYi0jmFnwe");
        lstString.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS-dKKm2OXc5u55BNm-N3AzmpazQQ_jF7S4HNt14RYzu7BayC5uIw");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("schoollll", "size: " + lstString.size());
        v = inflater.inflate(R.layout.fragment_school, container, false);
        viewPager_school = v.findViewById(R.id.viewPager_schoolss);
   /*     PagerAdapter adapter = new PagerAdapter(getActivity(), lstString);
        viewPager_school.setAdapter(adapter);*/

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimeTask(), 2000, 4000);
        return v;
    }

    public static SchoolFragment newInstance() {

        Bundle args = new Bundle();

        SchoolFragment fragment = new SchoolFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public class MyTimeTask extends TimerTask {

        @Override
        public void run() {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager_school.getCurrentItem() == 0) {
                        viewPager_school.setCurrentItem(1);
                    } else if (viewPager_school.getCurrentItem() == 1) {
                        viewPager_school.setCurrentItem(2);
                    } else {
                        viewPager_school.setCurrentItem(0);
                    }
                }
            });
        }
    }

}
