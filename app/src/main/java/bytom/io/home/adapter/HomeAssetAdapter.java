package bytom.io.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import bytom.io.R;
import bytom.io.common.AssetsMaps;
import bytom.io.home.bean.HomeAssetsBean;

/**
 * Created by DongFangZhou on 2018/6/22.
 */

public class HomeAssetAdapter extends RecyclerView.Adapter {
    private static final int TYPE_TOP = 0;
    private static final int TYPE_CONTENT = 1;
    private final Context mContext;
    private final List<HomeAssetsBean.AssetsBean> mList;
    private final OnAssetClickListener mListener;

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
    public void onBindViewHolder (@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (position == 0){
            HomeAssetTopHolder holder1 = (HomeAssetTopHolder) holder;
            if (mListener != null){
                holder1.mIvMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick (View v) {
                        mListener.onMenuClick();
                    }
                });
                holder1.mIvReceive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick (View v) {
                        mListener.onReciveClick();
                    }
                });
                holder1.mIvScan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick (View v) {
                        mListener.onScanClick();
                    }
                });
                holder1.mIvSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick (View v) {
                        mListener.onSendClick();
                    }
                });
            }

        }else{
            HomeAssetContentHolder holder1 = (HomeAssetContentHolder) holder;
            holder1.mRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v) {
                    if (mListener != null){
                        mListener.onItemClick(position -1);
                    }
                }
            });
            HomeAssetsBean.AssetsBean bean = mList.get(position - 1);
            holder1.mIvIcon.setImageResource(AssetsMaps.getAssetsIcon(bean.getAssetID()));
            float amount = Float.parseFloat(bean.getAmount()) / 100000000;
            holder1.mTvItemCount.setText(amount+"");
            holder1.mTvName.setText(AssetsMaps.getAssetsName(bean.getAssetID()));
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

    public HomeAssetAdapter (Context context, List<HomeAssetsBean.AssetsBean> list, OnAssetClickListener listener) {
        this.mContext = context;
        if (list != null){
            this.mList = list;
        }else{
            mList = new ArrayList();
        }
        this.mListener = listener;
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
        @BindView(R.id.rl_home_asset_item)
        RelativeLayout mRl;

        public HomeAssetContentHolder (View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnAssetClickListener{
        public void onMenuClick();
        public void onSendClick();
        public void onReciveClick ();
        public void onScanClick();
        public void onItemClick(int position);
    }
}
