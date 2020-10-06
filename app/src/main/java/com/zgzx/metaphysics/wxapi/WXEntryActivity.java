package com.zgzx.metaphysics.wxapi;

import android.app.Activity;
import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.umeng.socialize.weixin.view.WXCallbackActivity;


public class WXEntryActivity extends WXCallbackActivity {
    @Override
    public void onReq(BaseReq req) {

        super.onReq(req);
        ToastUtils.showShort(req.openId);
    }

}
