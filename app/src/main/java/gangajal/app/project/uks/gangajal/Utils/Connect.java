package gangajal.app.project.uks.gangajal.Utils;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by WIND on 5/14/2018.
 */

public class Connect {
    public static final int GET = Request.Method.GET;
    public static final int POST = Request.Method.POST;
    protected static RequestQueue mQueue;
    static Context mContext;
    private static Connect mSharedInstance = null;

    private Connect(Context context) {
        mQueue = Volley.newRequestQueue(context);
    }

    public static Connect getInstance(Context context) {
        mContext = context;
        if (mSharedInstance == null) {
            mSharedInstance = new Connect(context);
        }
        return mSharedInstance;
    }

    public void doNetworkRequest(int type, String url, final HashMap<String, String> params, Response.Listener requestListener, Response.ErrorListener errorListener) {
        StringRequest mRequest = new StringRequest(type, url, requestListener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();

                return headers;
            }
        };
        int socketTimeout = 500000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        mRequest.setRetryPolicy(policy);
        mRequest.setShouldCache(false);
        mQueue.add(mRequest);
    }
}
