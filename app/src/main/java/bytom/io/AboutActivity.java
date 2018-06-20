package bytom.io;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class AboutActivity extends BytomActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        setUpToolBar();
        launchFragment();
    }

    private void setUpToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView webTitle = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.chevron_selected);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        webTitle.setText(R.string.about);
    }

    private void launchFragment() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    protected Fragment createFragment() {
        //return AboutFragment.newInstance(getIntent().getData());
        Uri uri = Uri.parse("https://www.bytom.io");  // fake uri
        return AboutFragment.newInstance(uri);
    }
}
