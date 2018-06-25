package bytom.io.my.transaction.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import bytom.io.R;

/**
 * <b>版权</b>：　　　比原链 版权所有(c) 2018 <br>
 * <b>作者</b>：　　　愤世嫉俗
 * <b>创建日期</b>：　18/6/11 <br>
 */


public class TransactionDetailActivity extends Activity{

    @BindView(R.id.tv_num)
    protected TextView mNumView;
    @BindView(R.id.tv_status)
    protected TextView mStatusView;
    @BindView(R.id.tv_receive)
    protected TextView mReceiveView;
    @BindView(R.id.tv_fee)
    protected TextView mFeeView;
    @BindView(R.id.tv_ps)
    protected TextView mPsView;
    @BindView(R.id.tv_trans_num)
    protected TextView mTransNumView;
    @BindView(R.id.tv_time)
    protected TextView mTimeView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_transaction_detail);

        init();
    }

    private void init() {

        ((TextView)findViewById(R.id.tv_title)).setText(getString(R.string.trans_detail));
        findViewById(R.id.iv_left).setOnClickListener(listener);

    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };
}
