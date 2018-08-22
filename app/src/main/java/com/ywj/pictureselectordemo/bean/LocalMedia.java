package com.ywj.pictureselectordemo.bean;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * 媒体对象
 * author：luck
 * project：PictureSelector
 * package：com.luck.picture.entity
 * email：893855882@qq.com
 * data：16/12/31
 */
public class LocalMedia implements Parcelable {
    /**路径*/
    private String path;
    /**文件夹名称*/
    private String folderName;
    /**修改时间*/
    private long lastUpdateAt;

    private boolean isChecked;
    private long duration;
    private String compressPath;
    private String cutPath;
    private boolean isCut;
    public int position;
    private int num;
    private int type;
    private boolean compressed;

    public LocalMedia(String path, long lastUpdateAt, long duration, int type) {
        this.path = path;
        this.duration = duration;
        this.lastUpdateAt = lastUpdateAt;
        this.type = type;
    }

    public LocalMedia(String path, long duration, long lastUpdateAt,
                      boolean isChecked, int position, int num, int type) {
        this.path = path;
        this.duration = duration;
        this.lastUpdateAt = lastUpdateAt;
        this.isChecked = isChecked;
        this.position = position;
        this.num = num;
        this.type = type;
    }

    public LocalMedia() {
    }


    protected LocalMedia(Parcel in) {
        path = in.readString();
        folderName = in.readString();
        lastUpdateAt = in.readLong();
        isChecked = in.readByte() != 0;
        duration = in.readLong();
        compressPath = in.readString();
        cutPath = in.readString();
        isCut = in.readByte() != 0;
        position = in.readInt();
        num = in.readInt();
        type = in.readInt();
        compressed = in.readByte() != 0;
    }

    public static final Creator<LocalMedia> CREATOR = new Creator<LocalMedia>() {
        @Override
        public LocalMedia createFromParcel(Parcel in) {
            return new LocalMedia(in);
        }

        @Override
        public LocalMedia[] newArray(int size) {
            return new LocalMedia[size];
        }
    };

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getCutPath() {
        return cutPath;
    }

    public void setCutPath(String cutPath) {
        this.cutPath = cutPath;
    }

    public boolean isCut() {
        return isCut;
    }

    public void setCut(boolean cut) {
        isCut = cut;
    }

    public boolean isCompressed() {
        return compressed;
    }

    public void setCompressed(boolean compressed) {
        this.compressed = compressed;
    }

    public String getCompressPath() {
        return compressPath;
    }

    public void setCompressPath(String compressPath) {
        this.compressPath = compressPath;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getLastUpdateAt() {
        return lastUpdateAt;
    }

    public void setLastUpdateAt(long lastUpdateAt) {
        this.lastUpdateAt = lastUpdateAt;
    }

    public boolean getIsChecked() {
        return this.isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public LocalMedia(String path, String folderName, long lastUpdateAt, boolean isChecked, long duration, String compressPath, String cutPath, boolean isCut, int position, int num, int type, boolean compressed) {
        this.path = path;
        this.folderName = folderName;
        this.lastUpdateAt = lastUpdateAt;
        this.isChecked = isChecked;
        this.duration = duration;
        this.compressPath = compressPath;
        this.cutPath = cutPath;
        this.isCut = isCut;
        this.position = position;
        this.num = num;
        this.type = type;
        this.compressed = compressed;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
        dest.writeString(folderName);
        dest.writeLong(lastUpdateAt);
        dest.writeByte((byte) (isChecked ? 1 : 0));
        dest.writeLong(duration);
        dest.writeString(compressPath);
        dest.writeString(cutPath);
        dest.writeByte((byte) (isCut ? 1 : 0));
        dest.writeInt(position);
        dest.writeInt(num);
        dest.writeInt(type);
        dest.writeByte((byte) (compressed ? 1 : 0));
    }

    @Override
    public String toString() {
        return "LocalMedia{" +
                "path='" + path + '\'' +
                ", folderName='" + folderName + '\'' +
                ", lastUpdateAt=" + lastUpdateAt +
                ", isChecked=" + isChecked +
                ", duration=" + duration +
                ", compressPath='" + compressPath + '\'' +
                ", cutPath='" + cutPath + '\'' +
                ", isCut=" + isCut +
                ", position=" + position +
                ", num=" + num +
                ", type=" + type +
                ", compressed=" + compressed +
                '}';
    }
}
