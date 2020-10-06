package com.zgzx.metaphysics.ui.dialogs;

import android.content.Context;

import com.lxj.xpopup.XPopup;
import com.zgzx.metaphysics.city_time_picker.xpopupext.listener.TimePickerListener;
import com.zgzx.metaphysics.city_time_picker.xpopupext.popup.TimePickerPopup;


/**
 * 出生日期，农历、公历
 */
public class BirthDateDialog {

    public static void show(Context context, TimePickerListener listener) {
        TimePickerPopup popup = new TimePickerPopup(context)
                .setTimePickerListener(listener);

        popup.setMode(TimePickerPopup.Mode.YMDH);
        popup.setLunar(false);
        new XPopup.Builder(context)
                .asCustom(popup)
                .show();
    }

}
