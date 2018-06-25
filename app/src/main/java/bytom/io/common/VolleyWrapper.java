package bytom.io.common;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Nil on 2018/6/11
 */
public class VolleyWrapper {
    private static VolleyWrapper mInstance;
    private RequestQueue mRequestQueue;
    private static Context sCtx;

    private VolleyWrapper(Context context) {
        sCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized VolleyWrapper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleyWrapper(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(sCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
