package bytom.io.home;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import bytom.io.R;

/**
 * <b>版权</b>：　　　比原链 版权所有(c) 2018 <br>
 * <b>作者</b>：　　　愤世嫉俗
 * <b>创建日期</b>：　18/6/25 <br>
 */


public class RightDrawerView extends LinearLayout {

    @BindView(R.id.ll_drawer_layout)
    LinearLayout mRightDrawerLayout;
    @BindView(R.id.tv_wallet_personal)
    TextView mPersonalView;
    @BindView(R.id.tv_wallet_vault)
    TextView mVaultView;
    @BindView(R.id.tv_wallet_little)
    TextView mLittleView;
    @BindView(R.id.tv_wallet_create)
    TextView mCreateView;
    @BindView(R.id.tv_wallet_import)
    TextView mImportView;
    private OnDrawerClickListener mListener;
    private Unbinder unbinder;

    public RightDrawerView(Context context) {
        super(context);
        init(context);
    }

    public RightDrawerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RightDrawerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.vw_right_drawer, this);
        unbinder = ButterKnife.bind(this);
        int width = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width / 2, ViewGroup.LayoutParams.MATCH_PARENT);
        mRightDrawerLayout.setLayoutParams(params);
        mPersonalView.setSelected(true);
    }

    public void onDestroy() {
        if (null != unbinder)
            unbinder.unbind();
    }

    @OnClick({R.id.tv_wallet_personal, R.id.tv_wallet_vault
            , R.id.tv_wallet_little, R.id.tv_wallet_create
            , R.id.tv_wallet_import})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_wallet_personal:
                if (!mPersonalView.isSelected())
                    onSelectView(0);
                else
                    mListener.onCancelDrawer();
                break;
            case R.id.tv_wallet_vault:
                if (!mVaultView.isSelected())
                    onSelectView(1);
                else
                    mListener.onCancelDrawer();
                break;
            case R.id.tv_wallet_little:
                if (!mLittleView.isSelected())
                    onSelectView(2);
                else
                    mListener.onCancelDrawer();
                break;
            case R.id.tv_wallet_create:
                if (!mCreateView.isSelected())
                    onSelectView(3);
                else
                    mListener.onCancelDrawer();
                break;
            case R.id.tv_wallet_import:
                if (!mImportView.isSelected())
                    onSelectView(4);
                else
                    mListener.onCancelDrawer();
                break;
        }

    }

    private void onSelectView(int pos) {
        switch (pos) {
            case 0:
                mPersonalView.setSelected(true);
                mVaultView.setSelected(false);
                mLittleView.setSelected(false);
                mCreateView.setSelected(false);
                mImportView.setSelected(false);
                mListener.onPersonalClick();
                break;
            case 1:
                mPersonalView.setSelected(false);
                mVaultView.setSelected(true);
                mLittleView.setSelected(false);
                mCreateView.setSelected(false);
                mImportView.setSelected(false);
                mListener.onVaultClick();
                break;
            case 2:
                mPersonalView.setSelected(false);
                mVaultView.setSelected(false);
                mLittleView.setSelected(true);
                mCreateView.setSelected(false);
                mImportView.setSelected(false);
                mListener.onLittleClick();
                break;
            case 3:
                mPersonalView.setSelected(false);
                mVaultView.setSelected(false);
                mLittleView.setSelected(false);
                mCreateView.setSelected(true);
                mImportView.setSelected(false);
                mListener.onCreateClick();
                break;
            case 4:
                mPersonalView.setSelected(false);
                mVaultView.setSelected(false);
                mLittleView.setSelected(false);
                mCreateView.setSelected(false);
                mImportView.setSelected(true);
                mListener.onImportClick();
                break;
        }
    }


    public void setDrawerClickListener(OnDrawerClickListener listener) {
        this.mListener = listener;
    }

    public interface OnDrawerClickListener {
        void onPersonalClick(); //私房钱

        void onVaultClick();    //小金库

        void onLittleClick();   //零钱包

        void onCreateClick();   //创建钱包

        void onImportClick();   //导入钱包

        void onCancelDrawer();  //关闭侧边栏
    }

}
