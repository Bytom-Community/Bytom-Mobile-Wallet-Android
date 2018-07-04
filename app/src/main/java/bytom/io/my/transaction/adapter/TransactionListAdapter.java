package bytom.io.my.transaction.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import bytom.io.R;
import bytom.io.entity.transaction.TransListGroupEntity;
import bytom.io.my.transaction.activity.TransactionDetailActivity;

/**
 * <b>版权</b>：　　　比原链 版权所有(c) 2018 <br>
 * <b>作者</b>：　　　愤世嫉俗
 * <b>创建日期</b>：　18/6/12 <br>
 */


public class TransactionListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private ArrayList<TransListGroupEntity> mGroupList;

    public TransactionListAdapter(Context context) {
        mContext = context;
    }

    public void setData(ArrayList<TransListGroupEntity> groupList) {
        mGroupList = groupList;
    }


    @Override
    public int getGroupCount() {
        if (null != mGroupList)
            return mGroupList.size();
        return 0;
    }

    @Override
    public int getChildrenCount(int i) {
        if(null != mGroupList && null != mGroupList.get(i) && null != mGroupList.get(i).getChildList()) {
           return mGroupList.get(i).getChildList().size();
        }


        return 0;
    }

    @Override
    public Object getGroup(int i) {
        if (null != mGroupList && i < mGroupList.size())
            mGroupList.get(i);
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        if(null != mGroupList && null != mGroupList.get(i) && null != mGroupList.get(i).getChildList()) {
            return mGroupList.get(i).getChildList().get(i1);
        }

        return null;
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public View getGroupView(int i, boolean b, View convertView, ViewGroup viewGroup) {
        ViewHolderGroup groupHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_trans_group, null);
            groupHolder = new ViewHolderGroup();
            groupHolder.textView = (TextView) convertView.findViewById(R.id.tv_time);
            groupHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_arrow);
            groupHolder.lineView = (TextView) convertView.findViewById(R.id.tv_line);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (ViewHolderGroup) convertView.getTag();
        }
        if (null != mGroupList.get(i))
            groupHolder.textView.setText(mGroupList.get(i).getTime());

        if (b) {
            groupHolder.imageView.setImageResource(R.mipmap.arrow_open);
            groupHolder.lineView.setVisibility(View.GONE);
        } else {
            groupHolder.imageView.setImageResource(R.mipmap.arrow_right);
            groupHolder.lineView.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    @Override
    public View getChildView(final int i, final int i1, boolean b, View convertView, ViewGroup viewGroup) {
        ViewHolderChild childHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_trans_child, null);
            childHolder = new ViewHolderChild();
            childHolder.childLayout = (RelativeLayout) convertView.findViewById(R.id.rl_layout);
            childHolder.lineView = (TextView) convertView.findViewById(R.id.tv_top_line);
            childHolder.iconView = (ImageView) convertView.findViewById(R.id.iv_icon);
            childHolder.addrview = (TextView) convertView.findViewById(R.id.tv_addr);
            childHolder.numView = (TextView) convertView.findViewById(R.id.tv_num);
            childHolder.timeView = (TextView) convertView.findViewById(R.id.tv_time);
            childHolder.statusView = (TextView) convertView.findViewById(R.id.tv_status);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ViewHolderChild) convertView.getTag();
        }

        if (null != mGroupList.get(i) && null != mGroupList.get(i).getChildList().get(i1)) {
            if (i1 == 0)
                childHolder.lineView.setVisibility(View.VISIBLE);
            else
                childHolder.lineView.setVisibility(View.GONE);
            if (mGroupList.get(i).getChildList().get(i1).isInput()) {
                childHolder.iconView.setBackgroundResource(R.mipmap.arrow_down);
            } else {
                childHolder.iconView.setBackgroundResource(R.mipmap.arrow_up);
            }

            childHolder.addrview.setText( mGroupList.get(i).getChildList().get(i1).getAddr());
            childHolder.numView.setText( mGroupList.get(i).getChildList().get(i1).getNum() + "");
            childHolder.timeView.setText( mGroupList.get(i).getChildList().get(i1).getTime());
            childHolder.statusView.setText( mGroupList.get(i).getChildList().get(i1).getStatus());
            childHolder.childLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, TransactionDetailActivity.class);
                    intent.putExtra("trans_detail",  mGroupList.get(i).getChildList().get(i1));
                    mContext.startActivity(intent);
                }
            });
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    class ViewHolderGroup {
        private TextView textView;
        private ImageView imageView;
        private TextView lineView;
    }

    class ViewHolderChild {
        private RelativeLayout childLayout;
        private TextView lineView;
        private ImageView iconView;
        private TextView addrview;
        private TextView numView;
        private TextView timeView;
        private TextView statusView;
    }
}
