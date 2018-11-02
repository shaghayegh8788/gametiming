package ir.game_timing;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {



    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     /*   DrawerLayout mDrawerLayout = getActivity().findViewById(R.id.drawer);
        ((SampleActivity)getActivity()).openDrawer();*/
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

}
