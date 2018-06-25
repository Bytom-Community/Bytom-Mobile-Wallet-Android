package bytom.io.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import bytom.io.R;

public class WalletListActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_wallet_list);
        init();
    }
    private void init(){
        findViewById(R.id.iv_left).setOnClickListener(listener);
        ((TextView)findViewById(R.id.tv_title)).setText(getString(R.string.wallet));
        findViewById(R.id.create_wallet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent(WalletListActivity.this, CreateWalletActivity.class);
                    startActivity(intent);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        findViewById(R.id.import_wallet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent(WalletListActivity.this, ImportWalletActivity.class);
                    startActivity(intent);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        WalletAdapter adapter = new WalletAdapter(WalletListActivity.this,R.layout.wallet_item);
        ListView lv = (ListView)findViewById(R.id.wallet_list);
        lv.setAdapter(adapter);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };


    public class WalletAdapter extends ArrayAdapter{

        private int mResId = 0;

        public WalletAdapter(Context context, int ResId) {
            super(context,ResId,new Object[10]);
            mResId = ResId;
        }
        //TODO List view is hiden by bottom button
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(getContext()).inflate(mResId,null);
            ImageView Walleticon = (ImageView) view.findViewById(R.id.wallet_icon);
            ImageView EnterDetail = (ImageView) view.findViewById(R.id.enter_detail);
            TextView PrivateWallet = (TextView) view.findViewById(R.id.private_wallet);
            TextView WalletAddress = (TextView) view.findViewById(R.id.wallet_address);
            Walleticon.setImageResource(R.drawable.wallet);
            EnterDetail.setImageResource(R.drawable.arrow_right);
            PrivateWallet.setText(R.string.private_wallet);
            WalletAddress.setText("BTMdSDSADSADASDSADASDSAD");
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        Intent intent = new Intent(WalletListActivity.this, PrivateWalletActivity.class);
                        startActivity(intent);
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            });
            return view;
        }
    }
}
