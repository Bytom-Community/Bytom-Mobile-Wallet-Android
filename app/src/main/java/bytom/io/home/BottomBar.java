package bytom.io.home;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import bytom.io.R;

/**
 * Created by DongFangZhou on 2018/6/21.
 */

public class BottomBar extends LinearLayout {
    @BindView(R.id.iv_nav_0)
    ImageView mIvNav0;
    @BindView(R.id.tv_nav_0)
    TextView mTvNav0;
    @BindView(R.id.rl_nav_0)
    RelativeLayout mRlNav0;
    @BindView(R.id.iv_nav_1)
    ImageView mIvNav1;
    @BindView(R.id.tv_nav_1)
    TextView mTvNav1;
    @BindView(R.id.rl_nav_1)
    RelativeLayout mRlNav1;
    @BindView(R.id.bottomNav)
    LinearLayout mBottomNav;
    private int mPage = 0;
    private OnTabChangeListener mListener;

    public BottomBar (Context context) {
        this(context, null);
    }

    public BottomBar (Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomBar (Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init () {
        LayoutInflater.from(getContext()).inflate(R.layout.item_home_navigation_bar, this);
        ButterKnife.bind(this);
        selectTab(0);
    }

    @OnClick({R.id.rl_nav_0, R.id.rl_nav_1})
    public void onClick (View view) {
        switch (view.getId()) {
            case R.id.rl_nav_0:
                if (mPage != 0){
                    selectTab(0);
                }
                break;
            case R.id.rl_nav_1:
                if (mPage != 1){
                    selectTab(1);
                }
                break;
        }

    }
    public void selectTab (int position) {
        mPage = position;
        View[] iv = {mIvNav0, mIvNav1};
        View[] tv = {mTvNav0, mTvNav1};
        for (int i = 0; i < iv.length; i++) {
            if (position == i) {
                iv[i].setSelected(true);
                tv[i].setSelected(true);
            } else {
                iv[i].setSelected(false);
                tv[i].setSelected(false);
            }
        }
        if (mListener != null){
            mListener.onTabChanged(position);
        }
    }
    public void setOnTabChangeListener (OnTabChangeListener listener) {
        this.mListener = listener;
    }

    interface OnTabChangeListener {
        void onTabChanged (int position);
    }
}
