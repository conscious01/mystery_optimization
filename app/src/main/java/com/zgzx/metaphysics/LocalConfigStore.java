package com.zgzx.metaphysics;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.qbw.spm.P;
import com.zgzx.metaphysics.model.LocalDataManager;
import com.zgzx.metaphysics.model.entity.UserDetailEntity;
import com.zgzx.metaphysics.network.WebApiConstants;

import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;


public class LocalConfigStore {

    private static final
    String USER_TOKEN = "USER_TOKEN",     // 当前用户token
            MOBILE = "MOBILE",// 手机号码
            USER_ID = "USER_ID", // 用户id
            NAME = "NAME", // 姓名
            RQ_CODE = "RQ_CODE", // 二维码
            ACCEPT_LANGUAGE = "Accept-Language",// 语言
            IS_COMPLETED_INFO = "completed_info", // 是否完善用户信息
            REGISTER_PAGE = "register-page",
            SEX = "sex",
            BIRTH_TIME = "birth_time",
            ROLE = "ROLE",
            RONG_TOKEN = "RONG_TOKEN",
            PHONE = "phone",
            PHONE_CODE = "phone_code",
            PAWSSORD = "passWord",
            H5_BASE = "H5_BASE",
            AK = "AK",
            TIMESTAMP = "TIMESTAMP",
            KEY = "KEY",
            JN_TYPE = "JN_TYPE",
            JN_TYPE_1 = "JN_TYPE_1",
            JN_TYPE_2 = "JN_TYPE_2",
            JN_TYPE_3 = "JN_TYPE_3",
            JN_TYPE_4 = "JN_TYPE_4",
            JN_TYPE_5 = "JN_TYPE_5",
            JN_TYPE_6 = "JN_TYPE_6",
            JN_TYPE_7 = "JN_TYPE_7",
            JN_TYPE_8 = "JN_TYPE_8",
            AD_ID = "ad_id",
            AD_CODE_ID = "AD_CODE_ID",
            AVATAR = "AVATAR"; // 头像

    private static LocalConfigStore sLocalConfigStore;

    public static LocalConfigStore getInstance() {
        if (sLocalConfigStore == null) {
            synchronized (LocalConfigStore.class) {
                if (sLocalConfigStore == null) {
                    sLocalConfigStore = new LocalConfigStore();
                }
            }
        }

        return sLocalConfigStore;
    }


    private String mUserToken;
    private LocalDataManager mLocalDataManager;
    private SharedPreferences mConfigsPreferences
            //mCacheDataPreferences
            ;

    private ObservableEmitter<String> mAvatarEmitter;

    private LocalConfigStore() {
        mLocalDataManager = new LocalDataManager();
        mConfigsPreferences = MetaphysicsApplication.sInstance.getSharedPreferences("configs",
                Context.MODE_PRIVATE);
        //mCacheDataPreferences = MetaphysicsApplication.sInstance.getSharedPreferences
        // ("cache_data", Context.MODE_PRIVATE);
    }


    public boolean ifMaster() {
        return mConfigsPreferences.getInt(ROLE, 0) == 2;
    }


    public String getUserToken() {
        if (TextUtils.isEmpty(mUserToken)) {
            mUserToken = mConfigsPreferences.getString(USER_TOKEN, "");
        }

        return mUserToken;
    }

    public long getTimestamp() {
        return mConfigsPreferences.getLong(TIMESTAMP, 0);
    }

    public String getAk() {
        return mConfigsPreferences.getString(AK, "");
    }

    public String getKey() {
        return mConfigsPreferences.getString(KEY, "");
    }

    public String getAdId() {
        return mConfigsPreferences.getString(AD_ID, "");
    }

    public String getAdCodeId() {
        return mConfigsPreferences.getString(AD_CODE_ID, "");
    }

    public String getH5_Base() {
        return mConfigsPreferences.getString(H5_BASE, "");
    }

    public void setH5Base(String h5_BASE) {
        mConfigsPreferences.edit().putString(H5_BASE, h5_BASE).apply();
    }

    public void setConfirgUrl(long timestamp, String ak, String key) {
        mConfigsPreferences.edit().putString(AK, ak).putLong(TIMESTAMP, timestamp).putString(KEY,
                key).apply();
    }

