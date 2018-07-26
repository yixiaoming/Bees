package org.yxm.bees.module.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import org.yxm.bees.R;

public class WebViewActivity extends AppCompatActivity {

    public static final String PARAM_URL = "url";

    private WebView mWebview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity_layout);
        initViews();
        String url = getIntent().getStringExtra(PARAM_URL);
        if (url != null) {
            mWebview.loadUrl(url);
        } else {
            throw new RuntimeException("open webview without url!!!");
        }
    }

    private void initViews() {
        mWebview = findViewById(R.id.webview);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
