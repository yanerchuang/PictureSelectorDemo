package com.ywj.pictureselectordemo.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ywj.pictureselectordemo.R;
import com.ywj.pictureselectordemo.bean.LocalMedia;
import com.ywj.pictureselectordemo.bean.LocalMediaFolder;
import com.ywj.pictureselectordemo.dialog.SweetAlertDialog;
import com.ywj.pictureselectordemo.recyclerview.GridSpacingItemDecoration;
import com.ywj.pictureselectordemo.recyclerview.MyRecyclerView;
import com.ywj.pictureselectordemo.recyclerview.PictureListRecyclerAdapter;
import com.ywj.pictureselectordemo.utils.CameraUtils;
import com.ywj.pictureselectordemo.utils.LocalMediaLoader;
import com.ywj.pictureselectordemo.utils.Logger;
import com.ywj.pictureselectordemo.utils.PictureSelectUtil;
import com.ywj.pictureselectordemo.utils.ScreenUtils;
import com.ywj.pictureselectordemo.utils.ToastUtils;
import com.ywj.pictureselectordemo.widget.PictureConfig;

import java.util.ArrayList;
import java.util.List;

import static com.ywj.pictureselectordemo.utils.PictureSelectUtil.CHECKED_PICTURE;
import static com.ywj.pictureselectordemo.utils.PictureSelectUtil.PICTURE_CONFIG;


/**
 * 图片列表
 * Created by weijing on 2017-03-14.
 */

public class PictureListActivity extends BasePictureActivity implements PictureListRecyclerAdapter.RefreshListener {


    private TextView picture_tv_left, picture_tv_menu, picture_tv_title, picture_tv_right;
    private MyRecyclerView recyclerView;
    private PictureListRecyclerAdapter mAdapter;


    private Context mContext;
    private final int REQUEST_CODE = 10011;
    public static final int PICTURE_CROP_REQUEST_CODE = 10012;
    public static final int PICTURE_RESULT_CODE = 10013;
    public static final int PICTURE_CROP_RESULT_CODE = 10014;

    private boolean isNull(Object... objects) {
        for (Object o : objects) {
            if (o == null)
                return true;
        }
        return false;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recnet_picture);

