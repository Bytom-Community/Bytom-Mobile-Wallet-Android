package bytom.io.assetmanage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import bytom.io.R;

/**
 * Created by Nil on 2018/6/18
 */
public class ReceiptFragment extends AssetManageBaseFragment {
    public static final String TAG = ReceiptFragment.class.getName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.as_receipt_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void setupToolbar() {
        super.setupToolbar();
        mTitle.setText(R.string.as_receipt_title);
    }
}
