package com.zgzx.metaphysics.network;

/**
 * API 接口参数常量
 */
public interface WebApiConstants {

    // -------------------------------- 语言相关字段 --------------------------------
    String LANGUAGE_ZH_CN = "zh-CN",  // 中文简体
            LANGUAGE_ZH_TW = "zh-TW"; // 中文繁体
    // -------------------------------- 语言相关字段 --------------------------------


    // -------------------------------- 用户相关字段 --------------------------------
    // 性别
    int SEX_MAN = 1, SEX_WOMAN = 2;

    // 角色
    int GENERAL_ROLE = 1, // 普通用户
            MASTER_ROLE = 2, // 师傅
            SUPER_SHAREHOLDER_ROLE = 4, // 大股东（超级股东）
            PARTNER_ROLE = 5; // 小股东（超级合伙人）
    // -------------------------------- 用户相关字段 --------------------------------


    // -------------------------------- 师傅相关字段 --------------------------------
    // 师傅服务状态
    int MASTER_NORMAL_STATUS = 1, // 正常状态
            MASTER_STOP_STATUS = 2; // 停止状态

    // 师傅服务列表排序
    int MASTER_SORT_DEFAULT = 0,// 默认
            MASTER_SORT_SCORE = 1, // 评分
            MASTER_SORT_ANSWER = 2, // 回答数量
            MASTER_SORT_PRICE = 3; // 价格

    int SORT_POSITIVE_ORDER = 1, // 正序
            SORT_REVERSE_ORDER = 2; // 倒序
    // -------------------------------- 师傅相关字段 --------------------------------


    // -------------------------------- 命书相关字段 --------------------------------
    int FATE_BOOK_SELF = 1; // 自己的命书

    int PAY_TYPE_KMZ = 1, // 孔明珠支付类型
            PAY_TYPE_SINGLE = 2,  // 单个命书购买
            PAY_TYPE_ALL = 3; // 整本命书购买
    // -------------------------------- 命书相关字段 --------------------------------

}