        initData();
        initView();
        initRecyclerView();
        requestPermission();


    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if (isNull(picture_tv_left, picture_tv_menu, picture_tv_title, picture_tv_right, recyclerView, mAdapter)) {
            initData();
            initView();
            initRecyclerView();
        }
        requestPermission();
    }


    private void initData() {
        mContext = this;
        Intent intent = getIntent();

        ArrayList<LocalMedia> localMedias = (ArrayList<LocalMedia>) intent.getSerializableExtra(PictureSelectUtil.PICTURE_SELECT_LIST);
        if (localMedias != null) {
            checkedMediaBeans = localMedias;
        }
        PictureConfig config = (PictureConfig) intent.getSerializableExtra(PictureSelectUtil.PICTURE_CONFIG);

        if (config != null)
            this.config = config;

//        currentLocalMediaFolderPosition = intent.getIntExtra("position", 0);
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                loadData();

            } else {
                requestPermission(101, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        } else {
            loadData();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 101) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadData();
            } else {
                Toast.makeText(this, "权限拒绝", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initView() {
        picture_tv_left = (TextView) findViewById(R.id.tv_picture_left);
        picture_tv_menu = (TextView) findViewById(R.id.picture_tv_menu);
        picture_tv_title = (TextView) findViewById(R.id.tv_picture_title);
        picture_tv_right = (TextView) findViewById(R.id.tv_picture_right);

        picture_tv_left.setOnClickListener(clickListener);
        picture_tv_menu.setOnClickListener(clickListener);
        picture_tv_right.setOnClickListener(clickListener);


    }

    MyOnClickListener clickListener = new MyOnClickListener();

    class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            if (v == picture_tv_left) {
                finish();
            } else if (v == picture_tv_menu) {
                Intent intent = new Intent(mContext, PictureFolderListActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            } else if (v == picture_tv_right) {
                setResult();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == PICTURE_RESULT_CODE) {
                    setResult();

                }
                break;
            case PICTURE_CROP_REQUEST_CODE:
                if (resultCode == PICTURE_CROP_RESULT_CODE) {
                    checkedMediaBeans = (ArrayList<LocalMedia>) data.getSerializableExtra(PictureSelectUtil.CHECKED_PICTURE);
                    config.isCrop = false;
                    setResult();

                }
                break;
            case PictureSelectUtil.REQUEST_CODE_CAMERA:
                String cameraPath = CameraUtils.onCameraResult(this, PictureSelectUtil.REQUEST_CODE_CAMERA);
                LocalMedia localMedia = new LocalMedia();
                localMedia.setPath(cameraPath);
                currentLocalMedias.add(1, localMedia);
                checkedMediaBeans.add(localMedia);
                currentLocalMediaFolder.setCheckedNum(currentLocalMediaFolder.getCheckedNum()+1);
                mAdapter.bindImagesData(currentLocalMedias);
                break;
            default:
                break;
        }
    }

    private void setResult() {
        if (config.isCrop) {
            Intent intent = new Intent(this, PictureCropActivity.class);
            intent.putParcelableArrayListExtra(CHECKED_PICTURE, checkedMediaBeans);
            intent.putExtra(PICTURE_CONFIG, config);
            startActivityForResult(intent, PICTURE_CROP_REQUEST_CODE);
        } else {

            Intent intent = new Intent();
            intent.putParcelableArrayListExtra(CHECKED_PICTURE, checkedMediaBeans);
            setResult(PICTURE_RESULT_CODE, intent);
            finish();
            resetData();
        }
    }

    /**
     * 重置数据
     */
    private void resetData() {
        checkedMediaBeans = null;
        currentLocalMedias = null;
        currentLocalMediaFolder = null;
        localMediaFolders = null;
        currentLocalMediaFolderPosition = 0;
        config = null;
    }

    private void initRecyclerView() {
        recyclerView = (MyRecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(config.spanCount, ScreenUtils.dip2px(this, 2), false));
        recyclerView.setLayoutManager(new GridLayoutManager(this, config.spanCount));
        recyclerView.setLoadMore(false);


        mAdapter = new PictureListRecyclerAdapter(this);
        mAdapter.setRefreshListener(this);
        recyclerView.setAdapter(mAdapter);

    }

    /**
     * 加载数据
     */
    private void loadData() {
        if (localMediaFolders == null || localMediaFolders.size() == 0) {

            showDialog("请稍候...");

            // 取最近相册或视频数据 TYPE_IMAGE  TYPE_VIDEO
            new LocalMediaLoader(this, LocalMediaLoader.TYPE_IMAGE).loadAllImage(new LocalMediaLoader.LocalMediaLoadListener() {
                @Override
                public void loadComplete(List<LocalMediaFolder> folders) {
                    dismiss();
                    if (folders.size() > 0) {
                        localMediaFolders = folders;
                        inflateData(folders);
                        //填充已选择的图片的文件夹
                        for (LocalMedia checkedMediaBean : checkedMediaBeans) {
                            for (LocalMediaFolder localMediaFolder : localMediaFolders) {
                                String localMediaFolderName = localMediaFolder.getName();
                                String folderName = checkedMediaBean.getFolderName();
                                if (localMediaFolderName != null && localMediaFolderName.equals(folderName)) {
                                    localMediaFolder.setCheckedNum(localMediaFolder.getCheckedNum() + 1);
                                }
                            }
                        }
                    } else {
                        ToastUtils.show(mContext, "未获取到数据");
                    }
                }
            });
            Logger.d("loadData数据");
        } else {
            Logger.d("已获取的数据");
            inflateData(localMediaFolders);

        }
    }

    /**
     * 填充数据
     *
     * @param localMediaFolders
     */
    private void inflateData(List<LocalMediaFolder> localMediaFolders) {
        currentLocalMediaFolder = localMediaFolders.get(currentLocalMediaFolderPosition);
        currentLocalMedias = currentLocalMediaFolder.getImages();
        if (config.isShowCamera) {
            if (currentLocalMedias.get(0) != null) {
                currentLocalMedias.add(0, null);
            }
        }
        if (mAdapter != null)
            mAdapter.bindImagesData(currentLocalMedias);

        picture_tv_title.setText(currentLocalMediaFolder.getName());
        picture_tv_right.setText("(" + checkedMediaBeans.size() + "/" + config.maxNumber + ")" + "完成");


    }

    /**
     * 刷新数据集合
     *
     * @param localMedia
     */
    @Override
    public void refrsh(LocalMedia localMedia) {
        picture_tv_right.setText("(" + checkedMediaBeans.size() + "/" + config.maxNumber + ")" + "完成");

        //遍历判断选中
//        if (checkedMediaBeans.size() > 0) {
//            for (LocalMedia checkedMediaBean : checkedMediaBeans) {
//                for (LocalMediaFolder localMediaFolder : localMediaFolders) {
//                    for (LocalMedia localMedia : localMediaFolder.getImages()) {
//                        if (checkedMediaBean==localMedia){
//
//                        }
//                    }
//                }
//            }
//
//
//        }
        if (checkedMediaBeans.size() > 0) {
            if (checkedMediaBeans.contains(localMedia)) {
                currentLocalMediaFolder.setCheckedNum(currentLocalMediaFolder.getCheckedNum() + 1);
            } else {
                currentLocalMediaFolder.setCheckedNum(currentLocalMediaFolder.getCheckedNum() - 1);
            }
        } else {
            currentLocalMediaFolder.setCheckedNum(0);
        }


//       checkedMediaBeans;选中的对象
//       currentLocalMedias;当前对象集合
//       localMediaFolders;当前文件夹集合
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        resetData();
    }
}
