package org.yxm.modules.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import org.yxm.modules.base.R;

public class WebViewActivity extends SwipeCloseActivity {

    public static final String PARAM_URL = "url";

    private WebView mWebview;
    private WebViewClient mWebviewClient;
    private WebChromeClient mWebChromeClient;

    private Toolbar mToolbar;
    private ProgressBar mProgressbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity_layout);
        initViews();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }

        String url = getIntent().getStringExtra(PARAM_URL);
        if (url != null) {
            mWebview.loadUrl(url);
        } else {
            throw new RuntimeException("open webview without url!!!");
        }
    }

    private void initViews() {
        mToolbar = findViewById(R.id.webview_toolbar);
        setSupportActionBar(mToolbar);
        mProgressbar = findViewById(R.id.webview_progress);

        mWebview = findViewById(R.id.webview);
        WebSettings settings = mWebview.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebviewClient = new MyWebviewClient();
        mWebChromeClient = new MyWebChromeClient();
        mWebview.setWebViewClient(mWebviewClient);
        mWebview.setWebChromeClient(mWebChromeClient);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebview.destroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class MyWebviewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                mProgressbar.setVisibility(View.GONE);
            } else {
                mProgressbar.setVisibility(View.VISIBLE);
                mProgressbar.setProgress(newProgress);
            }
        }
    }
}
