package com.liuleilei.macbook.mydemo.receiver;

import android.content.Context;
import android.util.Log;

import com.liuleilei.macbook.mydemo.R;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.MzPushMessageReceiver;
import com.meizu.cloud.pushsdk.handler.MzPushMessage;
import com.meizu.cloud.pushsdk.notification.PushNotificationBuilder;
import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import com.meizu.cloud.pushsdk.platform.message.SubTagsStatus;
import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;

/**
 * create by liu
 * on2019/6/15
 */
public class MyPushMsgReceiver extends MzPushMessageReceiver {
    @Override
    @Deprecated
    public void onRegister(Context context, String pushid) {
        //调用PushManager.register(context）方法后，会在此回调注册状态
        //应用在接受返回的pushid
        Log.v(TAG,
                "onMessage is called. " + pushid);
    }

    @Override
    public void onMessage(Context context, String s) {
        //接收服务器推送的透传消息
        Log.v(TAG,
                "onRegister is called. " + s.toString());
    }

    @Override
    @Deprecated
    public void onUnRegister(Context context, boolean b) {
        //调用PushManager.unRegister(context）方法后，会在此回调反注册状态
    }

    //设置通知栏小图标
    @Override
    public void onUpdateNotificationBuilder(PushNotificationBuilder pushNotificationBuilder) {
        //重要,详情参考应用小图标自定设置
        pushNotificationBuilder.setmStatusbarIcon(R.drawable.show);
    }

    @Override
    public void onPushStatus(Context context,PushSwitchStatus pushSwitchStatus) {
        //检查通知栏和透传消息开关状态回调
    }

    @Override
    public void onRegisterStatus(Context context,RegisterStatus registerStatus) {
        //调用新版订阅PushManager.register(context,appId,appKey)回调
        Log.v(TAG,
                "onRegisterStatus is called. " + registerStatus.getPushId());
    }

    @Override
    public void onUnRegisterStatus(Context context,UnRegisterStatus unRegisterStatus) {
        //新版反订阅回调
    }

    @Override
    public void onSubTagsStatus(Context context,SubTagsStatus subTagsStatus) {
        //标签回调
    }

    @Override
    public void onSubAliasStatus(Context context,SubAliasStatus subAliasStatus) {
        //别名回调
    }
    @Override
    public void onNotificationArrived(Context context, MzPushMessage mzPushMessage) {
        //通知栏消息到达回调，flyme6基于android6.0以上不再回调
        Log.v(TAG,
                "onNotificationArrived is called. " + mzPushMessage.getContent());
    }

    @Override
    public void onNotificationClicked(Context context, MzPushMessage mzPushMessage) {
        //通知栏消息点击回调
        Log.v(TAG,
                "onNotificationClicked is called. " + mzPushMessage.getContent());
    }

    @Override
    public void onNotificationDeleted(Context context, MzPushMessage mzPushMessage) {
        //通知栏消息删除回调；flyme6基于android6.0以上不再回调
    }
}
