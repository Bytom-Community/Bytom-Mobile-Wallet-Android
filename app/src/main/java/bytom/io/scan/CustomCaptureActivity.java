package bytom.io.scan;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.TextView;

import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import bytom.io.R;

/**
 * Created by Nil on 2018/7/15
 */
public class CustomCaptureActivity extends Activity implements Toolbar.OnMenuItemClickListener {
    private static final int REQUEST_CODE_PICK_IMAGE = 100;

    private CaptureManager capture;

    @BindView(R.id.zxing_barcode_scanner)
    DecoratedBarcodeView mBarcodeScannerView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBarcodeScannerView = initializeContent();
        mToolbarTitle.setText(R.string.scan_title);

        capture = new CaptureManager(this, mBarcodeScannerView);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();

        setupToolbar();
    }

    /**
     * Override to use a different layout.
     *
     * @return the DecoratedBarcodeView
     */
    protected DecoratedBarcodeView initializeContent() {
        setContentView(R.layout.scan_capture_activity);
        ButterKnife.bind(this);
        return mBarcodeScannerView;
    }

    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    protected void setupToolbar() {
        mToolbar.setNavigationIcon(R.drawable.chevron_selected);
        mToolbar.inflateMenu(R.menu.scan_menu_gallery);
        mToolbar.setOnMenuItemClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        capture.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mBarcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_item_gallery) {
            pickImage();
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
            InputStream inputStream = null;
            try {
                inputStream = getContentResolver().openInputStream(data.getData());
                Bitmap qrBitmap = BitmapFactory.decodeStream(inputStream);
                ScanWrapper.decodeQRCode(this, qrBitmap);
            } catch (Exception e) {
            } finally {
                try {
                    if (inputStream != null) inputStream.close();
                } catch (Exception e) {
                }
            }
        }
    }

    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }
}

