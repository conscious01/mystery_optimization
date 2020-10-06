package com.zgzx.metaphysics.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class UserDetailEntity implements Parcelable {

    /**
     * token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0aW1lc3RhbXAiOjE2MDEwMjEzNTEwODA3NDgzNzMsInVpZCI6MTAwMjE3NSwidXNlcm5hbWUiOiIxMzc2MDI2NzE0NyJ9.saCQJDIjERLVhgl-s6wPGGP8BDAVm7zxlGc9dMitXZk
     * uid : 1002175
     * username : 13760267147
     * sex : 1
     * role : 2
     * realname : 哈哈
     * nickname : 哈哈
     * avatar : https://mystery-oss.oss-cn-hongkong.aliyuncs.com/uploads/2020/09/12/1599880485348639100.png
     * phone : 13760267147
     * vip_info : {"id":0,"icon":"","name":"","end_time":0}
     * phone_code : +86
     * invite_code : DGQ9PJ
     * invite_qrcode : https://mystery-oss.oss-cn-hongkong.aliyuncs.com/uploads/2020/09/02/1599031178058687665.png
     * invite_link : http://h5.test.kongming365.com?invite_code=DGQ9PJ
     * qr_code :
     * birth_area : 中國,河北省,唐山市,玉田縣
     * live_area :
     * birth_day : 1600819200
     * birth_hour : 4
     * timezone : 8
     * calendar_type : 2
     * rc_token : JC6zpjYuRCV5gB5jAjAR/GLfWgpVjGXMAdOnl9scQE8=@84mk.cn.rongnav.com;84mk.cn.rongcfg.com
     * apply_status : 0
     * invite_count : 0
     * has_paypass : false
     * has_completed_info : true
     * birth_time : 2010年10月01號 xx時 xx年
     * has_lifebook : true
     */

    private String token;
    private int uid;
    private String username;
    private int sex;
    private int role;
    private String realname;
    private String nickname;
    private String avatar;
    private String phone;
    private VipInfoBean vip_info;
    private String phone_code;
    private String invite_code;
    private String invite_qrcode;
    private String invite_link;
    private String qr_code;
    private String birth_area;
    private String live_area;
    private int birth_day;
    private int birth_hour;
    private int timezone;
    private int calendar_type;
    private String rc_token;
    private int apply_status;
    private int invite_count;
    private boolean has_paypass;
    private boolean has_completed_info;
    private String birth_time;
    private boolean has_lifebook;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public VipInfoBean getVip_info() {
        return vip_info;
    }

    public void setVip_info(VipInfoBean vip_info) {
        this.vip_info = vip_info;
    }

    public String getPhone_code() {
        return phone_code;
    }

    public void setPhone_code(String phone_code) {
        this.phone_code = phone_code;
    }

    public String getInvite_code() {
        return invite_code;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }

    public String getInvite_qrcode() {
        return invite_qrcode;
    }

    public void setInvite_qrcode(String invite_qrcode) {
        this.invite_qrcode = invite_qrcode;
    }

    public String getInvite_link() {
        return invite_link;
    }

    public void setInvite_link(String invite_link) {
        this.invite_link = invite_link;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    public String getBirth_area() {
        return birth_area;
    }

    public void setBirth_area(String birth_area) {
        this.birth_area = birth_area;
    }

    public String getLive_area() {
        return live_area;
    }

    public void setLive_area(String live_area) {
        this.live_area = live_area;
    }

    public int getBirth_day() {
        return birth_day;
    }

    public void setBirth_day(int birth_day) {
        this.birth_day = birth_day;
    }

    public int getBirth_hour() {
        return birth_hour;
    }

    public void setBirth_hour(int birth_hour) {
        this.birth_hour = birth_hour;
    }

    public int getTimezone() {
        return timezone;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    public int getCalendar_type() {
        return calendar_type;
    }

    public void setCalendar_type(int calendar_type) {
        this.calendar_type = calendar_type;
    }

    public String getRc_token() {
        return rc_token;
    }

    public void setRc_token(String rc_token) {
        this.rc_token = rc_token;
    }

    public int getApply_status() {
        return apply_status;
    }

    public void setApply_status(int apply_status) {
        this.apply_status = apply_status;
    }

    public int getInvite_count() {
        return invite_count;
    }

    public void setInvite_count(int invite_count) {
        this.invite_count = invite_count;
    }

    public boolean isHas_paypass() {
        return has_paypass;
    }

    public void setHas_paypass(boolean has_paypass) {
        this.has_paypass = has_paypass;
    }

    public boolean isHas_completed_info() {
        return has_completed_info;
    }

    public void setHas_completed_info(boolean has_completed_info) {
        this.has_completed_info = has_completed_info;
    }

    public String getBirth_time() {
        return birth_time;
    }

    public void setBirth_time(String birth_time) {
        this.birth_time = birth_time;
    }

    public boolean isHas_lifebook() {
        return has_lifebook;
    }

    public void setHas_lifebook(boolean has_lifebook) {
        this.has_lifebook = has_lifebook;
    }

    public static class VipInfoBean implements Parcelable {
        /**
         * id : 0
         * icon :
         * name :
         * end_time : 0
         */

        private int id;
        private String icon;
        private String name;
        private long end_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getEnd_time() {
            return end_time;
        }

        public void setEnd_time(long end_time) {
            this.end_time = end_time;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.icon);
            dest.writeString(this.name);
            dest.writeLong(this.end_time);
        }

        public VipInfoBean() {
        }

        protected VipInfoBean(Parcel in) {
            this.id = in.readInt();
            this.icon = in.readString();
            this.name = in.readString();
            this.end_time = in.readLong();
        }

        public static final Creator<VipInfoBean> CREATOR = new Creator<VipInfoBean>() {
            @Override
            public VipInfoBean createFromParcel(Parcel source) {
                return new VipInfoBean(source);
            }

            @Override
            public VipInfoBean[] newArray(int size) {
                return new VipInfoBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.token);
        dest.writeInt(this.uid);
        dest.writeString(this.username);
        dest.writeInt(this.sex);
        dest.writeInt(this.role);
        dest.writeString(this.realname);
        dest.writeString(this.nickname);
        dest.writeString(this.avatar);
        dest.writeString(this.phone);
        dest.writeParcelable(this.vip_info, flags);
        dest.writeString(this.phone_code);
        dest.writeString(this.invite_code);
        dest.writeString(this.invite_qrcode);
        dest.writeString(this.invite_link);
        dest.writeString(this.qr_code);
        dest.writeString(this.birth_area);
        dest.writeString(this.live_area);
        dest.writeInt(this.birth_day);
        dest.writeInt(this.birth_hour);
        dest.writeInt(this.timezone);
        dest.writeInt(this.calendar_type);
        dest.writeString(this.rc_token);
        dest.writeInt(this.apply_status);
        dest.writeInt(this.invite_count);
        dest.writeByte(this.has_paypass ? (byte) 1 : (byte) 0);
        dest.writeByte(this.has_completed_info ? (byte) 1 : (byte) 0);
        dest.writeString(this.birth_time);
        dest.writeByte(this.has_lifebook ? (byte) 1 : (byte) 0);
    }

    public UserDetailEntity() {
    }

    protected UserDetailEntity(Parcel in) {
        this.token = in.readString();
        this.uid = in.readInt();
        this.username = in.readString();
        this.sex = in.readInt();
        this.role = in.readInt();
        this.realname = in.readString();
        this.nickname = in.readString();
        this.avatar = in.readString();
        this.phone = in.readString();
        this.vip_info = in.readParcelable(VipInfoBean.class.getClassLoader());
        this.phone_code = in.readString();
        this.invite_code = in.readString();
        this.invite_qrcode = in.readString();
        this.invite_link = in.readString();
        this.qr_code = in.readString();
        this.birth_area = in.readString();
        this.live_area = in.readString();
        this.birth_day = in.readInt();
        this.birth_hour = in.readInt();
        this.timezone = in.readInt();
        this.calendar_type = in.readInt();
        this.rc_token = in.readString();
        this.apply_status = in.readInt();
        this.invite_count = in.readInt();
        this.has_paypass = in.readByte() != 0;
        this.has_completed_info = in.readByte() != 0;
        this.birth_time = in.readString();
        this.has_lifebook = in.readByte() != 0;
    }

    public static final Creator<UserDetailEntity> CREATOR = new Creator<UserDetailEntity>() {
        @Override
        public UserDetailEntity createFromParcel(Parcel source) {
            return new UserDetailEntity(source);
        }

        @Override
        public UserDetailEntity[] newArray(int size) {
            return new UserDetailEntity[size];
        }
    };
}
