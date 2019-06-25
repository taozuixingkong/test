package com.liuleilei.macbook.basedispose.util;

import android.content.Context;
import android.widget.Toast;

/**
 * create by liu
 * on2019/6/25
 */
public class ToastUtil {
    public static void showToastShort(Context context, String content){
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

    public static void showToastLong(Context context, String content){
        Toast.makeText(context, content, Toast.LENGTH_LONG).show();
    }
}
