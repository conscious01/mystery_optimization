package com.zgzx.metaphysics.model;

import com.zgzx.metaphysics.city_time_picker.xpopupext.bean.DomesticJsonBean;
import com.zgzx.metaphysics.city_time_picker.xpopupext.bean.ForeignJsonBean;
import com.zgzx.metaphysics.model.entity.AdEntity;
import com.zgzx.metaphysics.model.entity.AddfortuneDataEntity;
import com.zgzx.metaphysics.model.entity.AreaCodeEntity;
import com.zgzx.metaphysics.model.entity.ArticleEntity;
import com.zgzx.metaphysics.model.entity.ArticleListEntity;
import com.zgzx.metaphysics.model.entity.BannerEntity;
import com.zgzx.metaphysics.model.entity.BasicListResponseEntity;
import com.zgzx.metaphysics.model.entity.BasicResponseEntity;
import com.zgzx.metaphysics.model.entity.CalendarDayEntity;
import com.zgzx.metaphysics.model.entity.CalendarDetailEntity;
import com.zgzx.metaphysics.model.entity.CalendarHourEntity;
import com.zgzx.metaphysics.model.entity.CatePriceEntity;
import com.zgzx.metaphysics.model.entity.DailyQuestionEntity;
import com.zgzx.metaphysics.model.entity.FateBookDetailEntity;
import com.zgzx.metaphysics.model.entity.FateBookEntity;
import com.zgzx.metaphysics.model.entity.FateBookTypeEntity;
import com.zgzx.metaphysics.model.entity.FateBooksEntity;
import com.zgzx.metaphysics.model.entity.FortuneEntity;
import com.zgzx.metaphysics.model.entity.H5BaseEntity;
import com.zgzx.metaphysics.model.entity.HomeDataEntity;
import com.zgzx.metaphysics.model.entity.HomeFouncationEntity;
import com.zgzx.metaphysics.model.entity.InvitationSummaryEntity;
import com.zgzx.metaphysics.model.entity.InviteListEntity;
import com.zgzx.metaphysics.model.entity.MasterCommentEntity;
import com.zgzx.metaphysics.model.entity.MasterDetailEntityNew;
import com.zgzx.metaphysics.model.entity.MasterServiceEntity;
import com.zgzx.metaphysics.model.entity.MasterServiceSettingEntity;
import com.zgzx.metaphysics.model.entity.MasterServiceTypeEntity;
import com.zgzx.metaphysics.model.entity.MessageEntity;
import com.zgzx.metaphysics.model.entity.MonthBlessEntity;
import com.zgzx.metaphysics.model.entity.OrderLifeBookEntity;
import com.zgzx.metaphysics.model.entity.OrderMemberEntity;
import com.zgzx.metaphysics.model.entity.OrderResultEntity;
import com.zgzx.metaphysics.model.entity.PrePayResult;
import com.zgzx.metaphysics.model.entity.PropertyBillEntity;
import com.zgzx.metaphysics.model.entity.QDetailEntity;
import com.zgzx.metaphysics.model.entity.RechargeActivitiesEntity;
import com.zgzx.metaphysics.model.entity.RechargeRecordEntity;
import com.zgzx.metaphysics.model.entity.RegisterEntity;
import com.zgzx.metaphysics.model.entity.SwitchStatusEntity;
import com.zgzx.metaphysics.model.entity.SystemMessageEntity;
import com.zgzx.metaphysics.model.entity.TrendEntity;
import com.zgzx.metaphysics.model.entity.UrlConfigEntity;
import com.zgzx.metaphysics.model.entity.UserAssetEntity;
import com.zgzx.metaphysics.model.entity.UserDetailEntity;
import com.zgzx.metaphysics.model.entity.VersionUpdateEntity;
import com.zgzx.metaphysics.model.entity.VipTypeEntity;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;

/**
 * 数据源
 */
public interface IDataSource {

    /**
     * 上传文件
     */
    Observable<BasicResponseEntity<List<String>>> upload(List<File> files);

    /**
     * 区号数据
     */
    Observable<BasicResponseEntity<List<AreaCodeEntity>>> phoneCode();

    /**
     * 国内地区数据
     */
    Observable<BasicResponseEntity<List<DomesticJsonBean>>> areaChina();

    /**
     * 海外地区数据
     */
    Observable<BasicResponseEntity<List<ForeignJsonBean>>> areaOversea();

    /**
     * 版本更新
     */
    Observable<BasicResponseEntity<VersionUpdateEntity>> versionUpdate();

