package bytom.io;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import bytom.io.activity.CurrencyActivity;
import bytom.io.activity.SiteActivity;
import bytom.io.activity.WalletListActivity;
import bytom.io.assetmanage.AssetManagementActivity;
import bytom.io.home.HomeActivity;

public class BytomActivity extends AppCompatActivity {

    @BindView(R.id.bt_home)
    Button mBtHome;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bytom);
        ButterKnife.bind(this);

        findViewById(R.id.bt_asset_management).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                AssetManagementActivity.startActivity(BytomActivity.this);
            }
        });

        findViewById(R.id.bt_wallet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent intent = new Intent(BytomActivity.this, WalletListActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent intent = new Intent(BytomActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
        mBtHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent intent = new Intent(BytomActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_currency).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent intent = new Intent(BytomActivity.this, CurrencyActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_site).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent intent = new Intent(BytomActivity.this, SiteActivity.class);
                startActivity(intent);
            }
        });

    }
}
