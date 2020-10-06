package com.zgzx.metaphysics;

public interface Constants {

    // 协议 --------------------
    String SERVICE_PROTOCOL = BuildConfig.DOMAIN + "/protocol/", // 用户协议
            POLICY_PROTOCOL = BuildConfig.DOMAIN + "/policy/"; // 隐私协议

    // 师傅介绍长度
    int MASTER_INTRODUCTION_LENGTH = 200;

    // 照片数量
    int MASTER_PHOTO_SIZE = 20,//师傅个人照片数量
            QUESTION_PHOTO_SIZE = 3; // 提问照片数量

    // 最大命书数据
    int MAX_FATE_BOOK_NUMBER = 10;

    int REQ_ARE_CODE = 1; // 区号

    String REQ_RESULT = "RESULT"; // 结果

    // 请求延时
    int REQUEST_DELAY = 250;

    String EXTRA_URL = "EXTRA_URL"; // 链接

    String EXTRA_AREA_CODE = "AREA_CODE"; // 区号

    String EXT_PARCELABLE = "PARCELABLE";

    int RQC_WRITE_EXTERNAL_STORAGE = 3; // 保存图片


    String EXT_AMOUNT = "AMOUNT";
    String EXT_TYPE = "TYPE";
    String EXT_ID = "ID";
    int HOME_REQUEST_CODE_FORYUNE = 0X002;
    int HOME_REQUEST_CODE_FATE_BOOK = 0X001;
    String WEB_URL = "http://192.168.1.53:8080";

    public final static int PAY_ALI = 1;
    public final static int PAY_WECHAT = 2;
    public static final String TYPE = "TYPE_";
    String KEY = "KEY";
    int USER_ORDER_STATUS_NOT_PAID = 1;
    int USER_ORDER_STATUS_WAIT_ANSWER = 2;
    int USER_ORDER_STATUS_WAIT_COMMENT = 3;
    int USER_ORDER_STATUS_CLOSED = 4;
    int USER_ORDER_STATUS_COMPLETED = 5;

    //6已取消,7已拒绝,8超时关闭
    int USER_ORDER_STATUS_CANCELED = 6;
    int USER_ORDER_STATUS_REJECT = 7;
    int USER_ORDER_STATUS_OVER_TIME = 8;


    int USER_ORDER_STATUS_ALL = 0;


//    问答状态:2待解答(只有这一种状态).
//    tips:师傅视角显示的已完成包含待评价和已完成,即status=3和5.
//    关闭:4,超时72小时师傅未接单则关闭;
//    6已取消(用户取消订单);7已拒绝(师傅拒单);8一小时未支付关闭则订单

    int MASTER_ORDER_STATUS_WAIT_ANSWER = 2;
    int MASTER_ORDER_STATUS_ALL = 0;
    int MASTER_ORDER_STATUS_DONE_1 = 3;
    int MASTER_ORDER_STATUS_DONT2 = 5;
    int MASTER_ORDER_STATUS_CLOSED_1 = 4;
    int MASTER_ORDER_STATUS_USER_CANCELED = 6;
    int MASTER_ORDER_STATUS_REJECT = 7;
    int MASTER_ORDER_STATUS_CLOSED_2 = 8;


    static final String WX_APP_ID = "wx6e1840953b329781";
    int SWITCH_FORTUNE = 1;
    int SWITCH_MESSAGE = 2;
    String KEY_NAME = "KEY_NAME";

    /**
     * 周公解梦
     */
    int WEB_VIEW_TYPE_ZGJM = 1;
    /**
     * 易经占卜
     */
    int WEB_VIEW_TYPE_YJZB = 2;
}