    /**
     * 发送短信验证码
     *
     * @param scene 1-注册 2-忘记登录密码 3-修改登录密码 4-修改支付密码
     */
    Observable<BasicResponseEntity<Object>> send(String countryCode, String phone, int scene);


    /**
     * 注册
     *
     * @param countryCode 区号，不填默认为国内
     * @param phone       手机号
     * @param verifyCode  验证码
     * @param password    登录密码，明文
     * @param inviteCode  邀请码
     */
    Observable<BasicResponseEntity<RegisterEntity>> register(
            String countryCode,
            String phone,
            String verifyCode,
            String password,
            String inviteCode
    );


    /**
     * 完善用户信息
     *
     * @param name         姓名
     * @param sex          性别，1-男，2-女
     * @param birthDay     出生年月日，格式：1595398597
     * @param birthHour    出生时辰，[-1]-未知，[1-24]-其他
     * @param calendarType 日历类型，1-农历，2-阳历
     * @param birthArea    出生地区
     */
    Observable<BasicResponseEntity<UserDetailEntity>> completeInfo(
            String birth_time,
            String avatar,
            String name,
            int sex,
            long birthDay,
            int birthHour,
            int calendarType,
            String birthArea
    );


    /**
     * 登录
     */
    Observable<BasicResponseEntity<UserDetailEntity>> login(
            String phone,
            String countryCode,
            String password
    );


    /**
     * 验证验证码
     */
    Observable<BasicResponseEntity<Object>> verify(
            String phone,
            String countryCode,
            String verifyCode
    );


    /**
     * 重置密码
     */
    Observable<BasicResponseEntity<Object>> forgetLoginPassword(
            String password, // 登录密码，明文，格式：8-20位数字+字母/符号组合
            String phone, // 手机号
            String countryCode, // 区号，不填默认为国内
            String verifyCode // 手机验证码，短信服务关闭时默认”12345”
    );

    /**
     * 修改登录密码
     */
    Observable<BasicResponseEntity<Object>> updateLoginPassword(
            String password, // 登录密码，明文，格式：8-20位数字+字母/符号组合
            String phone, // 手机号
            String countryCode, // 区号，不填默认为国内
            String verifyCode // 手机验证码，短信服务关闭时默认”12345”
    );

    /**
     * 修改支付密码
     */
    Observable<BasicResponseEntity<Object>> updatePayPassword(
            String password, // 登录密码，明文，格式：8-20位数字+字母/符号组合
            String phone, // 手机号
            String countryCode, // 区号，不填默认为国内
            String verifyCode // 手机验证码，短信服务关闭时默认”12345”
    );

    /**
     * 用户信息
     */
    Observable<BasicResponseEntity<UserDetailEntity>> userDetail();


    /**
     * 更新用户头像
     */
    Observable<BasicResponseEntity<Object>> updateAvatar(String avatarPath);


    /**
     * 更新用户信息
     */
    Observable<BasicResponseEntity<Object>> updateUserDetail(
            String birth_time,
            String nickname,
            String name,
            int sex,
            long birthDay,
            int birthHour,
            int calendarType,
            String birthArea,
            String liveArea
    );


    /**
     * 申请成为师傅
     */
    Observable<BasicResponseEntity<Object>> applyMaster(
            String content,
            String phone,
            List<String> paths
    );


    /**
     * 申请状态
     */
    Observable<BasicResponseEntity<Integer>> applyMasterStatus();


    /**
     * 师傅服务类型
     */
    Observable<BasicResponseEntity<List<MasterServiceTypeEntity>>> serviceType();


    /**
     * 师傅服务列表
     */
//    Observable<BasicListResponseEntity<MasterServiceEntity>> serviceList(
//            int page,
//            int typeId, // 项目类型
//            int sortType, // 排序类型，1评分; 2解答次数; 3收费金额
//            int priceSort // 价格排序
//    );

    /**
     * 师傅服务列表
     */
    Observable<BasicListResponseEntity<MasterServiceEntity>> serviceList(
            int page
    );

    /**
     * 师傅信息
     */
//    Observable<BasicResponseEntity<MasterDetailEntity>> masterDetail();


    /**
     * 更新师傅介绍
     */
    Observable<BasicResponseEntity<Object>> updateMasterIntro(String intro);


    /**
     * 更新师傅领域
     */
    Observable<BasicResponseEntity<Object>> updateMasterFields(List<Integer> fields);


