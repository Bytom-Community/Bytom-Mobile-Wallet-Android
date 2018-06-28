package bytom.io.my.transaction.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import bytom.io.R;
import bytom.io.common.VolleyWrapper;
import bytom.io.constant.HttpUrls;
import bytom.io.entity.transaction.TransListGroupEntity;
import bytom.io.entity.transaction.TransListItemEntity;
import bytom.io.entity.transaction.TransactionListEntity;
import bytom.io.entity.transaction.TransactionsEntity;
import bytom.io.my.transaction.adapter.TransactionListAdapter;

/**
 * <b>版权</b>：　　　比原链 版权所有(c) 2018 <br>
 * <b>作者</b>：　　　愤世嫉俗
 * <b>创建日期</b>：　18/6/11 <br>
 */


public class TransactionListActivity extends Activity {

    private ExpandableListView mExpandableListView;
    private TransactionListAdapter mAdapter;
    private ArrayList<TransListGroupEntity> mGroupList;
    private ArrayList<ArrayList<TransListItemEntity>> mChildGroupList;
    private ArrayList<TransListItemEntity> mItemList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_transaction_list);

        init();

        onRequestData();
    }

    private void init() {
        ((TextView) findViewById(R.id.tv_title)).setText(getString(R.string.trans_record));
        findViewById(R.id.iv_left).setOnClickListener(listener);
        mExpandableListView = findViewById(R.id.exlistview);
        int width = getWindowManager().getDefaultDisplay().getWidth();
        mExpandableListView.setIndicatorBounds(width - 40, width - 10);
        mAdapter = new TransactionListAdapter(this);
        mExpandableListView.setAdapter(mAdapter);
    }

    private void onRequestData() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("address", "bm1q5p9d4gelfm4cc3zq3slj7vh2njx23ma2cf866j");
        params.put("assetID", "ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff");
        JsonRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, HttpUrls.URL_TRANSACTIONS_LIST, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        TransactionsEntity entity = new Gson().fromJson(response.toString(), TransactionsEntity.class);
                        generateData(entity);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (null != error)
                    Toast.makeText(TransactionListActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        VolleyWrapper.getInstance(this).addToRequestQueue(jsonRequest);
    }

    private void generateData(TransactionsEntity entity) {
        if(null == entity) return;
        if(null == entity.getTransactions()) return;

        ArrayList<TransactionListEntity> list = entity.getTransactions();
        mGroupList = new ArrayList<>();
        for (int i =0; i< list.size(); i++) {
            TransListGroupEntity groupEntity = new TransListGroupEntity();
            if(null == list.get(i)) break;
            groupEntity.setTime(generateYearMonth(list.get(i).getTimestamp()));

            mChildGroupList = new ArrayList<>();
        }


        initData();
        mAdapter.setData(mGroupList, mChildGroupList);
        mAdapter.notifyDataSetChanged();
    }

    private String generateYearMonth(long time) {
       return new SimpleDateFormat("yyyy-MM-dd",
               Locale.ENGLISH).format(time);
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
