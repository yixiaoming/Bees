package org.yxm.lib.volley;

import android.app.Activity;
import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.BaseHttpStack;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

public class VolleyManager {

    private static final int DEFAULT_CACHE_SIZE = 10 * 1024 * 1024;
    private static VolleyManager sInstance;
    private static Context mAppContext;

    private RequestQueue mRequestQueue;
    private Cache mDiskCache;
    private Network mNetwork;

    private VolleyManager(Context context, int cacheSize, BaseHttpStack stack) {
        if (context instanceof Activity) {
            mAppContext = context.getApplicationContext();
        } else {
            mAppContext = context;
        }
        initRequestQueue(cacheSize, stack);
    }

    private void initRequestQueue(int cacheSize, BaseHttpStack stack) {
        mDiskCache = new DiskBasedCache(mAppContext.getCacheDir(), cacheSize);
        mNetwork = new BasicNetwork(stack);
        mRequestQueue = new RequestQueue(mDiskCache, mNetwork);
        mRequestQueue.start();
    }

    public static void init(Context appContxt) {
        init(appContxt, DEFAULT_CACHE_SIZE);
    }

    public static void init(Context appContext, int cacheSize) {
        init(appContext, cacheSize, new HurlStack());
    }

    public static void init(Context appContext, int cacheSize, BaseHttpStack stack) {
        if (sInstance == null) {
            sInstance = new VolleyManager(appContext, cacheSize, stack);
        } else {
            throw new AlreadyInitException();
        }
    }

    public static VolleyManager getInstance() {
        if (sInstance == null) {
            throw new NotInitException();
        }
        return sInstance;
    }

    public RequestBuilder getRequest(String url) {
        return new RequestBuilder(Request.Method.GET, url);
    }

    public RequestBuilder postRequest(String url) {
        return new RequestBuilder(Request.Method.POST, url);
    }

    public class RequestBuilder {
        private final String url;
        private int method = Request.Method.GET;
        private Response.Listener successListener;
        private Response.ErrorListener errorListener;
        private JSONObject postJsonObjParams;
        private JSONArray postJsonArrParams;

        public RequestBuilder(int method, String url) {
            this.method = method;
            this.url = url;
        }

        /**
         * 请求类型
         * @param method Request.Method.*
         *               int DEPRECATED_GET_OR_POST = -1;
         *               int GET = 0;
         *               int POST = 1;
         *               int PUT = 2;
         *               int DELETE = 3;
         *               int HEAD = 4;
         *               int OPTIONS = 5;
         *               int TRACE = 6;
         *               int PATCH = 7;
         * @return
         */
        public RequestBuilder method(int method) {
            this.method = method;
            return this;
        }

        public RequestBuilder body(JSONObject params) {
            postJsonObjParams = params;
            return this;
        }

        public RequestBuilder body(JSONArray params) {
            postJsonArrParams = params;
            return this;
        }

        public <T> RequestBuilder listener(Response.Listener<T> listener) {
            this.successListener = listener;
            return this;
        }

        public RequestBuilder errorListener(Response.ErrorListener listener) {
            this.errorListener = listener;
            return this;
        }

        private StringRequest createStringRequest() {
            StringRequest request = new StringRequest(method, url, successListener, errorListener);
            return request;
        }

        private JsonObjectRequest createJsonObjectRequest() {
            return new JsonObjectRequest(
                    method, url, postJsonObjParams, successListener, errorListener
            );
        }

        private JsonArrayRequest createJsonArrayRequest() {
            return new JsonArrayRequest(
                    method, url, postJsonArrParams, successListener, errorListener
            );
        }

        public void start() {
            if (postJsonObjParams != null && postJsonArrParams != null) {
                throw new RuntimeException("Cann't choose which post params!");
            }
            if (postJsonObjParams != null) {
                mRequestQueue.add(createJsonObjectRequest());
            } else if (postJsonArrParams != null) {
                mRequestQueue.add(createJsonArrayRequest());
            } else {
                mRequestQueue.add(createStringRequest());
            }
        }
    }

    public static class NotInitException extends RuntimeException {
        public NotInitException() {
            super("Must call VolleyManager.init(appContxt) before you use it!");
        }
    }

    public static class AlreadyInitException extends RuntimeException {
        public AlreadyInitException() {
            super("Can only be init once!");
        }
    }
}