    public void setAdConfirg(String ad_id, String ids) {
        mConfigsPreferences.edit().putString(AD_ID, ad_id).putString(AD_CODE_ID, ids).apply();
    }

    public void updateAcceptLanguage(String language) {
        mConfigsPreferences.edit().putString(ACCEPT_LANGUAGE, language).apply();
    }

    public void updateRgisterPage(String url) {
        mConfigsPreferences.edit().putString(REGISTER_PAGE, url).apply();
    }

    public String getAcceptLanguage() {
        String language = mConfigsPreferences.getString(ACCEPT_LANGUAGE, null);

        if (TextUtils.isEmpty(language)) {
            Locale locale =
                    MetaphysicsApplication.sInstance.getResources().getConfiguration().locale;
            if ("CN".equals(locale.getCountry())) {
                language = WebApiConstants.LANGUAGE_ZH_CN;

            } else if ("TW".equals(locale.getCountry()) || "HK".equals(locale.getCountry())) {
                language = WebApiConstants.LANGUAGE_ZH_TW;
            }
        }

        return language;
    }

    public String getRongToken() {
        return mConfigsPreferences.getString(RONG_TOKEN, "");
    }

    public String getRqCode() {
        return mConfigsPreferences.getString(RQ_CODE, null);
    }

    public String getRegisterPage() {
        return mConfigsPreferences.getString(REGISTER_PAGE, null);
    }

    public String getMobile() {
        return mConfigsPreferences.getString(MOBILE, null);
    }

    public String getUserName() {
        return mConfigsPreferences.getString(NAME, null);
    }

    public String getRole() {
        return mConfigsPreferences.getString(ROLE, null);
    }

    public int getSex() {
        return mConfigsPreferences.getInt(SEX, -1);
    }

    public String getBirthTime() {
        return mConfigsPreferences.getString(BIRTH_TIME, null);
    }

    public Observable<String> getAvatar() {
        return Observable.create(emitter -> {
            this.mAvatarEmitter = emitter;
            emitter.onNext(mConfigsPreferences.getString(AVATAR, null));
        });
    }

    public String getUserId() {
        return mConfigsPreferences.getString(USER_ID, null);
    }

    public boolean isCompletedInfo() {
        return mConfigsPreferences.getBoolean(IS_COMPLETED_INFO, false);
    }

    public void completedInfo() {
        mConfigsPreferences.edit().putBoolean(IS_COMPLETED_INFO, true).apply();
    }

    public int getJnType() {
        return mConfigsPreferences.getInt(JN_TYPE, 0);
    }

    public void setJnType(int i) {
        int jntype = getJnType() + 1;
        mConfigsPreferences.edit().putInt(JN_TYPE, jntype).apply();

    }

    public boolean getJnType1() {
        return mConfigsPreferences.getBoolean(JN_TYPE_1, false);
    }

    public void setJnType_1(boolean isAd) {
        mConfigsPreferences.edit().putBoolean(JN_TYPE_1, isAd).apply();
    }

    public boolean getJnType2() {
        return mConfigsPreferences.getBoolean(JN_TYPE_2, false);
    }

    public void setJnType_2(boolean isAd) {
        mConfigsPreferences.edit().putBoolean(JN_TYPE_2, isAd).apply();
    }

    public boolean getJnType3() {
        return mConfigsPreferences.getBoolean(JN_TYPE_3, false);
    }

    public void setJnType_3(boolean isAd) {
        mConfigsPreferences.edit().putBoolean(JN_TYPE_3, isAd).apply();
    }

    public boolean getJnType4() {
        return mConfigsPreferences.getBoolean(JN_TYPE_4, false);
    }

    public void setJnType_4(boolean isAd) {
        mConfigsPreferences.edit().putBoolean(JN_TYPE_4, isAd).apply();
    }

    public boolean getJnType5() {
        return mConfigsPreferences.getBoolean(JN_TYPE_5, false);
    }

    public void setJnType_5(boolean isAd) {
        mConfigsPreferences.edit().putBoolean(JN_TYPE_5, isAd).apply();
    }

    public boolean getJnType6() {
        return mConfigsPreferences.getBoolean(JN_TYPE_6, false);
    }

