package com.liuleilei.macbook.mydemo.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.liuleilei.macbook.basedispose.util.ToastUtil;
import com.liuleilei.macbook.mydemo.R;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * create by liu
 * on2019/6/25
 * 请求https 添加证书
 */
public class HttpUrlActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView requestText;
    private KProgressHUD kProgressHUD;

    public static void start(Context context) {
        Intent starter = new Intent(context, HttpUrlActivity.class);
       // starter.putExtra();
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_url);
        requestText = findViewById(R.id.requestText);
        requestText.setOnClickListener(this);
        kProgressHUD = KProgressHUD.create(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.requestText:
                reQuest();
                break;
            default:
                break;
        }
    }
    //  从文件中读取地址数据
    private void reQuest() {
        kProgressHUD.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //第一个参数为 返回实现指定安全套接字协议的SSLContext对象。第二个为提供者

                    TrustManager[] tm = new TrustManager[]{
                            new X509TrustManager() {
                                @Override
                                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                                }

                                @Override
                                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                                }

                                @Override
                                public X509Certificate[] getAcceptedIssuers() {
                                    return new X509Certificate[0];
                                }
                            }
                    };
                    SSLContext sslContext = SSLContext.getInstance("SSL");
                    sslContext.init(null, tm, new SecureRandom());
                    SSLSocketFactory ssf = sslContext.getSocketFactory();
                    HostnameVerifier ignoreHostnameVerifier = new HostnameVerifier() {
                        @Override
                        public boolean verify(String s, SSLSession sslsession) {
                            System.out.println("WARNING: Hostname is not matched for cert.");
                            return true;
                        }
                    };
                    HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
                    HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

                    HttpURLConnection conn = (HttpURLConnection) new URL("https://static.doyure.com/data/area.json").openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5 * 1000);
                    //conn.connect();
                    InputStream is = conn.getInputStream();
                    long leeee = conn.getContentLength();
                    StringBuffer sb = new StringBuffer();
                    int len = -1;
                    byte[] buf = new byte[1024];
                    while ((len = is.read(buf)) != -1) {
                        sb.append(new String(buf, 0, len, "gbk"));
                    }
                    is.close();
                    conn.disconnect();
                   // provinces = JSON.parseArray(sb.toString(), City.class);
                    kProgressHUD.dismiss();
                    HttpUrlActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showToastShort(HttpUrlActivity.this,"success");
                        }
                    });
                } catch (IOException e) {
                    kProgressHUD.dismiss();
                    e.printStackTrace();
                }catch (Exception e){
                    kProgressHUD.dismiss();
                    e.printStackTrace();
                }
            }
        }).start();
    }



    public void showBottomToCenterPopUpWindow(View view) {
        ToastUtil.showToastShort(this, "haha");
        View contentView = LayoutInflater.from(this).inflate(R.layout.modal_test, null);
        PopupWindow popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);

    }
    /*
    public void showBottomToCenterModal() {
        ToastUtil.showToastShort(this, "haha");
        View contentView = LayoutInflater.from(this).inflate(R.layout.modal_test, null);
        contentView.setBackgroundColor(Color.RED);
        Modal modal = new Modal(this);
        modal.show(contentView, ModalDirection.FROM_BOTTOM, true,true,0, 0,
                GlobalManager.windowHeight, getWindow().getDecorView().getHeight() / 2 - DensityUtil.dp2px(200)/2, 1, 1, 1, 1);
        modal.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {

                    @Override
                    public boolean onPreDraw() {
                        modal.getViewTreeObserver().removeOnPreDrawListener(this);
                        modal.getWidth(); // 获取宽度
                        modal.getHeight(); // 获取高度
                        return true;
                    }
                });
        int decorViewHeight = getWindow().getDecorView().getHeight();
        int _navigationBarHeight = _navigationBar.getHeight();
        int contentViewHeight  = _contentView.getHeight();
        //int ActivityHeight = this.getDelegate().s
        int sattusBarheight = getResources().getDimensionPixelSize(getResources().getIdentifier("status_bar_height", "dimen", "android"));
    }
    */
}
