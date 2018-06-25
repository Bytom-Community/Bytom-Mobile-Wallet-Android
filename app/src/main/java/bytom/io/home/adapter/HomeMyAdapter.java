package bytom.io.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bytom.io.R;
import bytom.io.home.holder.HomeMyHolder;

/**
 * Created by DongFangZhou on 2018/6/25.
 */

public class HomeMyAdapter extends RecyclerView.Adapter<HomeMyHolder> {
    private Context mContext;
    private int[] tvs_left;
    private int[] tvs_right;
    private OnItemClickListener mListener;
    private int[] ivs;

    @NonNull
    @Override
    public HomeMyHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_mine, parent, false);
        return new HomeMyHolder(view);
    }

    @Override
    public void onBindViewHolder (@NonNull HomeMyHolder holder, final int position) {
        holder.mIvLeft.setImageResource(ivs[position]);
        holder.mTvLeft.setText(tvs_left[position]);
        holder.mTvRight.setText(tvs_right[position]);
        holder.mRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if (mListener != null){
                    mListener.onClick(position);
                }
            }
        });
    }


    @Override
    public int getItemCount () {
        return ivs.length;
    }

    public HomeMyAdapter (Context context,int[] ivs,int[] tvs_left,int[] tvs_right,OnItemClickListener listener) {
        this.mContext = context;
        this.ivs= ivs;
        this.tvs_left= tvs_left;
        this.tvs_right= tvs_right;
        this.mListener = listener;
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

}