    /**
     * 发现页面师傅信息
     *
     * @return 师傅查看自己传uid，用户查看师傅传masterid
     */
    Observable<BasicResponseEntity<MasterDetailEntityNew>> masterDetail(int masterid,int uid, int user_id);


    /**
     * @param page     评价
     * @param pagesize
     * @param uid
     * @return
     */
    Observable<BasicResponseEntity<MasterCommentEntity>> getMasterComment(int page, int pagesize,
                                                                          int uid,int masterid);


    /**
     * 获取师傅所有服务项目
     */
    Observable<BasicResponseEntity<MasterServiceSettingEntity>> masterServices();


    /**
     * 个人运程
     */
    Observable<BasicResponseEntity<FortuneEntity>> personalFortune(int timestamp, int type);


    /**
     * 命书列表
     */
    Observable<BasicResponseEntity<List<FateBooksEntity>>> fateBooksList();


    /**
     * 分类
     */
    Observable<BasicResponseEntity<List<FateBookTypeEntity>>> fateBookTypes(int fateBookId);


    /**
     * 命书详情
     */
    Observable<BasicResponseEntity<FateBookDetailEntity>> fateBookDetail(int fateBookId,
                                                                         int cateId);


    /**
     * 创建命书
     */
    Observable<BasicResponseEntity<FateBookEntity>> createFateBook(
            String name,
            int sex,
            long birthDay,
            int birthHour
    );


    /**
     * 删除命书
     */
    Observable<BasicResponseEntity<Object>> deleteFateBook(int id);


    /**
     * 购买命书
     *
     * @param id 命书ID
     */
    Observable<BasicResponseEntity<Object>> buyFateBook(int id, String password, int isAll,
                                                        int type, String cateId,String ak,
                                                        long timestamp,
                                                        String sign);

    /**
     * 创建命书订单
     *
     * @param id 命书ID
     */
    Observable<BasicResponseEntity<OrderLifeBookEntity>> orderLifeBookEntity(int id, String cateId, String ak,
                                                                             long timestamp,
                                                                             String sign);

    /**
     * 创建会员订单
     *
     * @param id 命书ID
     */
    Observable<BasicResponseEntity<OrderMemberEntity>> orderMember(int id,String ak,
                                                                   long timestamp,
                                                                   String sign);

    /**
     * 孔明珠余额
     */
    Observable<BasicResponseEntity<Float>> balance();


    /**
     * 财务记录
     *
     * @param type 1 充值, 2 提现,3 问答,4 分佣, 5 奖励, 6 划转, 7 其他
     */
    Observable<BasicListResponseEntity<PropertyBillEntity>> propertyBill(int page, int type);


    /**
     * 系统消息列表
     */
    Observable<BasicListResponseEntity<SystemMessageEntity>> systemMessage(int page);


    /**
     * 划转
     */
    Observable<BasicResponseEntity<Object>> transfer(long uid, float amount, String password, long timestamp, String ak, String sign);


    /**
     * 我的邀请
     */
//    Observable<BasicResponseEntity<InviteListEntity>> inviteList(int page);


    /**
     * 万年历
     */
    Observable<BasicResponseEntity<CalendarDetailEntity>> calendarDetail(int timestamp);


    /**
     * 获取banner列表
     */
    Observable<BasicResponseEntity<List<BannerEntity>>> bannerList();


    /**
     * 通知消息
     */
    Observable<BasicListResponseEntity<SystemMessageEntity>> noticeList(int page);


    /**
     * 获取命书分类价格
     */
    Observable<BasicResponseEntity<CatePriceEntity>> getCatePrice(int id);


    /**
     * 充值列表
     */
    Observable<BasicResponseEntity<List<RechargeActivitiesEntity>>> rechargeActivitiesList();


    /**
     * VIP列表
     */
    Observable<BasicResponseEntity<List<VipTypeEntity>>> vipList();


    /**
     * 我的邀请-概述
     */
    Observable<BasicResponseEntity<InvitationSummaryEntity>> invitationSummary();


    /**
     * 我的邀请-充值记录
     */
    Observable<BasicListResponseEntity<RechargeRecordEntity>> rechargeRecord(int todaySort,
                                                                             int totalSort,
                                                                             int page);


    /**
     * 我的邀请-邀请记录
     */
    Observable<BasicListResponseEntity<InviteListEntity>> inviteList(int todaySort, int totalSort
            , int page);


    /**
     * 主页
     */
    Observable<BasicResponseEntity<HomeDataEntity>> homeData();


