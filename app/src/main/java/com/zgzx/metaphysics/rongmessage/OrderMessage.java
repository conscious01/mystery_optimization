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
@MessageTag(value = "KM:OrderPushMsg",flag = MessageTag.NONE  )
public class OrderMessage extends MessageContent {
    private int id, orderId, openType;
    private String title, content, extra, pushdata;
    private long time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOpenType() {
        return openType;
    }

    public void setOpenType(int openType) {
        this.openType = openType;
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

    public OrderMessage(byte[] data) {
        String jsonStr = null;
        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
        }

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            id = jsonObj.optInt("id");
            orderId = jsonObj.optInt("orderId");
            openType = jsonObj.optInt("openType");

            title = jsonObj.optString("title");
            content = jsonObj.optString("content");
            extra = jsonObj.optString("extra");
            pushdata = jsonObj.optString("pushdata");

            time = jsonObj.optLong("time");

        } catch (JSONException e) {

        }

    }


    /**
     * 读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理。
     */
    public static final Creator<OrderMessage> CREATOR = new Creator<OrderMessage>() {

        @Override
        public OrderMessage createFromParcel(Parcel source) {
            return new OrderMessage(source);
        }

        @Override
        public OrderMessage[] newArray(int size) {
            return new OrderMessage[size];
        }
    };


    public OrderMessage(Parcel source) {

        id = ParcelUtils.readIntFromParcel(source);
        orderId = ParcelUtils.readIntFromParcel(source);
        openType = ParcelUtils.readIntFromParcel(source);

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
        ParcelUtils.writeToParcel(dest, this.orderId);
        ParcelUtils.writeToParcel(dest, this.openType);
        ParcelUtils.writeToParcel(dest, this.title);
        ParcelUtils.writeToParcel(dest, this.content);
        ParcelUtils.writeToParcel(dest, this.extra);
        ParcelUtils.writeToParcel(dest, this.pushdata);
        ParcelUtils.writeToParcel(dest, this.time);


    }
}
