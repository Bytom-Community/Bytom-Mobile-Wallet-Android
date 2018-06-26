package bytom.io.home;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import bytom.io.R;
import bytom.io.home.adapter.HomeViewPagerAdapter;
import bytom.io.home.fragment.HomeAssetsFragment;
import bytom.io.home.fragment.HomeMineFragment;
import bytom.io.utils.StatusBarUtil;

public class HomeActivity extends AppCompatActivity implements BottomBar.OnTabChangeListener, HomeAssetsFragment.OnMenuClickListener{

    @BindView(R.id.vp_home)
    HomeViewPager mVp;
    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;
    private List<Fragment> mFragments;

    @BindView(R.id.main_right_drawer_layout)
    RightDrawerView mRighDrawerView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        StatusBarUtil.setStatusColor(this, Color.BLACK);
        initViewPager();
        initDrawerLayout();
    }

    private void initViewPager() {
        mFragments = new ArrayList<>();
        HomeAssetsFragment homeAssetsFragment = new HomeAssetsFragment();
        homeAssetsFragment.setMenuListener(this);
        mFragments.add(homeAssetsFragment);
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
    public void onTabChanged(int position) {
        mVp.setCurrentItem(position);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != mRighDrawerView)
            mRighDrawerView.onDestroy();
    }

    private void initDrawerLayout() {
        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        mRighDrawerView.setDrawerClickListener(onDrawerClickListener);
    }

    private void onDrawer() {
        mDrawerLayout.openDrawer(Gravity.RIGHT);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED | Gravity.RIGHT);
    }

    @Override
    public void onMenuClick() {
        onDrawer();
    }

    RightDrawerView.OnDrawerClickListener onDrawerClickListener = new RightDrawerView.OnDrawerClickListener() {
        @Override
        public void onPersonalClick() {
            mDrawerLayout.closeDrawer(Gravity.RIGHT);
            Toast.makeText(HomeActivity.this, "私房钱", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onVaultClick() {
            mDrawerLayout.closeDrawer(Gravity.RIGHT);
            Toast.makeText(HomeActivity.this, "小金库", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onLittleClick() {
            mDrawerLayout.closeDrawer(Gravity.RIGHT);
            Toast.makeText(HomeActivity.this, "零钱包钱", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCreateClick() {
            mDrawerLayout.closeDrawer(Gravity.RIGHT);
            Toast.makeText(HomeActivity.this, "创建钱包", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onImportClick() {
            mDrawerLayout.closeDrawer(Gravity.RIGHT);
            Toast.makeText(HomeActivity.this, "导入钱包", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancelDrawer() {
            mDrawerLayout.closeDrawer(Gravity.RIGHT);
        }
    };
}
