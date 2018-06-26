package bytom.io.my.transaction.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import bytom.io.R;

/**
 * <b>版权</b>：　　　比原链 版权所有(c) 2018 <br>
 * <b>作者</b>：　　　愤世嫉俗
 * <b>创建日期</b>：　18/6/11 <br>
 */


public class TransactionDetailActivity extends Activity {

    @BindView(R.id.iv_left)
    ImageView mBackBtn;
    @BindView(R.id.tv_title)
    TextView mTitleView;
    @BindView(R.id.tv_num)
    TextView mNumView;
    @BindView(R.id.tv_status)
    TextView mStatusView;
    @BindView(R.id.tv_receive)
    TextView mReceiveView;
    @BindView(R.id.tv_fee)
    TextView mFeeView;
    @BindView(R.id.tv_ps)
    TextView mPsView;
    @BindView(R.id.tv_trans_num)
    TextView mTransNumView;
    @BindView(R.id.tv_time)
    TextView mTimeView;
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_transaction_detail);

        init();
    }

    private void init() {
        unbinder = ButterKnife.bind(this);
        mTitleView.setText(getString(R.string.trans_detail));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != unbinder)
            unbinder.unbind();
    }

    @OnClick({R.id.iv_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
        }
    }
}
