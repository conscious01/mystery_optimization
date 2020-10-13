package com.zgzx.metaphysics.rongmessage;

import android.annotation.SuppressLint;
import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;

@SuppressLint("ParcelCreator")
@MessageTag(value = "KM:LuckPushMsg",flag = MessageTag.NONE  )
public class LuckMessage extends MessageContent {
    private int id;
    private String title, content, extra, pushdata;
    private long time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getPushdata() {
        return pushdata;
    }

    public void setPushdata(String pushdata) {
        this.pushdata = pushdata;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public LuckMessage(byte[] data) {
        String jsonStr = null;
        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e1) {

        }

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            content = jsonObj.optString("content");

        } catch (JSONException e) {

        }

    }


    /**
     * 读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理。
     */
    public static final Creator<LuckMessage> CREATOR = new Creator<LuckMessage>() {

        @Override
        public LuckMessage createFromParcel(Parcel source) {
            return new LuckMessage(source);
        }

        @Override
        public LuckMessage[] newArray(int size) {
            return new LuckMessage[size];
        }
    };


    public LuckMessage(Parcel source) {

        id = ParcelUtils.readIntFromParcel(source);


        title = ParcelUtils.readFromParcel(source);
        content = ParcelUtils.readFromParcel(source);
        extra = ParcelUtils.readFromParcel(source);
        pushdata = ParcelUtils.readFromParcel(source);
        time = ParcelUtils.readLongFromParcel(source);


    }



    @Override
    public byte[] encode() {

        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest, this.id);

        ParcelUtils.writeToParcel(dest, this.title);
        ParcelUtils.writeToParcel(dest, this.content);
        ParcelUtils.writeToParcel(dest, this.extra);
        ParcelUtils.writeToParcel(dest, this.pushdata);
        ParcelUtils.writeToParcel(dest, this.time);


    }
}