    public void setJnType_6(boolean isAd) {
        mConfigsPreferences.edit().putBoolean(JN_TYPE_6, isAd).apply();
    }


    public boolean getJnType8() {
        return mConfigsPreferences.getBoolean(JN_TYPE_8, false);
    }

    public void setJnType_8(boolean isAd) {
        mConfigsPreferences.edit().putBoolean(JN_TYPE_8, isAd).apply();
    }

    public boolean getJnType7() {
        return mConfigsPreferences.getBoolean(JN_TYPE_7, false);
    }

    public void setJnType_7(boolean isAd) {
        mConfigsPreferences.edit().putBoolean(JN_TYPE_7, isAd).apply();
    }


    public boolean isLogin() {
        String mobile = mConfigsPreferences.getString(MOBILE, null);
        // 必须要有手机号码和token才视为登录
        return !TextUtils.isEmpty(getUserToken()) && !TextUtils.isEmpty(mobile);
    }

    public void logout() {
        mUserToken = null;

        mConfigsPreferences.edit()
                .remove(USER_TOKEN)
                .remove(MOBILE)
                .remove(NAME)
                .remove(AVATAR)
                .remove(USER_ID)
                .remove(RQ_CODE)
                .remove(IS_COMPLETED_INFO)
                .remove(RONG_TOKEN)
                .apply();
        mConfigsPreferences.edit()
                .remove(PHONE)
                .remove(PHONE_CODE)
                .remove(PAWSSORD).apply();
        // 退出登录，清除所有缓存数据
        mLocalDataManager.clear();
    }

    public void saveUserToken(String token) {
        mUserToken = token;
        mConfigsPreferences.edit().putString(USER_TOKEN, token).apply();
    }

    public String getPhone() {
        return mConfigsPreferences.getString(PHONE, null);
    }

    public String getPhoneCode() {
        return mConfigsPreferences.getString(PHONE_CODE, null);
    }


    public String getPassword() {
        return mConfigsPreferences.getString(PAWSSORD, null);
    }

    public void saveUserLoginInfo(String phone, String code, String passwoed) {
        mConfigsPreferences.edit()
                .putString(PHONE, phone)
                .putString(PHONE_CODE, code)
                .putString(PAWSSORD, passwoed).apply();
    }

    public void saveUserAvatar(String avatarPath) {
        mConfigsPreferences.edit().putString(AVATAR, avatarPath).apply();

        // 刷新
        if (mAvatarEmitter != null) {
            mAvatarEmitter.onNext(avatarPath);
        }
    }

    public void saveUserInfo(UserDetailEntity userDetail) {
        setUser(userDetail);

        if (userDetail != null && userDetail.getToken() != null) {
            mUserToken = userDetail.getToken();
            mConfigsPreferences.edit()
                    .putString(USER_TOKEN, mUserToken).apply();
        }
        //后台返回融云token为空的时候不要保存，逻辑应该放在后台判断
        if (!userDetail.getRc_token().isEmpty()) {
            mConfigsPreferences.edit().putString(RONG_TOKEN, userDetail.getRc_token());
        }

        mConfigsPreferences.edit()
                .putString(MOBILE, userDetail.getPhone())
                .putString(NAME, userDetail.getNickname())
                .putString(AVATAR, userDetail.getAvatar())
                .putString(BIRTH_TIME, userDetail.getBirth_time())
                .putInt(SEX, userDetail.getSex())
                .putInt(ROLE, userDetail.getRole())
                .putString(USER_ID, String.valueOf(userDetail.getUid()))
                .putString(RQ_CODE, userDetail.getInvite_qrcode())
                .putBoolean(IS_COMPLETED_INFO, userDetail.isHas_completed_info())
                .apply();
    }


    private UserDetailEntity mUser;
    private final String KEY_LOGINED_USER_INFO = "usermanager_key_logined_user_info";

    public UserDetailEntity getUser() {
        if (mUser == null) {
            mUser = P.getObject(KEY_LOGINED_USER_INFO, UserDetailEntity.class);
        }
        return mUser;
    }

    public void setUser(UserDetailEntity user) {
        mUser = user;
        P.putObject(KEY_LOGINED_USER_INFO, user);
    }
}
