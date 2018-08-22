package com.ywj.pictureselectordemo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.ywj.pictureselectordemo.R;
import com.ywj.pictureselectordemo.bean.LocalMedia;
import com.ywj.pictureselectordemo.utils.PictureSelectUtil;
import com.ywj.pictureselectordemo.utils.ToastUtils;
import com.ywj.pictureselectordemo.view.FixedViewPager;

import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by weijing on 2017-03-15.
 */

public class PictureViewPagerActivity extends BasePictureActivity {

    private FixedViewPager viewpager;
    private int position;
    private TextView tv_picture_title, tv_picture_right, tv_picture_left;


    private Context mContext;
    private Activity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        mContext = this;
        mActivity = this;
        initView();
        initData();
        initListener();


    }

    private void initView() {
        viewpager = (FixedViewPager) findViewById(R.id.viewpager);
        tv_picture_left = (TextView) findViewById(R.id.tv_picture_left);
        tv_picture_title = (TextView) findViewById(R.id.tv_picture_title);
        tv_picture_right = (TextView) findViewById(R.id.tv_picture_right);
    }

    private void initData() {

        Intent intent = getIntent();

        position = intent.getIntExtra(PictureSelectUtil.LOCAL_MEDIA_POSITION, 0);

        viewpager.setAdapter(new ViewPagerAdapter(this, currentLocalMedias));
        viewpager.setCurrentItem(position);
        syncState();
    }

    private void initListener() {
        tv_picture_left.setOnClickListener(onClickListener);
        tv_picture_right.setOnClickListener(onClickListener);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                PictureViewPagerActivity.this.position = position;
                syncState();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 同步状态
     */
    private void syncState() {
        tv_picture_title.setText(position + 1 + "/" + currentLocalMedias.size());
        if (checkedMediaBeans.contains(currentLocalMedias.get(position))) {
            tv_picture_right.setText("(" + checkedMediaBeans.size() + "/" + config.maxNumber + ")移除 ");
        } else {
            tv_picture_right.setText("(" + checkedMediaBeans.size() + "/" + config.maxNumber + ")添加 ");
        }
    }

    MyOnClickListener onClickListener = new MyOnClickListener();

    class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v == tv_picture_left) {
                finish();
            } else if (v == tv_picture_right) {
                if (checkedMediaBeans.contains(currentLocalMedias.get(position))) {
                    checkedMediaBeans.remove(currentLocalMedias.get(position));
                } else {
                    if (checkedMediaBeans.size() < config.maxNumber) {

                        checkedMediaBeans.add(currentLocalMedias.get(position));
                    } else {
                        ToastUtils.show(mContext, "已选择" + config.maxNumber + "张图片");
                    }
                }
                syncState();
//                setResult(PictureListActivity.PICTURE_RESULT_CODE, new Intent());
//                finish();
            }
        }
    }

    class ViewPagerAdapter extends PagerAdapter {
        private List<LocalMedia> mediaBeanList;
        private Context context;

        public ViewPagerAdapter(Context context, List<LocalMedia> mediaBeanList) {
            this.mediaBeanList = mediaBeanList;
            this.context = context;
        }

        @Override
        public int getCount() {

            return mediaBeanList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(mContext, R.layout.item_image, null);
            final ImageView imageView = (ImageView) view.findViewById(R.id.imageView);

            Glide.with(context)
                    .load(mediaBeanList.get(position).getPath())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(new GlideDrawableImageViewTarget(imageView) {
                              @Override
                              public void onResourceReady(GlideDrawable drawable, GlideAnimation anim) {
                                  super.onResourceReady(drawable, anim);
                                  //在这里添加一些图片加载完成的操作
                                  new PhotoViewAttacher(imageView);
                              }
                          }
                        );
//                    .into(imageView);
            container.addView(view);
            return view;
//            final ImageView imageView = new ImageView(context);
//            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            PhotoViewAttacher mAttacher = new PhotoViewAttacher(imageView);
//            Glide.with(context)
//                    .load(mediaBeanList.get(position).getPath())
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//                    .skipMemoryCache(true)
//                    .into(imageView);
//            mAttacher.update();
//            container.addView(imageView);
//            return imageView;


//            ZoomImageView myZoomImageView = new ZoomImageView(context);
//            myZoomImageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
////            myZoomImageView.setScaleType(ImageView.ScaleType.CENTER);
//
//            Glide.with(context).load(mediaBeanList.get(position).getPath()).into(myZoomImageView);
//            container.addView(myZoomImageView);
//            return myZoomImageView;


        }
//
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            final ImageView imageView = new ImageView(context);
//            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            final PhotoViewAttacher mAttacher = new PhotoViewAttacher(imageView);
//            Glide.with(context)
//                    .load(mediaBeanList.get(position).getOriginalPath())
////                    .into(imageView);
//                    .into(new SimpleTarget<GlideDrawable>(480, 800) {
//                        @Override
//                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
//                            imageView.setImageDrawable(resource);
//                            mAttacher.update();
//                        }
//                    });
//            mAttacher.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
//                @Override
//                public void onViewTap(View view, float x, float y) {
//                    finish();
//                }
//            });
//            container.addView(imageView);
//
//            return imageView;
////            ZoomImageView myZoomImageView = (ZoomImageView) LayoutInflater.from(context).inflate(R.layout.item_image, null);
////            Glide.with(context).load(mediaBeanList.get(position).getOriginalPath()).into(myZoomImageView);
////            container.addView(myZoomImageView);
////            return myZoomImageView;
//        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
}
