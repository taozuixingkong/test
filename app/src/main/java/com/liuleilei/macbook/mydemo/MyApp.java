package com.liuleilei.macbook.mydemo;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.liuleilei.macbook.mydemo.activity.MainActivity;
import com.meizu.cloud.pushsdk.PushManager;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;

import static android.provider.UserDictionary.Words.APP_ID;
import static com.liuleilei.macbook.mydemo.receiver.DemoMessageReceiver.TAG;

/**
 * create by liu
 * on2019/6/15
 */
public class MyApp extends Application {
    private static final String APP_ID = "2882303761518029335";
    private static final String MEIZU_APP_ID = "121409";
    // user your appid the key.
    private static final String APP_KEY = "5301802943335";
    private static final String MEIZU_APP_KEY = "60dac95c5ca9499e89bc5fc29334ae8c";
    private static DemoHandler sHandler = null;
    private static MainActivity sMainActivity = null;
    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * @param context
         * @param appId
         *         push 平台申请的应用id
         * @param appKey
         *         push 平台申请的应用key
         * 使用说明：可在应用启动时调用此方法，例如在Application.onCreate()调用即可,魅族推送只适用于Flyme系统,因此可以先行判断是否为魅族机型，再进行订阅，避免在其他机型上出现兼容性问题
         *
        }
         * */
        if (MzSystemUtils.isBrandMeizu(this)) {
            PushManager.register(this, MEIZU_APP_ID, MEIZU_APP_KEY);
        }else if(MzSystemUtils.isXiaoMi()){

            if (shouldInit()) {
                MiPushClient.registerPush(this, APP_ID, APP_KEY);
            }

        }else {
//            if (shouldInit()) {
//                MiPushClient.registerPush(this, APP_ID, APP_KEY);
//            }
        }
        LoggerInterface newLogger = new LoggerInterface() {

            @Override
            public void setTag(String tag) {
                // ignore
            }

            @Override
            public void log(String content, Throwable t) {
                Log.d(TAG, content, t);
            }

            @Override
            public void log(String content) {
                Log.d(TAG, content);
            }
        };
        Logger.setLogger(this, newLogger);
        if (sHandler == null) {
            sHandler = new DemoHandler(getApplicationContext());
        }
    }
    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
    public static void reInitPush(Context ctx) {
        MiPushClient.registerPush(ctx.getApplicationContext(), APP_ID, APP_KEY);
    }

    public static DemoHandler getHandler() {
        return sHandler;
    }

    public static void setMainActivity(MainActivity activity) {
        sMainActivity = activity;
    }

    public static class DemoHandler extends Handler {

        private Context context;

        public DemoHandler(Context context) {
            this.context = context;
        }

        @Override
        public void handleMessage(Message msg) {
            String s = (String) msg.obj;
            if (sMainActivity != null) {
                sMainActivity.refreshLogInfo();
            }
            if (!TextUtils.isEmpty(s)) {
                Toast.makeText(context, s, Toast.LENGTH_LONG).show();
            }
        }
    }
}
