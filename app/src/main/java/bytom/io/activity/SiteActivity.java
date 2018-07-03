package bytom.io.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import bytom.io.R;
import bytom.io.utils.StatusBarUtil;

public class SiteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site);
        // setupWindow();
        setUpToolBar();
    }

    void setupWindow() {
        StatusBarUtil.transparencyBar(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    private void setUpToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.chevron_selected);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbarTitle.setText(R.string.site);
    }


}
