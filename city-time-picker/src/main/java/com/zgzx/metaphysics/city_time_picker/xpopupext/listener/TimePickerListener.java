package com.zgzx.metaphysics.city_time_picker.xpopupext.listener;


public interface TimePickerListener {

    /**
     * @param time         显示的时间
     * @param timestamp    选择的年月日 时间戳 timestamp
     * @param hour         选择的小时 hour
     * @param calendarType 农历或者公历 calendarType (1-农历，2-阳历)
     */
    void onTimeConfirm(String time, int timestamp, int hour, int calendarType);

}
