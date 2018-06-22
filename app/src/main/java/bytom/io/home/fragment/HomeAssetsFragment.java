package bytom.io.home.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import bytom.io.R;
import bytom.io.home.adapter.HomeAssetAdapter;

/**
 * Created by DongFangZhou on 2018/6/21.
 */

public class HomeAssetsFragment extends Fragment {
    @BindView(R.id.rv_home_asset)
    RecyclerView mRv;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_asset, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated (@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init () {
        mRv.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add(i);
        }
        mRv.setAdapter(new HomeAssetAdapter(getContext(),list));
    }

    @Override
    public void onDestroyView () {
        super.onDestroyView();
        unbinder.unbind();
    }
}
