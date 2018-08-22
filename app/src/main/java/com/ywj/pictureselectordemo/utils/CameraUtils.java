package com.ywj.pictureselectordemo.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.io.File;

/**
 * 照相工具类
 */
public class CameraUtils {

    public static final int Req_Camera = 5;
    public static String cameraPath ;

    /**
     * 检查照相权限
     *
     * @param myActivity
     * @return
     */
    public static boolean checkCameraPermisson(Activity myActivity) {
        if (ContextCompat.checkSelfPermission(myActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(myActivity, new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, Req_Camera);
            return false;
        }
        return true;
    }

    /**
     * 开启照相
     */
    public static void startCamera(Activity activity) {
        if (checkCameraPermisson(activity)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraPath = PictureSelectUtil.basePath + File.separator + "photo" + System.currentTimeMillis() + ".jpg";
            File file = new File(cameraPath);
            Uri uri = FileProvider7.getUriForFile(activity, file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            activity.startActivityForResult(intent, PictureSelectUtil.REQUEST_CODE_CAMERA );
        }
    }

    public static String onCameraResult(Activity activity, int requestCode) {
        if (requestCode==PictureSelectUtil.REQUEST_CODE_CAMERA ){
            return cameraPath;
        }
        return null;
    }


    /**
     * 请求存储权限，在华为等6.0手机适配
     */
    public static void checkStoragePer(Activity activity) {
        try {
            final int REQUEST_EXTERNAL_STORAGE = 1;
            String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

            int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
