package com.ywj.pictureselectordemo.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * Created by dgy on 17/8/1.
 *
 * Android7.0文件访问行为变更<br/>
 * 详见 http://blog.csdn.net/lmj623565791/article/details/72859156
 *
 */
public class FileProvider7 {

    /**
     * 获取文件uri 适配7.0
     */
    public static Uri getUriForFile(Context context, File file) {
        Uri fileUri = null;
        if (Build.VERSION.SDK_INT >= 24) {
            fileUri = FileProvider.getUriForFile(context, context.getPackageName() + ".android7.fileprovider", file);
        } else {
            fileUri = Uri.fromFile(file);
        }
        return fileUri;
    }

    /**
     * apk安装适配7.0
     */
    public static Intent installAPK(Context context, File apkFile) {
        Intent installIntent = new Intent(Intent.ACTION_VIEW);
        installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        setIntentDataAndType(context, installIntent, "application/vnd.android.package-archive", apkFile);
        context.startActivity(installIntent);
        return installIntent;
    }

    /**
     * apk安装intent适配7.0
     */
    private static void setIntentDataAndType(Context context, Intent intent, String type, File apkfile) {
        intent.setDataAndType(getUriForFile(context, apkfile), type);
        if (Build.VERSION.SDK_INT >= 24) {
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION| Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
    }
}