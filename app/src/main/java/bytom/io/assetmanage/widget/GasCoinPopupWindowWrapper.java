package bytom.io.assetmanage.widget;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.OnClick;
import bytom.io.R;

/**
 * Created by Nil on 2018/6/22
 */
public class GasCoinPopupWindowWrapper {

    private Context mContext;
    private Button mGasCoinType;
    private PopupWindow mPopupWindow;

    public GasCoinPopupWindowWrapper(Context context, Button gasButton) {
        mGasCoinType = gasButton;
        mContext = context;
    }

    public void showAsDropDown(View anchor) {
        View popupView = LayoutInflater.from(mContext)
                .inflate(R.layout.as_transfer_gas_coin_types, null);
        ButterKnife.bind(this, popupView);

        Resources res = mContext.getResources();
        int popupWidth = res.getDimensionPixelSize(R.dimen.as_gas_coin_popup_width);
        int popupHeight = res.getDimensionPixelOffset(R.dimen.as_gas_coin_popup_height);
        mPopupWindow= new PopupWindow(popupView, popupWidth, popupHeight);

        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.showAsDropDown(anchor);
    }

    @OnClick({R.id.as_transfer_gas_btm, R.id.as_transfer_gas_mbtm, R.id.as_transfer_gas_neu})
    void onClickGasItem(TextView item) {
        switch (item.getId()) {
            case R.id.as_transfer_gas_btm: {
                mGasCoinType.setText(R.string.as_transfer_gas_btm);
                break;
            }

            case R.id.as_transfer_gas_mbtm: {
                mGasCoinType.setText(R.string.as_transfer_gas_mbtm);
                break;
            }

            case R.id.as_transfer_gas_neu: {
                mGasCoinType.setText(R.string.as_transfer_gas_neu);
                break;
            }
        }
        mPopupWindow.dismiss();
    }
}
