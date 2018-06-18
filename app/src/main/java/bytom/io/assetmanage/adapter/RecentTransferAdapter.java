package bytom.io.assetmanage.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import bytom.io.R;
import bytom.io.assetmanage.bean.TransferBean;

/**
 * Created by Nil on 2018/6/18
 */
public class RecentTransferAdapter extends RecyclerView.Adapter {
    private Context mContext;

    private List<TransferBean> mDataList;

    public RecentTransferAdapter(Context context) {
        this.mContext = context;
        mDataList = new ArrayList<>();
    }

    public void setData(List<TransferBean> data) {
        if (data != null) {
            mDataList.clear();
            this.mDataList.addAll(data);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.as_recent_transfer_item, parent, false);
        return new RecentTransferViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RecentTransferViewHolder vh = (RecentTransferViewHolder) holder;
        vh.bind(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class RecentTransferViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.as_transfer_type_icon) ImageView transferTypeIcon;
        @BindView(R.id.as_transfer_address) TextView transferAddress;
        @BindView(R.id.as_transfer_time) TextView transferTime;
        @BindView(R.id.as_number_of_transactions) TextView numberOfTransactions;
        @BindView(R.id.as_transfer_result) TextView transferResult;

        public RecentTransferViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }

        public void bind(TransferBean transferBean) {
            //Transfer type
            int transferType = transferBean.getTransferType();
            if (transferType == 0) {
                transferTypeIcon.setImageResource(R.drawable.arrow_down);
            } else {
                transferTypeIcon.setImageResource(R.drawable.arrow_up);
            }

            //Other
            transferAddress.setText(transferBean.getTransferAddress());
            transferTime.setText(transferBean.getTransferTime());
            numberOfTransactions.setText(transferBean.getNumberOfTransactions());

            //Transfer result
            boolean transferSuccess = transferBean.isTransferSuccess();
            if(!transferSuccess) {
                //failed
                transferResult.setText(R.string.as_management_transfer_failed);
            } else {
                transferResult.setVisibility(View.INVISIBLE);
            }
        }
    }
}
