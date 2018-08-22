package com.ywj.pictureselectordemo.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 相册列表bean,媒体对象文件夹
 * author：luck
 * project：PictureSelector
 * package：com.luck.picture.entity
 * email：893855882@qq.com
 * data：16/12/31
 */
public class LocalMediaFolder   {
    /**文件夹名称*/
    private String name;
    /**文件夹路径*/
    private String path;
    /**第一张图片*/
    private String firstImagePath;
    /**图片数量*/
    private int imageNum;
    /**文件夹是否被选中*/
    private boolean isChecked;
    /**选中的图片数量*/
    private int checkedNum;
    private int type;
    private List<LocalMedia> images = new ArrayList<LocalMedia>();

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCheckedNum() {
        return checkedNum;
    }

    public void setCheckedNum(int checkedNum) {
        this.checkedNum = checkedNum;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFirstImagePath() {
        return firstImagePath;
    }

    public void setFirstImagePath(String firstImagePath) {
        this.firstImagePath = firstImagePath;
    }

    public int getImageNum() {
        return imageNum;
    }

    public void setImageNum(int imageNum) {
        this.imageNum = imageNum;
    }

    public List<LocalMedia> getImages() {
        return images;
    }

    public void setImages(List<LocalMedia> images) {
        this.images = images;
    }

}
