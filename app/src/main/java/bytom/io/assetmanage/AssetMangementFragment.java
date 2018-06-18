package bytom.io.assetmanage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import bytom.io.R;

/**
 * Created by Nil on 2018/6/18
 */
public class AssetMangementFragment extends AssetManageBaseFragment {
    public static final String TAG = AssetMangementFragment.class.getName();

    @BindView(R.id.as_transfer_recent_list) RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.as_management_fragment, container, false);
        ButterKnife.bind(this, view);

        return view;
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
