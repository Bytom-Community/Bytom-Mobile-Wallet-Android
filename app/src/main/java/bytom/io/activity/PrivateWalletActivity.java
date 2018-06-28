package bytom.io.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import bytom.io.BytomActivity;
import bytom.io.R;
import bytom.io.assetmanage.widget.PasswordConfirmDialog;

public class PrivateWalletActivity extends BytomActivity
        implements ItemWithArrowAdapter.OnItemClickListener{

    private static final int ITEM_SIZE = 3;
    private String[] mExtraItem = {"tv2", null, null};
    private int[] mRightImageView = new int[ITEM_SIZE];
    private RecyclerView mRecyclerView;
    private ItemWithArrowAdapter mItemWithArrowAdapter;
    private PasswordConfirmDialog mPasswordConfirmDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_private_wallet);
        setUpToolBar();
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Button bt_copy = findViewById(R.id.button_private_wallet);
        bt_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO add function for click copy button
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        if (mItemWithArrowAdapter == null) {
            mItemWithArrowAdapter =
                    new ItemWithArrowAdapter(this,
                            getResources().getStringArray(R.array.private_wallet),
                            mExtraItem, mRightImageView, this);
            mRecyclerView.setAdapter(mItemWithArrowAdapter);
        } else {
            mItemWithArrowAdapter.setExtraItem(mExtraItem);
            mItemWithArrowAdapter.notifyDataSetChanged();
        }
    }

    private void setUpToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.chevron_selected);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbarTitle.setText(R.string.private_wallet);
    }

    @Override
    public void onClick(int position) {
        switch (position){
            case 0:
                // TODO add function for click alias button
                Toast.makeText(this,
                        getResources().getStringArray(R.array.private_wallet)[position],
                        Toast.LENGTH_SHORT).show();
                break;
            case 1:
                mPasswordConfirmDialog = new PasswordConfirmDialog();
                mPasswordConfirmDialog.show(getSupportFragmentManager(), "passworkConfirm");
                break;
            case 2:
                startActivity(new Intent(PrivateWalletActivity.this,
                        BackupWalletActivity.class));
                break;
            default:
                break;

        }
    }
}
