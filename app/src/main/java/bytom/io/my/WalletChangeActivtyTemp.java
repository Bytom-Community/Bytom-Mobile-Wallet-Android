package bytom.io.my;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import bytom.io.R;

/**
 * <b>版权</b>：　　　比原链 版权所有(c) 2018 <br>
 * <b>作者</b>：　　　愤世嫉俗
 * <b>创建日期</b>：　18/6/25 <br>
 */


public class WalletChangeActivtyTemp extends Activity {

    private DrawerLayout mDrawerLayout;
    private LinearLayout mRightDraweLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_wallet_change_temp);

        initLayout();

        setOnClick();
    }

    private void initLayout() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        mRightDraweLayout = (LinearLayout) findViewById(R.id.main_right_drawer_layout);
        int width = getWindowManager().getDefaultDisplay().getWidth();
        DrawerLayout.LayoutParams params = new DrawerLayout.LayoutParams(width/2, ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.RIGHT;
        mRightDraweLayout.setLayoutParams(params);

        ((TextView)findViewById(R.id.tv_wallet_personal)).setSelected(true);
    }

    private void setOnClick() {
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(Gravity.RIGHT);
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED | Gravity.RIGHT);
            }
        });
    }

}
