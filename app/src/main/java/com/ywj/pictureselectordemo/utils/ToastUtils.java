package com.ywj.pictureselectordemo.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by weijing on 2017-03-16.
 */

public class ToastUtils {
    static Toast toast;

    public static void show(Context context, String msg) {
        if (toast == null)
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
    }
}
