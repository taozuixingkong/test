package com.liuleilei.macbook.mydemo.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youngkaaa.ycircleview.CircleView;
import com.liuleilei.macbook.basedispose.base.BaseActivity;
import com.liuleilei.macbook.basedispose.util.ToastUtil;
import com.liuleilei.macbook.mydemo.R;

/**
 * create by liu
 * on2019/7/17
 * webView
 */
public class WebViewActivity extends BaseActivity implements View.OnClickListener {
    private WebView webView;
    private long exitTime = 0;
    private ImageView refreshImageView;

    public static void start(Context context) {
        Intent starter = new Intent(context, WebViewActivity.class);
        // starter.putExtra();
        context.startActivity(starter);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addView(R.layout.activity_web_view);
        setStatusBarColor(Color.WHITE,0);
        refreshImageView = findViewById(R.id.refresh_image_view);
        webView = findViewById(R.id.webView);
       // CircleView
        initWebView();
        initEvent();

    }

    private void initEvent() {
        leftTextView.setOnClickListener(this);
        refreshImageView.setOnClickListener(this);
        rightTextView.setOnClickListener(this);
    }

    private void initWebView() {
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                titleTextView.setText(title);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            //设置在webView点击打开的新网页在当前界面显示,而不跳转到新的浏览器中
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        //设置WebView属性,运行执行js脚本
        webView.getSettings().setJavaScriptEnabled(true);
        //调用loadUrl方法为WebView加入链接
        webView.loadUrl("https://www.jianshu.com/u/6e1a8019b9ce");
    }

    //我们需要重写回退按钮的时间,当用户点击回退按钮：
    //1.webView.canGoBack()判断网页是否能后退,可以则goback()
    //2.如果不可以连续点击两次退出App,否则弹出提示Toast
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtil.showToastShort(this, "再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_text_view:
                finish();
                break;
            case R.id.right_text_view:
                webView.reload();
                break;
            case R.id.refresh_image_view:
                webView.setScaleY(0);
                break;
            default:
                break;
        }
    }
}
