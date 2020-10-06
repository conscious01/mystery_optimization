package com.zgzx.metaphysics.widgets;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.LocalConfigStore;
import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.model.entity.JSResondEntity;
import com.zgzx.metaphysics.ui.activities.DailyBlessActivity;
import com.zgzx.metaphysics.ui.activities.FindActivity;
import com.zgzx.metaphysics.ui.activities.MainActivity;
import com.zgzx.metaphysics.ui.activities.MyOrderActivity;
import com.zgzx.metaphysics.ui.activities.OrderDetailActivity;
import com.zgzx.metaphysics.ui.activities.PayMethodActivity;
import com.zgzx.metaphysics.ui.activities.WebViewActivity;

public class JavaScriptinterface {
    private static final String TAG = "JavaScriptinterface";

    Activity activity;
    int mJumpType, mOrderID;

    public JavaScriptinterface(Activity c) {
        activity = c;

    }

    public JavaScriptinterface(Activity c, int jumpType, int orderID) {
        activity = c;
        mJumpType = jumpType;
        mOrderID = orderID;

    }

    @JavascriptInterface
    public void openFuncationItem(String jsonString) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                LogUtils.d(TAG,
                        "openFuncationItem jsonString " + jsonString);
                JSResondEntity jsResondEntity
                        = new Gson().fromJson(jsonString, JSResondEntity.class);
                int jumpType = jsResondEntity.getJump_type();
                String navName = jsResondEntity.getNavite_page_name();
                String link_url = jsResondEntity.getLink_url();

                    if (navName.equals("zhougongjiemeng")){//周公解梦
                        String zgjmURL = LocalConfigStore.getInstance().getH5_Base() + "/pages/zhou_gong" +
                                "/oneiromancy?language=" + LocalConfigStore.getInstance().getAcceptLanguage() + "&status_bar_height=" + 24;

                        activity.startActivity(WebViewActivity.newIntent(activity, zgjmURL, Constants.WEB_VIEW_TYPE_ZGJM));

                    }else if ("yijingzhanbu".equals(navName)){//易经占卜
                        String yjzbURL = LocalConfigStore.getInstance().getH5_Base() + "/pages/divination" +
                                "/divination?language=" + LocalConfigStore.getInstance().getAcceptLanguage();
                        activity.startActivity(WebViewActivity.newIntent(activity, yjzbURL,Constants.WEB_VIEW_TYPE_YJZB));

                    }else if ("zengyunhongbao".equals(navName)){//增运红包
                        String hbjfURL = LocalConfigStore.getInstance().getH5_Base() + "/pages" +
                                "/red_packet/red_packet?language=" + LocalConfigStore.getInstance().getAcceptLanguage() + "&degree=0" + "&status_bar_height=" + 24;

                        activity.startActivity(WebViewActivity.newIntent(activity, hbjfURL));
                    }else if ("mingshu".equals(navName)){//命书
                        ((MainActivity) activity).mBottomNavigation.setSelectedItemId(R.id.nav_book);
                    }else if ("kongmingzuoguan".equals(navName)){//孔明做馆
                        ((MainActivity) activity).mBottomNavigation.setSelectedItemId(R.id.nav_find);
                    }else if ("meiriqifu".equals(navName)){//每日祈福
                        activity.startActivity(new Intent(activity, DailyBlessActivity.class));
                    }else if (TextUtils.isEmpty(navName)){
                        activity.startActivity(WebViewActivity.newIntent(activity, link_url));
                    }
                }
//                if (jumpType == 1) { //跳转类型  1 h5 2 原生
//                    activity.startActivity(WebViewActivity.newIntent(activity,
//                            jsResondEntity.getLink_url()));
////                        if (navName.equals("zhougongjiemeng")) {
////
////                        } else if (navName.equals("yijingzhanbu")) {
////
////                        } else if (navName.equals("zengyunhongbao")) {
////
////                        } else {
////
////                        }
//                } else if (jumpType == 2) {
//                    if (navName.equals("mingshu")) {
//
//                    } else if (navName.equals("kongmingzuoguan")) {
//
//                    } else if (navName.equals("meiriqifu")) {
//
//                    }
//                }


     //       }
        });
    }


    @JavascriptInterface
    public void pushEverydayRecommend(String jsonString) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                LogUtils.d(TAG,
                        "pushEverydayRecommend jsonString " + jsonString);
                activity.startActivity(FindActivity.newIntent(activity));
            }
        });
    }


    @JavascriptInterface
    public void back(String string) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                LogUtils.d(TAG, "closePage string " + string);

            }
        });
    }

    @JavascriptInterface
    public void closePage(String string) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                LogUtils.d(TAG, "closePage");
                activity.finish();
            }
        });
    }

    @JavascriptInterface
    public void buyVip(String id) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                LogUtils.d(TAG, "buyVip String " + id);
                //JSON获取单个对象
                JsonObject properies = JsonParser.parseString(id).getAsJsonObject();
                int member_id = properies.get("id").getAsInt();

                activity.startActivity(PayMethodActivity.newIntent(activity, 0, member_id, 1,
                        null, 2));
            }
        });
    }


    @JavascriptInterface
    public void goOrder(String id) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                LogUtils.d(TAG, "goOrder String " + id);
                //跳转类型-1列表,2详情
                if (mJumpType == 1) {
                    activity.startActivity(new Intent(activity,
                            MyOrderActivity.class));
                } else if (mJumpType == 2) {
                    OrderDetailActivity.start(activity,
                            mOrderID);
                }

            }
        });
    }


}
