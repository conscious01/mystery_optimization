package com.zgzx.metaphysics.utils;

import android.content.Context;

import com.zgzx.metaphysics.Constants;
import com.zgzx.metaphysics.R;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    //数组转list
    public static List convertStringToList(String str, String mark) {
        String[] strArray = str.split(mark);
        List list = Arrays.asList(strArray);
        return list;
    }
//            int USER_ORDER_STATUS_NOT_PAID = 1;
//            int USER_ORDER_STATUS_WAIT_ANSWER = 2;
//            int USER_ORDER_STATUS_WAIT_COMMENT = 3;
//            int USER_ORDER_STATUS_CLOSED = 4;
//            int USER_ORDER_STATUS_COMPLETED = 5;


    public static List convertStringArryaToList(String[] str) {
        List list = Arrays.asList(str);
        return list;
    }

    public static String getUserOrderString(int orderStatus, Context context) {
        if (orderStatus == Constants.USER_ORDER_STATUS_NOT_PAID) {
            return context.getString(R.string.order_status_not_paied);
        }
        if (orderStatus == Constants.USER_ORDER_STATUS_WAIT_ANSWER) {
            return context.getString(R.string.order_status_wait_answer);
        }
        if (orderStatus == Constants.USER_ORDER_STATUS_WAIT_COMMENT) {
            return context.getString(R.string.order_status_wait_comment);
        }
        if (orderStatus == Constants.USER_ORDER_STATUS_CLOSED) {
            return context.getString(R.string.order_status_closed);
        }
        if (orderStatus == Constants.USER_ORDER_STATUS_COMPLETED) {
            return context.getString(R.string.order_status_completed);
        }
        if (orderStatus == Constants.USER_ORDER_STATUS_CANCELED) {
            return context.getString(R.string.order_status_closed);
        }
        if (orderStatus == Constants.USER_ORDER_STATUS_REJECT) {
            return context.getString(R.string.order_status_closed);
        }
        if (orderStatus == Constants.USER_ORDER_STATUS_OVER_TIME) {
            return context.getString(R.string.order_status_closed);
        }

        return "";
    }


    public static String getMasterOrderString(int orderStatus, Context context) {
        if (orderStatus == Constants.MASTER_ORDER_STATUS_WAIT_ANSWER) {
            return context.getString(R.string.order_status_wait_answer);
        } else if (orderStatus == Constants.MASTER_ORDER_STATUS_DONE_1) {
            return context.getString(R.string.completed);
        } else if (orderStatus == Constants.MASTER_ORDER_STATUS_DONT2) {
            return context.getString(R.string.completed);
        } else if (orderStatus == Constants.MASTER_ORDER_STATUS_CLOSED_1) {
            return context.getString(R.string.closed);
        } else if (orderStatus == Constants.MASTER_ORDER_STATUS_CLOSED_2) {
            return context.getString(R.string.closed);
        } else if (orderStatus == Constants.MASTER_ORDER_STATUS_REJECT) {
            return context.getString(R.string.closed);
        } else if (orderStatus == Constants.MASTER_ORDER_STATUS_USER_CANCELED) {
            return context.getString(R.string.closed);
        } else {
            return context.getString(R.string.order_status_completed);
        }

    }


    public static boolean ifOrderMasterClosed(int status) {
        if (status == Constants.MASTER_ORDER_STATUS_CLOSED_1) {
            return true;
        }
        if (status == Constants.MASTER_ORDER_STATUS_USER_CANCELED) {
            return true;
        }
        if (status == Constants.MASTER_ORDER_STATUS_REJECT) {
            return true;
        }
        if (status == Constants.MASTER_ORDER_STATUS_CLOSED_2) {
            return true;
        }

        return false;
    }


    public static String getGenderString(int gender) {
        if (gender == 1) { //男性
            return "男";
        } else {
            return "女";
        }
    }

    //1-农历，2-阳历
    public static String getCalenderString(int type, Context context) {
        if (type == 1) { //男性
            return context.getString(R.string.nongli);
        } else {
            return context.getString(R.string.yangli);
        }
    }


    public static String getScoreDes(int score, Context context) {
        if (score == 5) {
            return context.getString(R.string.score_5);

        } else if (score == 4) {
            return context.getString(R.string.score_4);
        } else if (score == 3) {
            return context.getString(R.string.score_3);

        } else if (score == 2) {
            return context.getString(R.string.score_2);

        } else if (score == 1) {
            return context.getString(R.string.score_1);

        }
        return "";
    }


    /**
     * @param countname 判断是否存在汉字
     * @return
     */
    public static boolean checkHasChinese(String countname) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(countname);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * @param name 判断整个字符串都由汉字组成
     * @return
     */
    public static boolean checkAllChinese(String name) {
        int n = 0;
        for (int i = 0; i < name.length(); i++) {
            n = (int) name.charAt(i);
            if (!(19968 <= n && n < 40869)) {
                return false;
            }
        }
        return true;
    }
}
