package com.ywj.pictureselectordemo.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ywj.pictureselectordemo.R;
import com.ywj.pictureselectordemo.activity.PictureFolderListActivity;
import com.ywj.pictureselectordemo.activity.PictureListActivity;
import com.ywj.pictureselectordemo.bean.LocalMedia;
import com.ywj.pictureselectordemo.bean.LocalMediaFolder;

import java.util.List;

/**
 * 图片列表适配器
 * 作者：ywj on 2016/8/25 0025 11:11
 * 邮箱：ywj_and@163.com
 */
public class PictureFolderRecyclerAdapter extends RecyclerView.Adapter<PictureFolderRecyclerAdapter.MyViewHolder> {
    private List<LocalMediaFolder> localMediaFolders;
    private Context mContext;
    private PictureFolderListActivity mActivity;


    public PictureFolderRecyclerAdapter(PictureFolderListActivity activity, List<LocalMediaFolder> localMediaFolders) {
        this.mActivity = activity;
        this.localMediaFolders = localMediaFolders;
        mContext = mActivity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_picture_folder, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (localMediaFolders == null) {
            return;
        }
        LocalMediaFolder localMediaFolder = localMediaFolders.get(position);
        if (localMediaFolder == null) {
            return;
        }


        Glide.with(mContext)
                .load(localMediaFolder.getFirstImagePath())
                .placeholder(R.drawable.ic_placeholder)
//                .thumbnail(0.2f)//加载过程显示缩略图
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .override(240, 240)
                .into(holder.imageView);
        holder.tv_folder_name.setText(localMediaFolder.getName());
        holder.tv_image_num.setText("(" + localMediaFolder.getImageNum() + ")");
        holder.tv_selected_image_num.setText(localMediaFolder.getCheckedNum() + "");
        holder.tv_selected_image_num.setVisibility(localMediaFolder.getCheckedNum() > 0 ? View.VISIBLE : View.INVISIBLE);

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(onItemClickListener);
    }

    OnItemClickListener onItemClickListener = new OnItemClickListener();

    class OnItemClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            mActivity.currentLocalMediaFolderPosition = position;
            Intent intent = new Intent(mContext, PictureListActivity.class);
//            intent.putExtra("position", position);
            mContext.startActivity(intent);
        }
    }

    @Override
    public int getItemCount() {
        return localMediaFolders.size();
    }


    RefreshListener refreshListener;

    public void setRefreshListener(RefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    public void bindImagesData(List<LocalMedia> images) {

        notifyDataSetChanged();
    }

    private ItemClickListener itemClickListener = new ItemClickListener();

    class ItemClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tv_folder_name, tv_image_num, tv_selected_image_num;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            tv_folder_name = (TextView) itemView.findViewById(R.id.tv_folder_name);
            tv_image_num = (TextView) itemView.findViewById(R.id.tv_image_num);
            tv_selected_image_num = (TextView) itemView.findViewById(R.id.tv_selected_image_num);
        }
    }


    public interface RefreshListener {
        void refrsh();
    }
}
