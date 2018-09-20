package test.verizon.com.flickrphotos.Utils;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;


public class VolleyHelper {
    private Context mContext;
    private RequestQueue mRequestQueue;
    private String mUrl;
    private static VolleyHelper mInstance;

    public VolleyHelper(Context context) {
        this.mContext = context;
        mRequestQueue = getRequestQueue(context);
    }

    public static synchronized VolleyHelper getInstance(Context context){
        if(mInstance == null){
            mInstance = new VolleyHelper(context);
        }
        return mInstance;
    }

    private RequestQueue getRequestQueue(Context context) {
        if (mRequestQueue == null) {
            Cache cache = new DiskBasedCache(context.getCacheDir(), 10 * 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());
            mRequestQueue = new RequestQueue(cache, network);
            mRequestQueue.start();
        }
        return mRequestQueue;
    }

    public  void addToRequestQueue(Request req) {
        mRequestQueue.add(req);
    }

}