    /**
     * 购买命书的预生成订单
     */
    Observable<BasicResponseEntity<PrePayResult>> buyFateBookPrepay(int pay_type, String order_no,
                                                                    int discount_id, String ak,
                                                                    long timestamp,
                                                                    String sign);

    /**
     * 购买会员的预生成订单
     */
    Observable<BasicResponseEntity<PrePayResult>> buyMemberPrepay(int pay_type, String order_no, int discount_id, String ak,
                                                                  long timestamp,
                                                                  String sign);


    /**
     * @param pay_type
     * @param issue_id
     * @return 提问付款
     */
    Observable<BasicResponseEntity<PrePayResult>> buyQuestion(int pay_type, int issue_id, String ak,
                                                              long timestamp,
                                                              String sign);

    /**
     * 首页功能模块
     */
    Observable<BasicResponseEntity<HomeFouncationEntity>> entry();

    /**
     * 时辰宜忌
     *
     * @return
     */
    Observable<BasicResponseEntity<List<CalendarHourEntity>>> calendarHour();

    /**
     * 今日宜忌
     */
    Observable<BasicResponseEntity<CalendarDayEntity>> calendarDay();

    /**
     * 首页文章
     */
    Observable<BasicResponseEntity<ArticleListEntity>> articleCateList(int cate_id, int page,
                                                                       int page_size);

    /**
     * 首页文章
     */
    Observable<BasicResponseEntity<List<ArticleEntity>>> articleCate();

    /**
     * banner
     *
     * @return
     */
    Observable<BasicResponseEntity<List<BannerEntity>>> banner(int type);

    /**
     * 每日一问
     */
    Observable<BasicResponseEntity<List<DailyQuestionEntity>>> getDailyQuestion(int index);

    /**
     * 每日一问
     */
    Observable<BasicResponseEntity<TrendEntity>> getTrendData();

    /**
     * 获取祈福
     */
    Observable<BasicResponseEntity<MonthBlessEntity>> getMonthBless(String month);

    /**
     * 添加祈福
     */
    Observable<BasicResponseEntity<String>> addMonthBless(String content);


    /**
     * 提交问题
     *
     * @return
     */
    Observable<BasicResponseEntity<OrderResultEntity>> askQuestion(String nickname, int gender,
                                                                   int master_id, int birth_day,
                                                                   int birth_hour, int calendar_type,
                                                                   String birth_area, String content, String paths,String ak,
                                                                   long timestamp,
                                                                   String sign
    );

    Observable<BasicListResponseEntity<OrderResultEntity>> getOrderMaster(int status, int page,
                                                                          int page_size);


    Observable<BasicListResponseEntity<OrderResultEntity>> getOrderUser(int status, int page,
                                                                        int page_size);

    Observable<BasicResponseEntity<QDetailEntity>> getAnswerDetailMast(int issue_id);

    Observable<BasicResponseEntity<QDetailEntity>> getAnswerDetailUser(int issue_id);

    Observable<BasicResponseEntity<String>> doneAnswer(int issue_id, String content,String ak,
                                                       long timestamp,
                                                       String sign);

    Observable<BasicResponseEntity<String>> orderRefused(int issue_id, String reason,String ak,
                                                         long timestamp,
                                                         String sign);

    Observable<BasicResponseEntity<String>> orderComment(int issue_id, int score);

    Observable<BasicResponseEntity<String>> orderCancel(int issue_id,String ak,
                                                        long timestamp,
                                                        String sign);

    Observable<BasicListResponseEntity<MessageEntity.ItemsBean>> getMessage(int page);

    Observable<BasicResponseEntity<UserAssetEntity>> getUserAssets();

    Observable<BasicResponseEntity<H5BaseEntity>> getH5URL();


    Observable<BasicResponseEntity<UrlConfigEntity>> getConfigURL();

    Observable<BasicResponseEntity<SwitchStatusEntity>> getSwitchStatus();

    Observable<BasicResponseEntity<String>> updateSwitchFortune(int fortune_switch);

    Observable<BasicResponseEntity<String>> updateSwitchMsg(int fortune_switch);
    Observable<BasicResponseEntity<Float>> getTranFee();

    Observable<BasicResponseEntity<String>> sendFeedBack(String content,String images);

    /**
     *获取广告配置
     *
     * @return
     */
    Observable<BasicResponseEntity<AdEntity>> getAdConfig();

    /**
     * @param timestamp
     * @return 获取增运数据
     */
    Observable<BasicResponseEntity<AddfortuneDataEntity>> getAddfortuneData(int timestamp);
}
