package bytom.io.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import bytom.io.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SiteFragment extends Fragment {

    private static final String TAG = "SiteFragment";

    private EditText mSiteUrl;
    private Button mDefaultSite;
    private Button mSubmit;


    public SiteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_site, container, false);
        mSiteUrl = v.findViewById(R.id.edit_text_site_url);
        mSiteUrl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i(TAG, "text changed: " + s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, "after text changed: " + s.toString());

            }
        });
        mDefaultSite = v.findViewById(R.id.button_default_site);
        mDefaultSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSiteUrl.setText(R.string.www_bytom_io);
            }
        });
        mSubmit = v.findViewById(R.id.button_submit);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO update site config
                Log.i(TAG, "submit new site: " + mSiteUrl.getText());
            }
        });
        return v;
    }

}
