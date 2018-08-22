package com.ywj.pictureselectordemo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Parcelable;

import com.ywj.pictureselectordemo.activity.PictureListActivity;
import com.ywj.pictureselectordemo.bean.LocalMedia;
import com.ywj.pictureselectordemo.widget.PictureConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by weijing on 2017-08-08 09:13.
 */

public class PictureSelectUtil {

    public static String basePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "crop";
    /**
     * 选择的图片集合
     */
    public static final String CHECKED_PICTURE = "CHECKED_PICTURE";
    /**
     * 图片配置
     */
    public static final String PICTURE_CONFIG = "PICTURE_CONFIG";
    /**
     * 已选择图片集合
     */
    public static final String PICTURE_SELECT_LIST = "PICTURE_SELECT_LIST";


    public static final String LOCAL_MEDIAS = "LOCAL_MEDIAS";
    public static final String LOCAL_MEDIA_POSITION = "LOCAL_MEDIA_POSITION";


    public static final int REQUEST_CODE_CAMERA = 10101;
    public static final int REQUEST_CODE_SELECT_PICTURE = 10102;

    /**
     * 开始选择图片
     */
    public static void start(Activity activity, PictureConfig pictureConfig) {
        Intent intent = new Intent(activity, PictureListActivity.class);
        intent.putExtra(PICTURE_CONFIG, pictureConfig);
        activity.startActivityForResult(intent, REQUEST_CODE_SELECT_PICTURE);
    }

    /**
     * 开始选择图片
     */
    public static void start(Activity activity, PictureConfig pictureConfig, List<LocalMedia> checkedMediaBeans) {
        Intent intent = new Intent(activity, PictureListActivity.class);
        intent.putExtra(PICTURE_CONFIG, pictureConfig);
        intent.putParcelableArrayListExtra(PICTURE_SELECT_LIST, (ArrayList<? extends Parcelable>) checkedMediaBeans);
        activity.startActivityForResult(intent, REQUEST_CODE_SELECT_PICTURE);
    }

    /**
     * 选择图片结果返回
     */
    public static ArrayList<LocalMedia> onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_SELECT_PICTURE) {
            if (data != null) {
                return (ArrayList<LocalMedia>) data.getSerializableExtra(CHECKED_PICTURE);
            }
        }
        return null;
    }

    /**
     * 清除缓存
     */
    public static boolean clearCache(Context context) {
//        String cropPath = context.getFilesDir().getAbsolutePath() + File.separator + "crop";
        return deleteDir(new File(basePath));
    }

    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (String child : children) {
                boolean success = deleteDir(new File(dir, child));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
    private static boolean deleteDir(String path) {
        File dir = new File(path);
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (String child : children) {
                boolean success = deleteDir(path+File.separator+child);
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
}
