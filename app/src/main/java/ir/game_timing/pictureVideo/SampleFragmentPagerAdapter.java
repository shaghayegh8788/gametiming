package ir.game_timing.pictureVideo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "ویدئوها", "عکس ها"};


    public SampleFragmentPagerAdapter(FragmentManager fm) {

        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0)
        {
           /* return VideoFragment.newInstance(position + 1);*/
            return VideoFragment.newInstance(position + 1);
        }
        else
        {
            return VideoFragment.newInstance(position + 1);
       /*     return PictureFragment.new(position + 1);*/
        }

    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}