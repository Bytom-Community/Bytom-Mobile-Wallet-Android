package bytom.io.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import bytom.io.R;

/**
 * Created by DongFangZhou on 2018/6/22.
 */

public class HomeAssetAdapter extends RecyclerView.Adapter {
    private static final int TYPE_TOP = 0;
    private static final int TYPE_CONTENT = 1;
    private final Context mContext;
    private final List mList;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_TOP) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_asset_top, parent, false);
            return new HomeAssetTopHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_asset_content, parent, false);
            return new HomeAssetContentHolder(view);
        }
    }

    @Override
    public void onBindViewHolder (@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position == 0){
            HomeAssetTopHolder holder1 = (HomeAssetTopHolder) holder;

        }else{
            HomeAssetContentHolder holder1 = (HomeAssetContentHolder) holder;
        }
    }

    @Override
    public int getItemViewType (int position) {
        if (position == 0) {
            return TYPE_TOP;
        } else {
            return TYPE_CONTENT;
        }
    }

    @Override
    public int getItemCount () {
        return mList.size() + 1;
    }

    public HomeAssetAdapter (Context context, List list) {
        this.mContext = context;
        this.mList = list;
    }

    public class HomeAssetTopHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_home_asset_menu)
        ImageView mIvMenu;
        @BindView(R.id.tv_home_asset_total_asset)
        TextView mTvAsset;
        @BindView(R.id.tv_home_asset_money)
        TextView mTvMoney;
        @BindView(R.id.tv_home_asset_public_key)
        TextView mTvPublicKey;
        @BindView(R.id.iv_home_asset_receive)
        ImageView mIvReceive;
        @BindView(R.id.iv_home_asset_scan)
        ImageView mIvScan;
        @BindView(R.id.iv_home_asset_send)
        ImageView mIvSend;

        public HomeAssetTopHolder (View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class HomeAssetContentHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_home_asset_icon)
        ImageView mIvIcon;
        @BindView(R.id.tv_home_asset_name)
        TextView mTvName;
        @BindView(R.id.tv_home_asset_item_count)
        TextView mTvItemCount;
        @BindView(R.id.tv_home_asset_item_money)
        TextView mTvItemMoney;

        public HomeAssetContentHolder (View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
