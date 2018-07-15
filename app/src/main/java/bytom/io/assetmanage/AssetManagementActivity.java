package bytom.io.assetmanage;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import bytom.io.R;
import bytom.io.assetmanage.fragment.AssetMangementFragment;
import bytom.io.utils.Constants;
import bytom.io.utils.StatusBarUtil;

/**
 * Created by Nil on 2018/6/18
 */
public class AssetManagementActivity extends AppCompatActivity {

    @BindView(R.id.fragment_container)
    FrameLayout mFragmentContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.as_management_activity);

        setupWindow();

        ButterKnife.bind(this);

        initView();
    }

    void setupWindow() {
        StatusBarUtil.transparencyBar(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    void initView() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        /*transaction.setCustomAnimations(
                android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                android.R.anim.slide_in_left, android.R.anim.slide_out_right);*/
        transaction.add(R.id.fragment_container, new AssetMangementFragment(),
                AssetMangementFragment.TAG);
        //transaction.addToBackStack(null);
        transaction.commit();
    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, AssetManagementActivity.class));
    }

    public static void startActivity(Context context, String address, String assetId, String amount) {
        Intent intent = new Intent(context, AssetManagementActivity.class);
        intent.putExtra(Constants.EXTRA_ADDRESS, address);
        intent.putExtra(Constants.EXTRA_ASSET_ID, assetId);
        intent.putExtra(Constants.EXTRA_AMOUNT, amount);

        context.startActivity(intent);
    }
}
