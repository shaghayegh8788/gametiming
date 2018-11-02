package ir.game_timing;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import ir.game_timing.LoginActivity;
import ir.game_timing.R;
import ir.game_timing.TicketReservationActivity;
import ir.game_timing.pictureVideo.PicActivity;


public class HomeFragment extends Fragment {
    View view;
    RelativeLayout stepA, stepB, stepC, stepD;

    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        bind();
        stepA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TicketReservationActivity.class);
                startActivity(intent);
            }
        });

        stepB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PicActivity.class);
                startActivity(intent);
            }
        });


        return view;

    }

    public void bind() {
        stepA = view.findViewById(R.id.stepA);
        stepB = view.findViewById(R.id.stepB);
        stepC = view.findViewById(R.id.stepC);
        stepD = view.findViewById(R.id.stepD);
    }


}
