package bytom.io.assetmanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import bytom.io.R;
import bytom.io.assetmanage.adapter.RecentTransferAdapter;
import bytom.io.assetmanage.bean.ListTransactionsBean;
import bytom.io.common.GsonRequest;
import bytom.io.common.UrlConfig;
import bytom.io.common.VolleyWrapper;
import bytom.io.utils.Constants;

/**
 * Created by Nil on 2018/6/18
 */
public class AssetMangementFragment extends AssetManageBaseFragment {
    public static final String TAG = AssetMangementFragment.class.getSimpleName();

    @BindView(R.id.as_transfer_recent_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.as_coin_count)
    TextView mAsCoinCount;

    @BindView(R.id.as_equivalent_to_RMB)
    TextView mAsEquivalentToRMB;

    @BindView(R.id.progressView)
    View mProgressView;

    private String mAddress;
    private String mAssetId;
    private String mAmount;

    private DecimalFormat mDecimalFormat = new DecimalFormat("###.#########");

    private RecentTransferAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.as_management_fragment, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData() {
        Intent intent = getActivity().getIntent();
        //Just for test.
        mAddress = "bm1q5p9d4gelfm4cc3zq3slj7vh2njx23ma2cf866j";//intent.getStringExtra(Constants.EXTRA_ADDRESS);
        mAssetId = "ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff";//intent.getStringExtra(Constants.EXTRA_ASSET_ID);
        mAmount = intent.getStringExtra(Constants.EXTRA_AMOUNT);


        float amount = Float.parseFloat(mAmount) / 100000000;
        mAsCoinCount.setText(mDecimalFormat.format(amount));
        mAsEquivalentToRMB.setText(R.string.as_management_equ_to_rmb);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RecentTransferAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);

        Map<String, String> params = new HashMap<>();
        params.put("address", mAddress);
        params.put("assetID", mAssetId);
        GsonRequest jsonRequest = new GsonRequest(Request.Method.POST, UrlConfig.listTransactions(params),
                ListTransactionsBean.class, null, new JSONObject(params),
                new Response.Listener<ListTransactionsBean>() {
                    @Override
                    public void onResponse(ListTransactionsBean response) {
                        Log.i(TAG, "onResponse: " + response);

                        List<ListTransactionsBean.TransactionsBean> dataList =
                                response.getTransactions();
                        mAdapter.setData(dataList);
                        mProgressView.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "error: " + error);
                mProgressView.setVisibility(View.GONE);
            }
        });
        mProgressView.setVisibility(View.VISIBLE);
        VolleyWrapper.getInstance(getContext()).addToRequestQueue(jsonRequest);
    }

    @Override
    protected void setupToolbar() {
        super.setupToolbar();
        mTitle.setText(R.string.as_management_default_title);
    }

    @OnClick(R.id.as_transfer)
    void onTransferClick() {
        Log.i(TAG, "onTransferClick");
        startFragment(TransferFragment.class);
    }

    @OnClick(R.id.as_receipt)
    void onReceiptClick() {
        Log.i(TAG, "onReceiptClick");
        startFragment(ReceiptFragment.class);
    }

    private void startFragment(Class clazz) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(
                android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        if (clazz == TransferFragment.class) {
            transaction.replace(R.id.fragment_container, new TransferFragment(),
                    TransferFragment.TAG);
        } else if (clazz == ReceiptFragment.class) {
            transaction.replace(R.id.fragment_container, new ReceiptFragment(),
                    ReceiptFragment.TAG);
        }
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
