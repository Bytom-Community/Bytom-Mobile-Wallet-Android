package bytom.io.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;

import bytom.io.R;
import bytom.io.entity.transaction.TransListGroupEntity;
import bytom.io.entity.transaction.TransListItemEntity;
import bytom.io.my.transaction.adapter.TransactionListAdapter;



public class WalletListActivity extends Activity{

    private ExpandableListView mExpandableListView;
    private TransactionListAdapter mAdapter;
    private ArrayList<TransListGroupEntity> mGroupList;
    private ArrayList<ArrayList<TransListItemEntity>> mChildGroupList;
    private ArrayList<TransListItemEntity> mItemList;

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
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };
}
