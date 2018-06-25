package bytom.io.home.holder;

/**
 * Created by DongFangZhou on 2018/6/25.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import bytom.io.R;

public class HomeMyHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.iv_left)
    public ImageView mIvLeft;
    @BindView(R.id.tv_left)
    public TextView mTvLeft;
    @BindView(R.id.tv_right)
    public TextView mTvRight;
    @BindView(R.id.rl_home_mine_item)
    public RelativeLayout mRl;


    public HomeMyHolder (View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
