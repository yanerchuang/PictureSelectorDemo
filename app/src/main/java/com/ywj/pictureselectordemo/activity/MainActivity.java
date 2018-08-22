package com.ywj.pictureselectordemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.ywj.pictureselectordemo.R;
import com.ywj.pictureselectordemo.bean.LocalMedia;
import com.ywj.pictureselectordemo.utils.Logger;
import com.ywj.pictureselectordemo.utils.PictureSelectUtil;
import com.ywj.pictureselectordemo.utils.ScreenUtils;
import com.ywj.pictureselectordemo.utils.ToastUtils;
import com.ywj.pictureselectordemo.widget.PictureConfig;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private LinearLayout ll_container;
    private List<LocalMedia> checkedMediaBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ll_container = (LinearLayout) findViewById(R.id.ll_container);
    }

    public void select(View view) {
        PictureConfig pictureConfig = new PictureConfig();
        pictureConfig.maxNumber = 4;
        pictureConfig.spanCount = 3;
        pictureConfig.isCrop = true;
        pictureConfig.isShowCamera=true;

        PictureSelectUtil.start(this, pictureConfig, checkedMediaBeans);
    }

    public void clear(View view) {
        if (checkedMediaBeans != null)
            checkedMediaBeans.clear();
        ll_container.removeAllViews();
//        PictureSelectUtil.clearCache(this);
        final String s = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "crop";
        deleteDir(s);
        ToastUtils.show(this,"已清空");
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        checkedMediaBeans = PictureSelectUtil.onActivityResult(requestCode, resultCode, data);
        if (checkedMediaBeans != null) {
            for (LocalMedia checkedMediaBean : checkedMediaBeans) {
                ImageView imageView = new ImageView(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ScreenUtils.dip2px(this, 200), ScreenUtils.dip2px(this, 200));
                imageView.setLayoutParams(params);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(this).load(checkedMediaBean.getCutPath()).into(imageView);
                ll_container.addView(imageView);

            }
            Logger.e(checkedMediaBeans.size() + "张");
        }
    }



}
