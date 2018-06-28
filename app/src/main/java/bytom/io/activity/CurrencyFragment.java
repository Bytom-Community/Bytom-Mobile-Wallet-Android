package bytom.io.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import bytom.io.R;

/**
 * create an instance of this fragment.
 */
public class CurrencyFragment extends Fragment {

    private static final String TAG = "CurrencyFragment";

    private ListView mListview;

    private String[] mCurrency = new String[]{};
    private int mSelectedPosition;

    public CurrencyFragment() {
        // Required empty public constructor
    }

/*    public static CurrencyFragment newInstance() {
        CurrencyFragment fragment = new CurrencyFragment();
        return fragment;
    }*/

    final private AdapterView.OnItemClickListener mOnClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            onListItemClick((ListView) parent, v, position, id);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mCurrency = getResources().getStringArray(R.array.currency);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_currency, container, false);
        mListview = v.findViewById(R.id.currency_list);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListview.setAdapter(new ArrayAdapter<String>(getActivity(),
                bytom.io.R.layout.simple_list_item_single_choice, mCurrency));
        mListview.setItemsCanFocus(false);
        mListview.setOnItemClickListener(mOnClickListener);
        // TODO load default selected position
        mListview.setItemChecked(1, true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_currency, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_save:
                // TODO save selected position with mSelectedPosition
                Log.i(TAG, "save selected position!");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.i(TAG, "onListItemClick position = " + position);
        mSelectedPosition = position;
    }
}
