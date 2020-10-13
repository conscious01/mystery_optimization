package com.zgzx.metaphysics.utils;

import android.content.Context;
import android.view.View;

import com.zgzx.metaphysics.R;
import com.zgzx.metaphysics.ui.dialogs.OrderCommonDialog;

public class SystemUtils {

    public static void leadOpenNotification(Context context) {
        if (!AppNotificationJava.isNotificationEnabled(context)) {
            OrderCommonDialog.show(context, context.getString(R.string.warm_hint),
                    context.getString(R.string.sure_to_open_notification), new OrderCommonDialog.DialogClick() {
                        @Override
                        public void onLeftButton() {
                            OrderCommonDialog.disMiss();
                        }

                        @Override
                        public void onRightButton() {
                            AppNotificationJava.openNotification(context);
                            OrderCommonDialog.disMiss();

                        }
                    }, View.GONE);

        }
    }
}
