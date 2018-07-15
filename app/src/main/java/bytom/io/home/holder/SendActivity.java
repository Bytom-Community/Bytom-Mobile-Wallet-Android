package bytom.io.home.holder;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import bytom.io.R;
import bytom.io.assetmanage.fragment.TransferFragment;

public class SendActivity extends AppCompatActivity {

    @BindView(R.id.fl_send)
    FrameLayout mFlSend;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        ButterKnife.bind(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(
//                android.R.anim.slide_in_left, android.R.anim.slide_out_right,
//                android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        transaction.add(R.id.fl_send, new TransferFragment());
        transaction.commit();
    }
}
