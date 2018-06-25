package bytom.io.home.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import bytom.io.R;
import bytom.io.home.adapter.HomeMyAdapter;

/**
 * Created by DongFangZhou on 2018/6/21.
 */

public class HomeMineFragment extends Fragment implements HomeMyAdapter.OnItemClickListener {
    @BindView(R.id.rl_home_mine_wallet)
    RelativeLayout mRlWallet;
    @BindView(R.id.rl_home_mine_transaction_record)
    RelativeLayout mRlTransactionRecord;
    @BindView(R.id.rv_home_mine)
    RecyclerView mRv;
    Unbinder unbinder;

    //列表数据
    private int[] item_img = {R.mipmap.flow_branch,R.mipmap.path18,R.mipmap.forward,R.mipmap.info_with_circle};
    private int[] item_text_left = {R.string.home_mine_node_left, R.string.home_mine_currency_left, R.string.home_mine_share_left, R.string.home_mine_update_left};
    private int[] item_text_right = {R.string.home_mine_node_right, R.string.home_mine_currency_right, R.string.home_mine_share_right, R.string.home_mine_update_right};

    @Nullable
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init () {
        mRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mRv.setAdapter(new HomeMyAdapter(getContext(),item_img,item_text_left,item_text_right,this));
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick (int position) {
        switch (position){
            case 0:
                //节点
                Toast.makeText(getContext(), "节点", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                //货币
                Toast.makeText(getContext(), "货币", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                //分享
                Toast.makeText(getContext(), "分享", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                //关于
                Toast.makeText(getContext(), "关于", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
