package com.ywj.pictureselectordemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.ywj.pictureselectordemo.R;
import com.ywj.pictureselectordemo.recyclerview.MyRecyclerView;
import com.ywj.pictureselectordemo.recyclerview.PictureFolderRecyclerAdapter;
import com.ywj.pictureselectordemo.recyclerview.RecycleViewDivider;
import com.ywj.pictureselectordemo.utils.ScreenUtils;

/**
 * 图片文件夹列表
 * Created by weijing on 2017-03-16.
 */
public class PictureFolderListActivity extends BasePictureActivity{

    private MyRecyclerView recyclerView;
    private TextView tv_picture_title,tv_picture_left,tv_picture_right;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_list);
        mContext=this;


        recyclerView = (MyRecyclerView) findViewById(R.id.recyclerView);
        tv_picture_left = (TextView) findViewById(R.id.tv_picture_left);
        tv_picture_left.setOnClickListener(onClickListener);
        tv_picture_right = (TextView) findViewById(R.id.tv_picture_right);
        tv_picture_right.setOnClickListener(onClickListener);
        tv_picture_right.setText("(" + checkedMediaBeans.size() + "/" + config.maxNumber + ")" + "完成");
        tv_picture_title = (TextView) findViewById(R.id.tv_picture_title);
        tv_picture_title.setText("图片列表");

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL,
                ScreenUtils.dip2px(this, 0.5f), ContextCompat.getColor(this, R.color.grey_eee)));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        PictureFolderRecyclerAdapter adapter = new PictureFolderRecyclerAdapter(this, localMediaFolders);
        recyclerView.setAdapter(adapter);
        recyclerView.setLoadMore(false);

    }
    MyOnClickListener onClickListener=new MyOnClickListener();
    class  MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if (v==tv_picture_left){
                finish();
            }else if (v==tv_picture_right){
                setResult(PictureListActivity.PICTURE_RESULT_CODE,new Intent());
                finish();
            }
        }
    }
}
