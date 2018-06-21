package bytom.io.home;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import bytom.io.R;
import bytom.io.home.adapter.HomeViewPagerAdapter;
import bytom.io.home.fragment.HomeAssetsFragment;
import bytom.io.home.fragment.HomeMineFragment;
import bytom.io.utils.StatusBarUtil;

public class HomeActivity extends AppCompatActivity implements BottomBar.OnTabChangeListener {

    @BindView(R.id.vp_home)
    HomeViewPager mVp;
    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;
    private List<Fragment> mFragments;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        StatusBarUtil.setStatusColor(this, Color.BLACK);
        initViewPager();
    }

    private void initViewPager () {
        mFragments = new ArrayList<>();
        mFragments.add(new HomeAssetsFragment());
        mFragments.add(new HomeMineFragment());
        mBottomBar.setOnTabChangeListener(this);
        HomeViewPagerAdapter mAdapter = new HomeViewPagerAdapter(getSupportFragmentManager(), mFragments);
        mVp.setAdapter(mAdapter);
        //设置缓存页面
        mVp.setOffscreenPageLimit(mFragments.size() - 1);
        //默认设置第一个Fragment
        mVp.setCurrentItem(0);
    }

    @Override
    public void onTabChanged (int position) {
        mVp.setCurrentItem(position);
    }
}
