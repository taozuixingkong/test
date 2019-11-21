package com.liuleilei.macbook.mydemo.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liuleilei.macbook.basedispose.util.ToastUtil;
import com.liuleilei.macbook.mydemo.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * create by liu
 * on2019/6/22
 * asyncTask
 */
public class DownLoadActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int FILE_SIZE = 0;
    private static final int DOWNLOAD_PROGRESS = 1;
    private static final int DOWNLOAD_SUCCESS = 2;
    private TextView TvUpload;
    private RelativeLayout RlUpload;
    private ProgressBar PbUpload;
    private String url = "http://download.doyure.com/tts.apk";
    private String mSavePath;
    private int mProgress;
    private boolean mIsCancel = false;
    private String apkName = "xiaoai.apk";
    private TextView TvUploadAsyncTas;
    private RelativeLayout RlUploadAsyncTas;
    private ProgressBar PbUploadAsyncTas;

    public static void start(Context context) {
        Intent starter = new Intent(context, DownLoadActivity.class);
        //starter.putExtra();
        context.startActivity(starter);

    }

    private Handler mUpdateProgressHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    // 设置进度条
                    PbUpload.setProgress(mProgress);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        TvUpload = findViewById(R.id._tv_upload);
        RlUpload = findViewById(R.id._rl_upload);
        PbUpload = findViewById(R.id._pb_upload);
        TvUploadAsyncTas = findViewById(R.id._tv_upload_async_tas);
        RlUploadAsyncTas = findViewById(R.id._rl_upload_async_tas);
        PbUploadAsyncTas = findViewById(R.id._pb_upload_async_tas);
        RlUpload.setOnClickListener(this);
        RlUploadAsyncTas.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id._rl_upload:
                checkPermission();
                break;
            case R.id._rl_upload_async_tas:
                new DownloadAsyncTas().execute(url);
                break;
            default:
                break;
        }
    }

    private void checkPermission() {
        //如果是6.0以上的
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    DownLoadActivity.this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }
        downloadAPK();
    }

    private void downloadAPK() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        String sdPath = Environment.getExternalStorageDirectory() + "/";
//                      文件保存路径
                        mSavePath = sdPath + "Apk";

                        File dir = new File(mSavePath);
                        if (!dir.exists()) {
                            dir.mkdir();
                        }
                        // 下载文件
                        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
                        conn.connect();
                        InputStream is = conn.getInputStream();
                        int length = conn.getContentLength();

                        File apkFile = new File(mSavePath, apkName);
                        FileOutputStream fos = new FileOutputStream(apkFile);

                        int count = 0;
                        byte[] buffer = new byte[1024];
                        while (!mIsCancel) {
                            int numread = is.read(buffer);
                            count += numread;
                            // 计算进度条的当前位置
                            mProgress = (int) (((float) count / length) * 100);
                            // 更新进度条
                            mUpdateProgressHandler.sendEmptyMessage(1);

                            // 下载完成
                            if (numread < 0) {
                                mUpdateProgressHandler.sendEmptyMessage(2);
                                break;
                            }
                            fos.write(buffer, 0, numread);
                        }
                        fos.close();
                        is.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    class DownloadAsyncTas extends AsyncTask<String, Integer, Integer> {

        @Override
        protected Integer doInBackground(String... strings) {
            String url = strings[0];
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String sdPath = Environment.getExternalStorageDirectory() + "/";
//                      文件保存路径
                mSavePath = sdPath + "Apk";

                File dir = new File(mSavePath);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                try {
                    // 下载文件
                    HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    int length = conn.getContentLength();

                    File apkFile = new File(mSavePath, apkName);
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int numread = -1;
                    int count = 0;
                    byte[] buffer = new byte[1024];
                    while ((numread = is.read(buffer)) != -1) {
                        count += numread;
                        fos.write(buffer, 0, numread);
                        // 计算进度条的当前位置
                        mProgress = (int) (((float) count / length) * 100);
                        // 更新进度条
                        publishProgress(DOWNLOAD_PROGRESS, mProgress);
                        fos.flush();
                    }
                    fos.close();
                    is.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return DOWNLOAD_SUCCESS;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            switch (values[0]) {
                case DOWNLOAD_PROGRESS:
                    PbUploadAsyncTas.setProgress(values[1]);
                    break;
                default:
                    break;
            }
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if (integer == DOWNLOAD_SUCCESS) {
                ToastUtil.showToastShort(DownLoadActivity.this, "下载完成");
            }
        }
    }
}
