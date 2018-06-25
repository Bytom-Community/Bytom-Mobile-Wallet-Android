package bytom.io.assetmanage.widget;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.OnClick;
import bytom.io.R;

/**
 * Created by Nil on 2018/6/26
 */
public class PasswordConfirmDialog extends AppCompatDialogFragment {
    private static final String TAG = "PasswordConfirmDialog";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.as_transfer_passwork_confirm_dialog,
                container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        int width = getResources().getDimensionPixelSize(R.dimen.as_transfer_password_confirm_dialog_width);
        int height = getResources().getDimensionPixelSize(R.dimen.as_transfer_password_confirm_dialog_height);
        getDialog().getWindow().setLayout(width, height);
    }

    @OnClick({R.id.as_transfer_cancel, R.id.as_transfer_commit})
    void onClickAction(Button button) {
        this.dismiss();
        switch (button.getId()) {
            case R.id.as_transfer_cancel: {
                Log.i(TAG, "transfer cancel.");
                break;
            }

            case R.id.as_transfer_commit: {
                Log.i(TAG, "transfer commit.");
                break;
            }
        }
    }
}
