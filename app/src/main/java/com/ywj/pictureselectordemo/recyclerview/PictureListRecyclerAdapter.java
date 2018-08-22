package com.ywj.pictureselectordemo.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ywj.pictureselectordemo.R;
import com.ywj.pictureselectordemo.activity.PictureListActivity;
import com.ywj.pictureselectordemo.activity.PictureViewPagerActivity;
import com.ywj.pictureselectordemo.bean.LocalMedia;
import com.ywj.pictureselectordemo.utils.CameraUtils;
import com.ywj.pictureselectordemo.utils.PictureSelectUtil;
import com.ywj.pictureselectordemo.utils.ScreenUtils;
import com.ywj.pictureselectordemo.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 最近图片适配器
 * 作者：ywj on 2016/8/25 0025 11:11
 * 邮箱：ywj_and@163.com
 */
public class PictureListRecyclerAdapter extends RecyclerView.Adapter<PictureListRecyclerAdapter.MyViewHolder> {
    private List<LocalMedia> localMedias;
    private Context mContext;
    private PictureListActivity mActivity;


    public PictureListRecyclerAdapter(PictureListActivity activity) {
        this.mActivity = activity;
        localMedias = new ArrayList<>();
        mContext = mActivity;
    }

    public PictureListRecyclerAdapter(PictureListActivity activity, List<LocalMedia> localMedias) {
        this.mActivity = activity;
        this.localMedias = localMedias;
        mContext = mActivity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_picture_image_grid, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        if (position == 0 && mActivity.config.isShowCamera) {
//
//            holder.imageview.setImageResource(R.drawable.ic_camera_alt_white_24dp);
//            int px = ScreenUtils.dip2px(mContext, 8);
//            holder.imageview.setPadding(px,px,px,px);
//            holder.checkbox.setVisibility(View.GONE);
//            holder.itemView.setTag(position);
//            holder.itemView.setOnClickListener(itemClickListener);
//            return;
//        }

        LocalMedia localMedia = localMedias.get(position);

        //显示照相机
        if (localMedia == null) {
            holder.imageview.setImageResource(R.drawable.ic_camera_alt_white_24dp);
            int px = ScreenUtils.dip2px(mContext, 8);
            holder.imageview.setPadding(px, px, px, px);
            holder.checkbox.setVisibility(View.GONE);
            holder.itemView.setTag(position);
            holder.itemView.setOnClickListener(itemClickListener);
            return;
        }


//        holder.textView.setText(mediaBeanList.get(position));

//        int r = random.nextInt(400) + 100;
//        layoutParams = holder.itemView.getLayoutParams();
//        layoutParams.height = r;
//        holder.itemView.setLayoutParams(layoutParams);
//        Logger.w("position:"+position+",原图："+mediaBeanList.get(position).getOriginalPath()+",大图："+mediaBeanList.get(position).getThumbnailBigPath()+",小图："+mediaBeanList.get(position).getThumbnailSmallPath());

        //添加图片集合的时候需要判断是否存在
        Glide.with(mContext)

                .load(localMedia.getPath())
                .placeholder(R.drawable.image_placeholder)
//                .thumbnail(0.2f)//加载过程显示缩略图
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .override(240, 240)
                .into(holder.imageview);

        holder.checkbox.setVisibility(View.VISIBLE);
        holder.checkbox.setChecked(false);
        for (LocalMedia checkedMediaBean : mActivity.checkedMediaBeans) {
            String path = checkedMediaBean.getPath();
            String localMediaPath = localMedia.getPath();
            if (localMediaPath != null && localMediaPath.equals(path)) {
                holder.checkbox.setChecked(true);
                break;
            }
        }
//        if (mActivity.checkedMediaBeans.contains(localMedia)) {
//            holder.checkbox.setChecked(true);
//        } else {
//            holder.checkbox.setChecked(false);
//        }
        holder.checkbox.setTag(localMedia);
        holder.checkbox.setOnClickListener(checkClickListener);
        holder.itemView.setTag(position);
//        holder.itemView.setTag(mediaBean);
        holder.itemView.setOnClickListener(itemClickListener);

    }


    @Override
    public int getItemCount() {
        return localMedias.size();
    }


    private RefreshListener refreshListener;

    public void setRefreshListener(RefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    public void bindImagesData(List<LocalMedia> images) {
        localMedias = images;
        notifyDataSetChanged();
    }

    private ItemClickListener itemClickListener = new ItemClickListener();

    private class ItemClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
//            PictureConfig config = mActivity.config;
//            if (position == 0 && config.isShowCamera) {
//                ArrayList<LocalMedia> checkedMediaBeans = checkedMediaBeans;
//                if (checkedMediaBeans != null && checkedMediaBeans.size() < config.maxNumber) {
//                    CameraUtils.startCamera(mActivity);
//                } else {
//                    ToastUtils.show(mContext, "已经选择" + config.maxNumber + "张图片");
//                }
//                return;
//            }
            //判断是否是照相机
            if (localMedias.get(position) == null) {
                if (mActivity.checkedMediaBeans.size() < mActivity.config.maxNumber) {
                    CameraUtils.startCamera(mActivity);
                } else {
                    ToastUtils.show(mContext, "已经选择" + mActivity.config.maxNumber + "张图片");
                }
            } else {
                //放大
                Intent intent = new Intent(mContext, PictureViewPagerActivity.class);
                intent.putExtra(PictureSelectUtil.LOCAL_MEDIA_POSITION, position);
                mContext.startActivity(intent);
            }


        }
    }

    private CheckClickListener checkClickListener = new CheckClickListener();

    private class CheckClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            LocalMedia localMedia = (LocalMedia) v.getTag();
            localMedia.setFolderName(mActivity.currentLocalMediaFolder.getName());
            CheckBox cb = (CheckBox) v;
            if (cb.isChecked()) {
                if (mActivity.checkedMediaBeans.size() < mActivity.config.maxNumber) {
                    mActivity.checkedMediaBeans.add(localMedia);
                } else {
                    cb.setChecked(false);
                    ToastUtils.show(mContext, "已选择" + mActivity.config.maxNumber + "张图片");
                }
            } else {
                if (mActivity.checkedMediaBeans.contains(localMedia)) {
                    mActivity.checkedMediaBeans.remove(localMedia);
                }
            }
            if (refreshListener != null)
                refreshListener.refrsh(localMedia);
        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageview;
        CheckBox checkbox;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageview = (ImageView) itemView.findViewById(R.id.imageview);
            checkbox = (CheckBox) itemView.findViewById(R.id.checkbox);
        }
    }


    public interface RefreshListener {
        void refrsh(LocalMedia localMedia);
    }
}
