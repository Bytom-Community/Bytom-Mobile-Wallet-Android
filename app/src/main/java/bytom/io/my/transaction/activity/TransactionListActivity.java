package bytom.io.my.transaction.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;

import bytom.io.R;
import bytom.io.entity.transaction.TransListGroupEntity;
import bytom.io.entity.transaction.TransListItemEntity;
import bytom.io.my.transaction.adapter.TransactionListAdapter;

/**
 * <b>版权</b>：　　　比原链 版权所有(c) 2018 <br>
 * <b>作者</b>：　　　愤世嫉俗
 * <b>创建日期</b>：　18/6/11 <br>
 */


public class TransactionListActivity extends Activity{

    private ExpandableListView mExpandableListView;
    private TransactionListAdapter mAdapter;
    private ArrayList<TransListGroupEntity> mGroupList;
    private ArrayList<ArrayList<TransListItemEntity>> mChildGroupList;
    private ArrayList<TransListItemEntity> mItemList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_transaction_list);

        initData();

        init();
    }

    private void init() {

        ((TextView)findViewById(R.id.tv_title)).setText(getString(R.string.trans_record));
        findViewById(R.id.iv_left).setOnClickListener(listener);
        mExpandableListView = findViewById(R.id.exlistview);
        int width = getWindowManager().getDefaultDisplay().getWidth();
        mExpandableListView.setIndicatorBounds(width-40, width-10);
        mAdapter = new TransactionListAdapter(this, mGroupList, mChildGroupList);
        mExpandableListView.setAdapter(mAdapter);
    }

    private void initData() {
        mGroupList = new ArrayList<>();
        TransListGroupEntity groupEntity = new TransListGroupEntity();
        groupEntity.setTime("2018年6月");
        mGroupList.add(groupEntity);
        groupEntity = new TransListGroupEntity();
        groupEntity.setTime("2018年5月");
        mGroupList.add(groupEntity);
        groupEntity.setTime("2018年4月");
        mGroupList.add(groupEntity);

        mChildGroupList = new ArrayList<>();
        mItemList = new ArrayList<>();
        TransListItemEntity itemEntity = new TransListItemEntity();
        itemEntity.setAddr("BMASDGDGHDSHSDHDFHKKKFGFFGHDFGHFHFHJFYFJFHFHJFHJFUYFH");
        itemEntity.setClassify("Bytom");
        itemEntity.setTime("2018-06-01 12:00");
        itemEntity.setNum("-1000.0BTM");
         mItemList.add(itemEntity);
        itemEntity = new TransListItemEntity();
        itemEntity.setAddr("JJKOOLLL<JNFYFTDDRDHGJKGJUFJFGYFGHYFGGHYGTYFYUF");
        itemEntity.setClassify("Bytom");
        itemEntity.setTime("2018-06-02 12:00");
        itemEntity.setNum("-5000.0BTM");
        mItemList.add(itemEntity);
        itemEntity = new TransListItemEntity();
        itemEntity.setAddr("KLKHUHIUGHJBFGDGJ");
        itemEntity.setClassify("Bytom");
        itemEntity.setTime("2018-06-03 12:00");
        itemEntity.setNum("-6000.0BTM");
        mItemList.add(itemEntity);
        mChildGroupList.add(mItemList);


        mItemList = new ArrayList<>();
        itemEntity = new TransListItemEntity();
        itemEntity.setAddr("BMASDGDGHDSHSDHDFHFH");
        itemEntity.setClassify("Bytom");
        itemEntity.setTime("2018-06-01 12:00");
        itemEntity.setNum("-1000.0BTM");
        mItemList.add(itemEntity);
        itemEntity = new TransListItemEntity();
        itemEntity.setAddr("JJKOOLLL<JNGHYGTYFYUF");
        itemEntity.setClassify("Bytom");
        itemEntity.setTime("2018-06-02 12:00");
        itemEntity.setNum("-5000.0BTM");
        mItemList.add(itemEntity);
        itemEntity = new TransListItemEntity();
        itemEntity.setAddr("KLKHUHIUGHJBFGDGJ");
        itemEntity.setClassify("Bytom");
        itemEntity.setTime("2018-06-03 12:00");
        itemEntity.setNum("-6000.0BTM");
        mItemList.add(itemEntity);
        mChildGroupList.add(mItemList);

        mItemList = new ArrayList<>();
        itemEntity = new TransListItemEntity();
        itemEntity.setAddr("BMASDGDGHDSHSDHDFHFH");
        itemEntity.setClassify("Bytom");
        itemEntity.setTime("2018-06-01 12:00");
        itemEntity.setNum("-1000.0BTM");
        mItemList.add(itemEntity);
        itemEntity = new TransListItemEntity();
        itemEntity.setAddr("JJKOOLLL<JNGHYGTYFYUF");
        itemEntity.setClassify("Bytom");
        itemEntity.setTime("2018-06-02 12:00");
        itemEntity.setNum("-5000.0BTM");
        mItemList.add(itemEntity);
        itemEntity = new TransListItemEntity();
        itemEntity.setAddr("KLKHUHIUGHJBFGDGJ");
        itemEntity.setClassify("Bytom");
        itemEntity.setTime("2018-06-03 12:00");
        itemEntity.setNum("-6000.0BTM");
        mItemList.add(itemEntity);
        mChildGroupList.add(mItemList);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
             finish();
        }
    };
}
