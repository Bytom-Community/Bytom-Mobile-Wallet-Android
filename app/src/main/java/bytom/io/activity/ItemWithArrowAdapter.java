package bytom.io.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import bytom.io.R;

public class ItemWithArrowAdapter extends
        RecyclerView.Adapter<ItemWithArrowAdapter.ItemWithArrowHolder> {

    private final Context mContext;
    private final String[] mTv1;
    private String[] mTv2;
    private final int[] mIv;
    private OnItemClickListener mListener;

    public ItemWithArrowAdapter(
            Context context,
            String[] tv1,
            @Nullable String[] tv2,
            @Nullable int[] iv,
            OnItemClickListener listener) {
        mContext = context;
        mTv1 = tv1;
        mTv2 = tv2;
        mIv = iv;
        mListener = listener;
    }

    @NonNull
    @Override
    public ItemWithArrowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        return new ItemWithArrowHolder(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemWithArrowHolder holder, final int position) {
        holder.text1.setText(mTv1[position]);
        if (mTv2 != null && mTv2[position] != null) {
            holder.text2.setText(mTv2[position]);
        }
        if (mIv != null && mIv[position] != 0) {
            holder.imageView.setImageResource(mIv[position]);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTv1.length;
    }

    public void setExtraItem(String[] extraItem) {
        this.mTv2 = extraItem;
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public class ItemWithArrowHolder extends RecyclerView.ViewHolder {
        private final TextView text1;
        private final TextView text2;
        private final ImageView imageView;

        public ItemWithArrowHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_with_arrow, parent, false));
            text1 = itemView.findViewById(R.id.textView1);
            text2 = itemView.findViewById(R.id.textView2);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
