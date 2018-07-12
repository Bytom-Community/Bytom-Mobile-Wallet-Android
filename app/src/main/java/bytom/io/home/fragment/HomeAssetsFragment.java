package bytom.io.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import bytom.io.R;
import bytom.io.assetmanage.AssetManagementActivity;
import bytom.io.common.GsonRequest;
import bytom.io.common.UrlConfig;
import bytom.io.common.VolleyWrapper;
import bytom.io.home.ReceiveActivity;
import bytom.io.home.adapter.HomeAssetAdapter;
import bytom.io.home.bean.HomeAssetsBean;
import bytom.io.home.holder.SendActivity;

/**
 * Created by DongFangZhou on 2018/6/21.
 */

public class HomeAssetsFragment extends Fragment implements HomeAssetAdapter.OnAssetClickListener {
    @BindView(R.id.rv_home_asset)
    RecyclerView mRv;
    Unbinder unbinder;
    private OnMenuClickListener mMenuListener;
    private HomeAssetAdapter mAdapter;

    public void setMenuListener(OnMenuClickListener listener) {
        this.mMenuListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_asset, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        initData();
    }

    private void initData () {
        Map<String,String> map= new HashMap<>();
        map.put("address","bm1qsk6dj6pym7yng0ev7wne7tm3d54ea2sjz5tyxk");
        GsonRequest jsonRequest = new GsonRequest(Request.Method.POST, UrlConfig.assetsList(map),
                HomeAssetsBean.class, null, null,
                new Response.Listener<HomeAssetsBean>() {
                    @Override
                    public void onResponse(HomeAssetsBean response) {
                        // Display the first 500 characters of the response string.
                        Log.d("http", "onResponse: " + response);
                        showList(response.getAssets());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("http", "error: " + error);
            }
        });
        VolleyWrapper.getInstance(getContext()).addToRequestQueue(jsonRequest);
    }

    private void showList (List<HomeAssetsBean.AssetsBean> list) {
        mRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new HomeAssetAdapter(getContext(), list, this);
        mRv.setAdapter(mAdapter);
    }

    private void init() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onMenuClick() {
        if (null != mMenuListener)
            mMenuListener.onMenuClick();
    }

    @Override
    public void onSendClick() {
        startActivity(new Intent(getContext(), SendActivity.class));
    }

    @Override
    public void onReciveClick() {
        startActivity(new Intent(getContext(), ReceiveActivity.class));
    }

    @Override
    public void onScanClick() {
        Toast.makeText(getContext(), "扫描", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(int position) {
        HomeAssetsBean.AssetsBean bean = mAdapter.getItem(position);
        String address = "bm1qsk6dj6pym7yng0ev7wne7tm3d54ea2sjz5tyxk";
        String assetId = bean.getAssetID();
        String amount = bean.getAmount();

        AssetManagementActivity.startActivity(getActivity(), address, assetId, amount);
    }

    public interface OnMenuClickListener {
        void onMenuClick();
    }
}
