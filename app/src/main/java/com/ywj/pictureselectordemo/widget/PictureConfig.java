package com.ywj.pictureselectordemo.widget;

import com.isseiaoki.simplecropview.CropImageView;

import java.io.Serializable;

public class PictureConfig implements Serializable {
    /**
     * 每行显示条目数
     */
    public int spanCount = 4;
    /**
     * 最多可选图片数量
     */
    public int maxNumber = 1;



        /*压缩*/
    /**
     * 是否裁剪
     */
    public boolean isCrop = true;
    /**
     * 是否显示相机
     */
    public boolean isShowCamera = true;
    /**
     * 压缩质量
     */
    public int compressQuality = 100;
    /**
     * 裁剪宽/高比例
     */
    public int customRatio = 1;
    /**
     * 裁剪模式，比例，什么形状
     */
    public CropImageView.CropMode cropMode = CropImageView.CropMode.CUSTOM;
    /**最大输出尺寸*/
//        setOutputMaxSize
    /**
     * 显示帧缩放(0.01 ~ 1.0)
     */
    public float initialFrameScale = 0.75f;
    /**
     * 背景色
     */
    public int backgroundColor = 0xAA1C1C1C;

}