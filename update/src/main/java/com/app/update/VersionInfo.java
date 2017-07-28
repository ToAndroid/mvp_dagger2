package com.app.update;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by john on 2017/7/28.
 */

public class VersionInfo implements Parcelable {
    private String versionName;
    private String versionCode;
    private String des;
    private String time;
    private String size;
    private String url;
    private String status;

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.versionName);
        dest.writeString(this.versionCode);
        dest.writeString(this.des);
        dest.writeString(this.time);
        dest.writeString(this.size);
        dest.writeString(this.url);
        dest.writeString(this.status);
    }

    public VersionInfo() {
    }

    protected VersionInfo(Parcel in) {
        this.versionName = in.readString();
        this.versionCode = in.readString();
        this.des = in.readString();
        this.time = in.readString();
        this.size = in.readString();
        this.url = in.readString();
        this.status = in.readString();
    }

    public static final Parcelable.Creator<VersionInfo> CREATOR = new Parcelable.Creator<VersionInfo>() {
        public VersionInfo createFromParcel(Parcel source) {
            return new VersionInfo(source);
        }

        public VersionInfo[] newArray(int size) {
            return new VersionInfo[size];
        }
    };

    @Override
    public String toString() {
        return "VersionInfo{" +
                "versionName='" + versionName + '\'' +
                ", versionCode='" + versionCode + '\'' +
                ", des='" + des + '\'' +
                ", time='" + time + '\'' +
                ", size='" + size + '\'' +
                ", url='" + url + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

//    public static VersionInfo parseJson(String json){
//        VersionInfo versionInfo = new VersionInfo();
//
//    }
}
