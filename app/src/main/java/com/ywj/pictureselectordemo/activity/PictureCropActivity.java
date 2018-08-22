package com.ywj.pictureselectordemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.SaveCallback;
import com.ywj.pictureselectordemo.R;
import com.ywj.pictureselectordemo.bean.LocalMedia;
import com.ywj.pictureselectordemo.dialog.SweetAlertDialog;
import com.ywj.pictureselectordemo.utils.Logger;
import com.ywj.pictureselectordemo.utils.PictureSelectUtil;
import com.ywj.pictureselectordemo.utils.ToastUtils;
import com.ywj.pictureselectordemo.widget.PictureConfig;

import java.io.File;
import java.util.ArrayList;

import static com.ywj.pictureselectordemo.activity.PictureListActivity.PICTURE_CROP_RESULT_CODE;
import static com.ywj.pictureselectordemo.utils.PictureSelectUtil.CHECKED_PICTURE;

/**
 * 裁剪图片
 * 参考：https://github.com/IsseiAoki/SimpleCropView#download
 */

public class PictureCropActivity extends Activity {

    private CropImageView image_crop;


    private Uri sourceUri;

    private String type;
    private ArrayList<LocalMedia> checkedMediaBeans;
    private PictureConfig config;
    private File tempFile;
    private File cropedFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cropphotos);

        initView();
        initData();


//        h();
    }

    private void initView() {
        image_crop = (CropImageView) findViewById(R.id.image_crop);
    }

    private void initData() {
        Intent intent = getIntent();

        checkedMediaBeans = (ArrayList<LocalMedia>) intent.getSerializableExtra(CHECKED_PICTURE);
        config = (PictureConfig) intent.getSerializableExtra(PictureSelectUtil.PICTURE_CONFIG);
        for (LocalMedia checkedMediaBean : checkedMediaBeans) {
            Logger.e("checkedMediaBeans:" + checkedMediaBean.toString());
        }
        initConfig(config);
        startLoad();
    }

    private void initConfig(PictureConfig config) {
//        image_crop.setCustomRatio(8,5);//自定义比例
//        image_crop.setOutputMaxSize(800, 800);//最大宽高
        image_crop.setCompressQuality(config.compressQuality);//质量
        image_crop.setCropMode(config.cropMode);//裁剪模式
        image_crop.setInitialFrameScale(config.initialFrameScale);//显示帧缩放
        image_crop.setOverlayColor(config.backgroundColor);//背景
//        image_crop.startLoad(sourceUri, null);//开始加载
    }

    int pictureindex = -1;

    private void startLoad() {
        pictureindex++;
        if (pictureindex < checkedMediaBeans.size()) {
            LocalMedia localMedia = checkedMediaBeans.get(pictureindex);
            String path = localMedia.getPath();
//            image_crop.startLoad(Uri.parse(path), new LoadCallback() {
            tempFile = new File(path);

            image_crop.startLoad(Uri.fromFile(tempFile),null);//开始加载
        } else {
            //over
            for (LocalMedia checkedMediaBean : checkedMediaBeans) {
                Logger.e("checkedMediaBeans:" + checkedMediaBean.toString());
            }
            ToastUtils.show(this, "裁剪完成");
            Intent intent = new Intent();
            intent.putParcelableArrayListExtra(PictureSelectUtil.CHECKED_PICTURE,checkedMediaBeans);
            setResult(PICTURE_CROP_RESULT_CODE,intent);
            finish();
        }
    }


//    /**
//     * 头像
//     */
//    private void initCropParamsForHead() {
//        image_crop.setCropMode(CropImageView.CropMode.CIRCLE);
//        image_crop.setCompressQuality(25);
//        image_crop.setOutputMaxSize(200, 200);
//        image_crop.setInitialFrameScale(0.75f);
//        image_crop.setOverlayColor(0xAA1C1C1C);
//        image_crop.startLoad(sourceUri, null);
//    }
//
//    private void initCropParamsForAuth() {
//        image_crop.setCustomRatio(8,5);
//        image_crop.setCompressQuality(100);
//        image_crop.setCropMode(CropImageView.CropMode.FREE);
//        image_crop.setOutputMaxSize(800, 800);
//        image_crop.setInitialFrameScale(0.75f);
//        image_crop.setOverlayColor(0xAA1C1C1C);
//        image_crop.startLoad(sourceUri, null);
//    }
//
//    private void initCropParamsForGoods() {
//        image_crop.setCropMode(CropImageView.CropMode.SQUARE);
//        image_crop.setCompressQuality(100);
//        image_crop.setOutputMaxSize(520, 520);
//        image_crop.setInitialFrameScale(0.75f);
//        image_crop.setOverlayColor(0xAA1C1C1C);
//        image_crop.startLoad(sourceUri, null);
//    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_check:
                cropPhotos();
                break;
            case R.id.btn_rotate:
                image_crop.rotateImage(CropImageView.RotateDegrees.ROTATE_90D); // 顺时针旋转90度
                break;
            default:
                break;
        }
    }

    private void cropPhotos() {
        showDialog("裁剪中");
        // TODO: 存储位置
//        String basePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "crop";
//        String cropPath = getFilesDir().getAbsolutePath() + File.separator + "crop" + File.separator+ System.currentTimeMillis() + tempFile.getName() ;
        String cropPath = PictureSelectUtil.basePath + File.separator+ System.currentTimeMillis() + tempFile.getName() ;

        cropedFile = new File(cropPath);

        if (!cropedFile.exists()){
            cropedFile.getParentFile().mkdirs();
            cropedFile = new File(cropPath);
        }

        sourceUri=Uri.fromFile(cropedFile);
        image_crop.startCrop(sourceUri, new CropCallback() {
            @Override
            public void onSuccess(Bitmap cropped) {
            }

            @Override
            public void onError() {

            }
        }, new SaveCallback() {
            @Override
            public void onSuccess(Uri outputUri) {
                dismiss();
                setData(outputUri);
                startLoad();
            }

            @Override
            public void onError() {
            }
        });
    }

    private void setData(Uri outputUri) {
        if (pictureindex < checkedMediaBeans.size()) {
            LocalMedia localMedia = checkedMediaBeans.get(pictureindex);
            localMedia.setCutPath(outputUri.getPath());
        }
    }

    SweetAlertDialog dialog;

    private void showDialog(String msg) {
        dialog = new SweetAlertDialog(this);
        dialog.setTitleText(msg);
        dialog.show();
    }

    private void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.cancel();
        }
    }
}
