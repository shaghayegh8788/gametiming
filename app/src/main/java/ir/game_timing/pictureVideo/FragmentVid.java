package ir.game_timing.pictureVideo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;

import ir.game_timing.R;

public class FragmentVid extends Fragment {
View view;
    public FragmentVid() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_vide,container,false);
        return view;
    }
}
