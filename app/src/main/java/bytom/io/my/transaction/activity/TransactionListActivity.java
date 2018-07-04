package bytom.io.my.transaction.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
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
import bytom.io.entity.transaction.InputsEntity;
import bytom.io.entity.transaction.OutputsEntity;
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
    private String mAddress = "bm1q5p9d4gelfm4cc3zq3slj7vh2njx23ma2cf866j";

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
        final ProgressDialog dialog = ProgressDialog.show(TransactionListActivity.this, "", "数据请求中", true, true);
        dialog.show();
        Map<String, String> params = new HashMap<String, String>();

//        params.put("address", "bm1qv4709k0kv96xzfkycdlc20s94pah7gfyg2n47z");
        params.put("address", mAddress);
        params.put("assetID", "ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff");
        JsonRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, HttpUrls.URL_TRANSACTIONS_LIST, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("=====", " ==" + response.toString());
                        dialog.dismiss();
                        TransactionsEntity entity = new Gson().fromJson(response.toString(), TransactionsEntity.class);
                        generateData(entity);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                if (null != error && !TextUtils.isEmpty(error.getMessage()))
                    Toast.makeText(TransactionListActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(TransactionListActivity.this, getString(R.string.trans_req_data_failed), Toast.LENGTH_SHORT).show();
            }
        });
        VolleyWrapper.getInstance(this).addToRequestQueue(jsonRequest);
    }

    private void generateData(TransactionsEntity entity) {
        if (null == entity) return;
        if (null == entity.getTransactions()) return;

        ArrayList<TransactionListEntity> list = entity.getTransactions();
        if(null == list) return;
        mGroupList = new ArrayList<>();
        Log.e("===sze=====", "==size==" + list.size());
        for (int i = 0; i < list.size(); i++) {
            if (null == list.get(i)) break;
            if (!isSameDate(mGroupList, list.get(i).getTimestamp())) {
                TransListGroupEntity groupEntity = new TransListGroupEntity();
                groupEntity.setTime(generateYearMonth(list.get(i).getTimestamp()));
                mGroupList.add(groupEntity);
            }
        }

        for (int j = 0; j < mGroupList.size(); j++) {
            TransListGroupEntity groupEntity = mGroupList.get(j);
            ArrayList<TransListItemEntity> childList = new ArrayList<>();
            for (int k = 0; k < list.size(); k++) {
                if (mGroupList.get(j).getTime().equals(generateYearMonth(list.get(k).getTimestamp()))) {
                    generateChildItemData(list.get(k), childList);
                }
            }
            groupEntity.setChildList(childList);
        }

        mAdapter.setData(mGroupList);
        mAdapter.notifyDataSetChanged();
    }

    private void generateChildItemData(TransactionListEntity entity, ArrayList<TransListItemEntity> tempList) {
        TransListItemEntity itemEntity = new TransListItemEntity();
        ArrayList<OutputsEntity> outputsList = entity.getOutputs();
        ArrayList<InputsEntity> inputsList = entity.getInputs();

        itemEntity.setTime(generateYearMonthDay(entity.getTimestamp()));
        if (entity.getConfirmation() > 6)
            itemEntity.setStatus(getString(R.string.trans_success));
        else
            itemEntity.setStatus(getString(R.string.trans_failed));
        if (null != outputsList) {
            long mount = 0L;
            for (int j = 0; j < outputsList.size(); j++) {
                if (null == outputsList.get(j)) break;
                mount = mount + outputsList.get(j).getAmount();
            }
//            double num = mount/ Math.pow(Math.E, 8);

            if ("send".equals(entity.getOp())) {
                itemEntity.setNum("-" + mount);
            } else if ("receive".equals(entity.getOp())) {
                itemEntity.setNum("+" + mount);
            }
        }
        if ("send".equals(entity.getOp())) {
            if (null != outputsList && outputsList.size() > 1 && null != outputsList.get(1)
                    && !TextUtils.isEmpty(outputsList.get(1).getAddress())) {
                itemEntity.setAddr(outputsList.get(1).getAddress());
            } else if (null != outputsList && outputsList.size() > 0 && null != outputsList.get(0)
                    ) {
                itemEntity.setAddr(outputsList.get(0).getAddress());
            }

            itemEntity.setInput(false);
            itemEntity.setSendAddr(mAddress);
            itemEntity.setReceiveAddr(itemEntity.getAddr());

        } else if ("receive".equals(entity.getOp())) {
            if (null != inputsList && inputsList.size() > 0) {
                itemEntity.setAddr(inputsList.get(0).getAddress());
            }
            itemEntity.setInput(true);
            itemEntity.setSendAddr(itemEntity.getAddr());
            itemEntity.setReceiveAddr(mAddress);
        }
        itemEntity.setFee(entity.getFee());
        itemEntity.setTransNum(entity.getID());

        tempList.add(itemEntity);
    }

    private boolean isSameDate(ArrayList<TransListGroupEntity> list, long time) {
        if (null == list || list.size() == 0) return false;
        String timeStr = generateYearMonth(time);
        for (int i = 0; i < list.size(); i++) {
            if (timeStr.equals(list.get(i).getTime())) {
                return true;
            }
        }
        return false;
    }

    private String generateYearMonth(long time) {
        String result = new SimpleDateFormat("yyyy-MM", Locale.ENGLISH).format(time * 1000);
        return result;
    }

    private String generateYearMonthDay(long time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH).format(time*1000);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };
}
