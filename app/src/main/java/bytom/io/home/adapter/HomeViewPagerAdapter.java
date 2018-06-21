package bytom.io.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by DongFangZhou on 2018/6/21.
 */

public class HomeViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;

    public HomeViewPagerAdapter (FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem (int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount () {
        return mFragments.size();
    }
}
