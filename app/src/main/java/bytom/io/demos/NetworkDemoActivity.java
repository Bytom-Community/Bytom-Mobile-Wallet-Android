package bytom.io.demos;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import bytom.io.R;
import bytom.io.common.GsonRequest;
import bytom.io.common.VolleyWrapper;

/**
 * Created by Nil on 2018/6/18
 */
public class NetworkDemoActivity extends Activity implements View.OnClickListener {
    private TextView mResultShow;
    private Button mRequestString, mRequestJson, mRequestGson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.demos_activity_network);
        mResultShow = findViewById(R.id.result_show);
        mRequestString = findViewById(R.id.request_string);
        mRequestJson = findViewById(R.id.request_json);
        mRequestGson = findViewById(R.id.request_gson);

        mRequestString.setOnClickListener(this);
        mRequestJson.setOnClickListener(this);
        mRequestGson.setOnClickListener(this);
    }

    private void newStringRequest() {
        String url = "https://github.com";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        mResultShow.setText("StringResult>>> Response is: " + response.substring(0, 500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mResultShow.setText("That didn't work!");
            }
        });
        VolleyWrapper.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void newJsonRequest() {
        String url = "https://api.github.com/";
        // Request a string response from the provided URL.
        JsonRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        mResultShow.setText("JsonResult>>> Response is: " + response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mResultShow.setText("That didn't work!");
            }
        });
        VolleyWrapper.getInstance(this).addToRequestQueue(jsonRequest);
    }

    private void newGsonRequest() {
        String url = "https://api.github.com/";
        // Request a string response from the provided URL.
        GsonRequest jsonRequest = new GsonRequest(url, GithubBean.class, null,
                new Response.Listener<GithubBean>() {
                    @Override
                    public void onResponse(GithubBean response) {
                        // Display the first 500 characters of the response string.
                        mResultShow.setText("JsonResult>>> GithubBean: " + response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mResultShow.setText("That didn't work!");
            }
        });
        VolleyWrapper.getInstance(this).addToRequestQueue(jsonRequest);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.request_json: {
                newJsonRequest();
                break;
            }

            case R.id.request_string: {
                newStringRequest();
                break;
            }

            case R.id.request_gson: {
                newGsonRequest();
                break;
            }

            default:
                break;
        }
    }
}
