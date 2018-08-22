package com.ywj.pictureselectordemo.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.ywj.pictureselectordemo.bean.LocalMedia;
import com.ywj.pictureselectordemo.bean.LocalMediaFolder;
import com.ywj.pictureselectordemo.widget.PictureConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weijing on 2017-03-14.
 */

public class BasePictureActivity extends AppCompatActivity {

    /**当前选择的数据*/
    public static ArrayList<LocalMedia> checkedMediaBeans;
    /**当前数据集合*/
    public static List<LocalMedia> currentLocalMedias;
    /**当前文件夹*/
    public static LocalMediaFolder currentLocalMediaFolder;
    /**所有文件目录集合*/
    public static List<LocalMediaFolder> localMediaFolders;
    /**当前文件夹position*/
    public static int currentLocalMediaFolderPosition;

    /**配置*/
    public static PictureConfig config;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();

    }

    private void initDatas() {
        if (currentLocalMedias == null)
            currentLocalMedias = new ArrayList<>();
        if (localMediaFolders == null)
            localMediaFolders = new ArrayList<>();
        if (checkedMediaBeans == null)
            checkedMediaBeans = new ArrayList<>();
        if (currentLocalMediaFolder == null)
            currentLocalMediaFolder = new LocalMediaFolder();
        if (config == null)
            config = new PictureConfig();
    }


    /**
     * 动态请求权限
     *
     * @param code
     * @param permissions
     */
    protected void requestPermission(int code, String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, code);
    }

    /**
     * 针对6.0动态请求权限问题
     * 判断是否允许此权限
     *
     * @param permissions
     * @return
     */
    protected boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


}
