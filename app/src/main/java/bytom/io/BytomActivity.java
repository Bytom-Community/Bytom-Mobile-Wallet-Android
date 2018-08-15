package bytom.io;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.UnsupportedEncodingException;

import butterknife.BindView;
import butterknife.ButterKnife;
import bytom.io.activity.CurrencyActivity;
import bytom.io.activity.SiteActivity;
import bytom.io.activity.WalletListActivity;
import bytom.io.assetmanage.AssetManagementActivity;
import bytom.io.home.HomeActivity;
import bytom.io.utils.Constant;
import bytom.io.crypto.EncryptUtil;

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
        EncryptFunctionExample();
    }

    void EncryptFunctionExample(){
        final String TAG = "EncryptUtil";
        byte [] PriKey = new byte[64];
        byte [] PubKey = new byte[32];
        try{
            EncryptUtil.generateKeyPair(PubKey,PriKey);
        }catch (Exception ex){
            Log.v(TAG,"generateKeyPair exception",ex);
        }
        Log.v(TAG,"Prikey:"+ Constant.bytesToHex(PriKey));
        Log.v(TAG,"PubKey:"+ Constant.bytesToHex(PubKey));
        byte[] message = null;
        try{
            message = "test message".getBytes("utf-8");
        }catch(UnsupportedEncodingException ex){
            Log.e(TAG,"revert error");
            return;
        }
        byte[] sign = EncryptUtil.sign(PriKey,message);
        if(sign!=null){
            Log.v(TAG,"sign :" + Constant.bytesToHex(sign));
        }

        if(EncryptUtil.verify(PubKey,message,sign)){
            Log.v(TAG, "message verify passed");
        }else {
            Log.v(TAG, "message verify failed");
        }

        byte[] wrongmessage = null;
        try{
            wrongmessage = "wrong message".getBytes("utf-8");
        }catch(UnsupportedEncodingException ex){
            Log.e(TAG,"revert error");
            return;
        }
        if(EncryptUtil.verify(PubKey,wrongmessage,sign)){
            Log.v(TAG,"wrongmessage verify passed");
        }else {
            Log.v(TAG,"wrongmessage verify failed");
        }

    }
}
