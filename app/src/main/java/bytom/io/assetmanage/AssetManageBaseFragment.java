package bytom.io.assetmanage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import bytom.io.R;

/**
 * Created by Nil on 2018/6/18
 */
public abstract class AssetManageBaseFragment extends Fragment {

    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    protected TextView mTitle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        setupToolbar();
    }

    protected void setupToolbar() {
        mToolbar.setNavigationIcon(R.drawable.chevron_selected);
    }
}
