package bytom.io.assetmanage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import bytom.io.R;
import bytom.io.assetmanage.fragment.TransferFragment;

/**
 * Created by Nil on 2018/7/15
 */
public class TransferActivity extends AppCompatActivity {
    public static final String EXTRA_ADDRESS = "address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        ButterKnife.bind(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container, new TransferFragment());
        transaction.commit();
    }

    public static void startActivity(Context context, String address) {
        Intent intent = new Intent(context, TransferActivity.class);
        intent.putExtra(TransferActivity.EXTRA_ADDRESS, address);
        context.startActivity(intent);
    }
}
