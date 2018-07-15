package bytom.io.scan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.EnumMap;
import java.util.Map;

import bytom.io.R;
import bytom.io.assetmanage.TransferActivity;

/**
 * Created by Nil on 2018/7/15
 */
public class ScanWrapper {

    public static void startScanQRCode(Object caller) {
        IntentIntegrator intentIntegrator;
        if (caller instanceof android.support.v4.app.Fragment) {
            intentIntegrator =
                    IntentIntegrator.forSupportFragment((android.support.v4.app.Fragment) caller);
        } else if (caller instanceof android.app.Fragment) {
            intentIntegrator =
                    IntentIntegrator.forFragment((android.app.Fragment) caller);
        } else if (caller instanceof Activity) {
            intentIntegrator = new IntentIntegrator((Activity) caller);
        } else {
            throw new IllegalArgumentException("Caller is invalid. Caller is not fragment or activity.");
        }

        intentIntegrator.setCaptureActivity(CustomCaptureActivity.class);
        intentIntegrator.setTimeout(1000 * 60 * 10);
        intentIntegrator.initiateScan();
    }

    private static String parseScanResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        String retVal = null;
        if (result != null) {
            retVal = result.getContents();
        }
        return retVal;
    }

    public static int handleScanResult(Context context, int requestCode,
                                       int resultCode, Intent data) {
        String address = parseScanResult(requestCode, resultCode, data);
        if (!TextUtils.isEmpty(address)) {
            //success
            TransferActivity.startActivity(context, address);
            return 1;
        } else {
            //failed
            return -1;
        }
    }

    public static final Map<DecodeHintType, Object> HINTS = new EnumMap<>(DecodeHintType.class);

    /**
     * 解析二维码图片
     *
     * @param bitmap 要解析的二维码图片
     */
    public static void decodeQRCode(final Activity context, final Bitmap bitmap) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    int[] pixels = new int[width * height];
                    bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
                    RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);
                    Result result = new MultiFormatReader().decode(new BinaryBitmap(new HybridBinarizer(source)), HINTS);
                    return result.getText();
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                if (TextUtils.isEmpty(result)) {
                    Toast.makeText(context, R.string.scan_no_result, Toast.LENGTH_LONG).show();
                } else {
                    TransferActivity.startActivity(context, result);
                    context.finish();
                }
            }
        }.execute();
    }
}
