package com.zgzx.metaphysics.network;

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
import com.zgzx.metaphysics.model.entity.MasterDetailEntity;
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
import com.zgzx.metaphysics.model.params.AddBlessMonthParams;
import com.zgzx.metaphysics.model.params.ApplyMasterParams;
import com.zgzx.metaphysics.model.params.ArticleParams;
import com.zgzx.metaphysics.model.params.BannerParams;
import com.zgzx.metaphysics.model.params.BlessMothParams;
import com.zgzx.metaphysics.model.params.CommentParams;
import com.zgzx.metaphysics.model.params.CreateFateBookParams;
import com.zgzx.metaphysics.model.params.DailyQuestionParams;
import com.zgzx.metaphysics.model.params.FateBookParams;
import com.zgzx.metaphysics.model.params.FeedBackParams;
import com.zgzx.metaphysics.model.params.InvitationParams;
import com.zgzx.metaphysics.model.params.LoginParams;
import com.zgzx.metaphysics.model.params.MasterCommentParams;
import com.zgzx.metaphysics.model.params.MasterDetailParams;
import com.zgzx.metaphysics.model.params.MasterFieldsParams;
import com.zgzx.metaphysics.model.params.MasterIntroParams;
import com.zgzx.metaphysics.model.params.MasterListParams;
import com.zgzx.metaphysics.model.params.OrderParams;
import com.zgzx.metaphysics.model.params.Page2Params;
import com.zgzx.metaphysics.model.params.PageParams;
import com.zgzx.metaphysics.model.params.PasswordParams;
import com.zgzx.metaphysics.model.params.PersonalFortuneParams;
import com.zgzx.metaphysics.model.params.PhoneParams;
import com.zgzx.metaphysics.model.params.PropertyBillParams;
import com.zgzx.metaphysics.model.params.QuestionDetailParams;
import com.zgzx.metaphysics.model.params.RegisterParams;
import com.zgzx.metaphysics.model.params.SwitchFortuneParams;
import com.zgzx.metaphysics.model.params.SwitchMsgParams;
import com.zgzx.metaphysics.model.params.TimestampParams;
import com.zgzx.metaphysics.model.params.UserInfoParams;
import com.zgzx.metaphysics.model.params.VerifyParams;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface WebServiceApi {

    /**
     * 上传文件
     * <p>
     * 文件名称 upload[]
     */
    @Multipart
    @POST("/api/v1/upload/multi")
    Observable<BasicResponseEntity<List<String>>> upload(@PartMap Map<String, RequestBody> files);

    /**
     * 区号数据
     */
    @GET("/api/v1/open/phone_code")
    Observable<BasicResponseEntity<List<AreaCodeEntity>>> phoneCode();


    @GET("/api/v1/config/h5_domain")
    Observable<BasicResponseEntity<H5BaseEntity>> getH5BaseUrl();

    @GET("/api/v1/config/get_base_config")
    Observable<BasicResponseEntity<UrlConfigEntity>> getConfigurl();

    /**
     * 国内地区数据
     */
    @GET("/api/v1/open/area/china")
    Observable<BasicResponseEntity<List<DomesticJsonBean>>> areaChina();

    /**
     * 海外地区数据
     */
    @GET("/api/v1/open/area/oversea")
    Observable<BasicResponseEntity<List<ForeignJsonBean>>> areaOversea();

    /**
     * 版本更新
     */
    @GET("/api/v1/open/app_version")
    Observable<BasicResponseEntity<VersionUpdateEntity>> versionUpdate();


    /**
     * 发送短信验证码
     */
    @POST("/api/v1/open/sms/send")
    Observable<BasicResponseEntity<Object>> send(@Body PhoneParams params);

    /**
     * 注册
     */
    @POST("/api/v1/open/register")
    Observable<BasicResponseEntity<RegisterEntity>> register(@Body RegisterParams params);

    /**
     * 完善用户信息
     */
    @POST("/api/v1/user/complete_info")
    Observable<BasicResponseEntity<UserDetailEntity>> completeInfo(@Body UserInfoParams params);

    /**
     * 登录
     */
    @POST("/api/v1/open/login")
    Observable<BasicResponseEntity<UserDetailEntity>> login(@Body LoginParams params);

    /**
     * 验证验证码
     */
    @POST("/api/v1/open/sms/verify")
    Observable<BasicResponseEntity<Object>> verify(@Body VerifyParams params);

    /**
     * 忘记登录密码
     */
    @POST("/api/v1/open/forget_login_pass")
    Observable<BasicResponseEntity<Object>> forgetLoginPassword(@Body PasswordParams params);

    /**
     * 修改登录密码
     */
    @POST("/api/v1/user/update_login_pass")
    Observable<BasicResponseEntity<Object>> updateLoginPassword(@Body PasswordParams params);

    /**
     * 修改支付密码
     */
    @POST("/api/v1/user/update_pay_pass")
    Observable<BasicResponseEntity<Object>> updatePayPassword(@Body PasswordParams params);

    /**
     * 获取用户信息
     */
    @GET("/api/v1/user/get_info")
    Observable<BasicResponseEntity<UserDetailEntity>> userDetail();

    /**
     * 更新用户头像
     */
    @POST("/api/v1/user/update_avatar")
    Observable<BasicResponseEntity<Object>> updateAvatar(@Body UserInfoParams params);

    /**
     * 更新用户信息
     */
    @POST("/api/v1/user/update_info")
    Observable<BasicResponseEntity<Object>> updateUserDetail(@Body UserInfoParams params);

    /**
     * 申请成为师傅
     */
    @POST("/api/v1/master/apply")
    Observable<BasicResponseEntity<Object>> applyMaster(@Body ApplyMasterParams params);

    /**
     * 申请状态
     * <p>
     * 审核状态，0-未审核，1-申请资质审核中，2-资质审核通过，3-资质审核失败，4-申请注销审核中，5-申请注销通过，6-申请注销失败
     */
    @GET("/api/v1/master/status")
    Observable<BasicResponseEntity<Integer>> applyStatus();

    /**
     * 申请注销
     */
    @POST("/api/v1/master/cancel")
    Observable<BasicResponseEntity<Object>> applyMasterCancel();

    /**
     * 师傅信息
     */
    @POST("/api/v1/dis/query_master_info")
    Observable<BasicResponseEntity<MasterDetailEntity>> masterDetail(@Body MasterDetailParams params);

    /**
     * 更新师傅介绍
     */
    @POST("/api/v1/master/update_intro")
    Observable<BasicResponseEntity<Object>> updateMasterIntro(@Body MasterIntroParams params);

    /**
     * 更新师傅领域
     */
    @POST("/api/v1/master/update_fields")
    Observable<BasicResponseEntity<Object>> updateMasterFields(@Body MasterFieldsParams params);

    /**
     * 获取师傅所有服务项目
     */
    @GET("/api/v1/master/services")
    Observable<BasicResponseEntity<MasterServiceSettingEntity>> masterServices();

    /**
     * 师傅列表
     */
    @POST("/api/v1/dis/query_master_list")
    Observable<BasicListResponseEntity<MasterServiceEntity>> masterServiceList(@Body MasterListParams params);

    /**
     * 师傅服务类型
     */
    @POST("/api/v1/dis/query_item_list")
    Observable<BasicResponseEntity<List<MasterServiceTypeEntity>>> masterServiceType();

    /**
     * 个人运程
     */
    @POST("/api/v1/fortune/get_fortune")
    Observable<BasicResponseEntity<FortuneEntity>> personalFortune(@Body PersonalFortuneParams params);


    /**
     * 命书列表
     */
    @POST("/api/v1/lifebook/get_lifebook_list")
    Observable<BasicResponseEntity<List<FateBooksEntity>>> fateBooksList();


    /**
     * 获取命书分类
     */
    @POST("/api/v1/lifebook/get_cate")
    Observable<BasicResponseEntity<List<FateBookTypeEntity>>> fateBookTypes(@Body FateBookParams params);


    /**
     * 根据命书分类获取详情
     */
    @POST("/api/v1/lifebook/get_detail_by_cate")
    Observable<BasicResponseEntity<FateBookDetailEntity>> fateBookDetail(@Body FateBookParams params);


    /**
     * 添加命书
     */
    @POST("/api/v1/lifebook/create")
    Observable<BasicResponseEntity<FateBookEntity>> createFateBook(@Body CreateFateBookParams params);


    /**
     * 删除命书
     */
    @POST("/api/v1/lifebook/delete")
    Observable<BasicResponseEntity<Object>> deleteFateBook(@Body FateBookParams params);


    /**
     * 购买命书
     */
    @POST("/api/v1/lifebook/buy")
    @FormUrlEncoded
    Observable<BasicResponseEntity<Object>> buyFateBook(@Field("lifebook_id") int lifebook_id,
                                                        @Field("pay_pass") String password,
                                                        @Field("cate_id") String cate_ids,
                                                        @Field("is_all") int is_all,
                                                        @Field("pay_type") int pay_type,
                                                        @Field("ak") String ak,
                                                        @Field("timestamp") long timestamp,
                                                        @Field("sign") String sign);


    /**
     * 孔明珠余额
     */
    @POST("/api/v1/wallet/get_balance_kmz")
    Observable<BasicResponseEntity<Float>> balance();


    /**
     * 财务记录
     */
    @POST("/api/v1/wallet/query_asset_log_list")
    Observable<BasicListResponseEntity<PropertyBillEntity>> propertyBill(@Body PropertyBillParams params);


    /**
     * 系统消息列表
     */
    @POST("/api/v1/sys/query_system_msg_list")
    Observable<BasicListResponseEntity<SystemMessageEntity>> systemMessage(@Body Page2Params params);


    /**
     * 划转
     */
    @POST("/api/v1/wallet/transfer_kmz")
    @FormUrlEncoded
    Observable<BasicResponseEntity<Object>> transfer(@Field("to_uid") long uid,
                                                     @Field("num") float amount,
                                                     @Field("pay_pass") String password,
                                                     @Field("ak") String ak,
                                                     @Field("timestamp") long timestamp,
                                                     @Field("sign") String sign);


//    /**
//     * 我的邀请
//     */
//    @POST("/api/v1/user/my_invitation")
//    Observable<BasicResponseEntity<InviteListEntity>> inviteList(@Body PageParams params);


    /**
     * 我的邀请-概述
     */
    @POST("/api/v1/user/invitation/summary")
    Observable<BasicResponseEntity<InvitationSummaryEntity>> invitationSummary();


    /**
     * 我的邀请-充值记录
     */
    @POST("/api/v1/user/invitation/topup_list")
    Observable<BasicListResponseEntity<RechargeRecordEntity>> rechargeRecord(@Body InvitationParams params);


    /**
     * 我的邀请-邀请记录
     */
    @POST("/api/v1/user/invitation/invite_list")
    Observable<BasicListResponseEntity<InviteListEntity>> inviteList(@Body InvitationParams params);


    /**
     * 万年历
     */
    @POST("/api/v1/day/get")
    Observable<BasicResponseEntity<CalendarDetailEntity>> calendarDetail(@Body TimestampParams params);


    /**
     * 获取banner列表
     */
    @POST("/api/v1/sys/query_banner_list")
    Observable<BasicResponseEntity<List<BannerEntity>>> bannerList();


    /**
     * 通知消息
     */
    @POST("/api/v1/sys/query_system_notice_list")
    Observable<BasicListResponseEntity<SystemMessageEntity>> noticeList(@Body Page2Params params);


    /**
     * 获取命书分类价格
     */
    @POST("/api/v1/lifebook/get_cate_price")
    Observable<BasicResponseEntity<CatePriceEntity>> getCatePrice(@Body FateBookParams params);


    /**
     * 获取充值活动列表
     */
    @GET("/api/v1/config/topup_list")
    Observable<BasicResponseEntity<List<RechargeActivitiesEntity>>> rechargeActivitiesList();


    /**
     * 获取充值活动列表
     */
    @GET("/api/v1/config/vip_list")
    Observable<BasicResponseEntity<List<VipTypeEntity>>> vipList();


    /**
     * 首页数据
     */
    @GET("/api/v1/sys/index")
    Observable<BasicResponseEntity<HomeDataEntity>> homeData();

    /**
     * 购买会员的预生成订单
     */
    @POST("/api/v1/pay/prepay/member")
    @FormUrlEncoded
    Observable<BasicResponseEntity<PrePayResult>> buyMemberPrepay(@Field("pay_type") int pay_type,
                                                                  @Field("order_no") String order_no,
                                                                  @Field("discount_id") int discount_id,
                                                                  @Field("ak") String ak,
                                                                  @Field("timestamp") long timestamp,
                                                                  @Field("sign") String sign);


    /**
     * 购买命书的预生成订单
     */
    @POST("/api/v1/pay/prepay/lifebook")
    @FormUrlEncoded
    Observable<BasicResponseEntity<PrePayResult>> buyFateBookPrepay(@Field("pay_type") int pay_type,
                                                                    @Field("order_no") String order_no,
                                                                    @Field("discount_id") int discount_id,
                                                                    @Field("ak") String ak,
                                                                    @Field("timestamp") long timestamp,
                                                                    @Field("sign") String sign);

    /**
     * 首页功能模块
     */
    @GET("/api/v1/sys/entry")
    Observable<BasicResponseEntity<HomeFouncationEntity>> entry();

    /**
     * 时辰宜忌
     */
    @GET("/api/v1/tools/calendar/hour")
    Observable<BasicResponseEntity<List<CalendarHourEntity>>> calendarHour();

    /**
     * 今日宜忌
     */
    @GET("/api/v1/tools/calendar/day")
    Observable<BasicResponseEntity<CalendarDayEntity>> calendarDay();

    /**
     * 首页文章列表
     */
    @POST("/api/v1/sys/article/list")
    Observable<BasicResponseEntity<ArticleListEntity>> articleCateList(@Body ArticleParams params);

    /**
     * 创建命书订单
     */
    @POST("/api/v1/pay/order/lifebook")
    @FormUrlEncoded
    Observable<BasicResponseEntity<OrderLifeBookEntity>> orderLifeBook(@Field("lifebook_id") int lifebook_id,
                                                                       @Field("cate_ids") String order_no,
                                                                       @Field("ak") String ak,
                                                                       @Field("timestamp") long timestamp,
                                                                       @Field("sign") String sign);

    /**
     * 创建会员订单
     */
    @POST("/api/v1/pay/order/member")
    @FormUrlEncoded
    Observable<BasicResponseEntity<OrderMemberEntity>> orderMember(@Field("member_id") int member_id,
                                                                   @Field("ak") String ak,
                                                                   @Field("timestamp") long timestamp,
                                                                   @Field("sign") String sign);

    /**
     * 首页文章目录
     */
    @GET("/api/v1/sys/article/cate")
    Observable<BasicResponseEntity<List<ArticleEntity>>> articleCate();

    /**
     * banner
     */
    @POST("/api/v1/sys/banner")
    Observable<BasicResponseEntity<List<BannerEntity>>> banner(@Body BannerParams params);

    /**
     * 每日一问
     */
    @POST("/api/v1/fortune/get_daily_question")
    Observable<BasicResponseEntity<List<DailyQuestionEntity>>> getDailyQuestion(@Body DailyQuestionParams params);

    /**
     * 运势报告折线图数据
     */
    @GET("/api/v1/fortune/get_trend_data")
    Observable<BasicResponseEntity<TrendEntity>> getTrendData();

    /**
     * 获取当月祈福
     */
    @POST("/api/v1/fortune/get_month_bless")
    Observable<BasicResponseEntity<MonthBlessEntity>> getMonthBless(@Body BlessMothParams params);

    /**
     * 添加当月祈福
     */
    @POST("/api/v1/fortune/add_bless")
    Observable<BasicResponseEntity<String>> addMonthBless(@Body AddBlessMonthParams params);


    /**
     * @param params
     * @return 师傅页面
     */
    @POST("/api/v1/dis/query_master_info")
    Observable<BasicResponseEntity<MasterDetailEntityNew>> getMasterDetail(@Body MasterDetailParams params);


    /**
     * @param params
     * @return 评价
     */
    @POST("/api/v1/dis/query_master_judge")
    Observable<BasicResponseEntity<MasterCommentEntity>> getMasterComment(@Body MasterCommentParams params);


    @POST("/api/v1/dis/add_issue")
    @FormUrlEncoded
    Observable<BasicResponseEntity<OrderResultEntity>> askQuestion(@Field("nickname") String nickname,
                                                                   @Field("sex") int sex,
                                                                   @Field("master_id") int master_id,
                                                                   @Field("birth_day") int birth_day,
                                                                   @Field("birth_hour") int birth_hour,
                                                                   @Field("calendar_type") int calendar_type,
                                                                   @Field("birth_area") String birth_area,
                                                                   @Field("content") String content,
                                                                   @Field("photo_path") String photo_paths,
                                                                   @Field("ak") String ak,
                                                                   @Field("timestamp") long timestamp,
                                                                   @Field("sign") String sign);

    @POST("/api/v1/pay/prepay/master")
    @FormUrlEncoded
    Observable<BasicResponseEntity<PrePayResult>> buyQuestion(@Field("pay_type") int pay_type,
                                                              @Field("issue_id") int order_no,

                                                              @Field("ak") String ak,
                                                              @Field("timestamp") long timestamp,
                                                              @Field("sign") String sign);

    @POST("/api/v1/dis/query_issue_list_for_master")
    Observable<BasicListResponseEntity<OrderResultEntity>> getOrderMaster(@Body OrderParams orderParams);

    @POST("/api/v1/dis/query_issue_list")
    Observable<BasicListResponseEntity<OrderResultEntity>> getOrderUser(@Body OrderParams orderParams);

    @POST("/api/v1/dis/query_issue_detail")
    Observable<BasicResponseEntity<QDetailEntity>> getAnswerDetailMast(@Body QuestionDetailParams questionDetailParams);

    @POST("/api/v1/dis/query_issue_detail")
    Observable<BasicResponseEntity<QDetailEntity>> getAnswerDetailUser(@Body QuestionDetailParams questionDetailParams);

    @POST("/api/v1/dis/finished_issue")
    @FormUrlEncoded
    Observable<BasicResponseEntity<String>> doneAnswer(@Field("issue_id") int pay_type,
                                                       @Field("content") String content,
                                                       @Field("ak") String ak,
                                                       @Field("timestamp") long timestamp,
                                                       @Field("sign") String sign);

    @POST("/api/v1/dis/close_issue")
    @FormUrlEncoded
    Observable<BasicResponseEntity<String>> orderRefused(@Field("issue_id") int pay_type,
                                                         @Field("reason") String order_no,
                                                         @Field("ak") String ak,
                                                         @Field("timestamp") long timestamp,
                                                         @Field("sign") String sign);

    @POST("/api/v1/dis/set_score")
    Observable<BasicResponseEntity<String>> orderComment(@Body CommentParams commentParams);

    @POST("/api/v1/dis/cancel_issue")
    @FormUrlEncoded
    Observable<BasicResponseEntity<String>> orderCancel(@Field("issue_id") int pay_type,
                                                        @Field("ak") String ak,
                                                        @Field("timestamp") long timestamp,
                                                        @Field("sign") String sign);

    @POST("/api/v1/sys/query_system_msg_list")
    Observable<BasicListResponseEntity<MessageEntity.ItemsBean>> getMessage(@Body PageParams page2Params);

    @GET("/api/v1/user/assets")
    Observable<BasicResponseEntity<UserAssetEntity>> getUserAssets();

    @GET("/api/v1/im/get_push_switch")
    Observable<BasicResponseEntity<SwitchStatusEntity>> getSwitchStatus();

    @POST("/api/v1/im/update_push_switch")
    Observable<BasicResponseEntity<String>> updateSwitchFortune(@Body SwitchFortuneParams switchFortuneParams);


    @POST("/api/v1/im/update_push_switch")
    Observable<BasicResponseEntity<String>> updateSwitchMsg(@Body SwitchMsgParams switchMsgParams);

    /**
     * 获取费率
     * @param
     * @return
     */
    @GET("/api/v1/wallet/get_transfer_fee")
    Observable<BasicResponseEntity<Float>> getTranFerFee();

    @POST("/api/v1/sys/feedback")
    Observable<BasicResponseEntity<String>> sendFeedBack(@Body FeedBackParams feedBackParams);

    @GET("/api/v1/config/get_pangle_ad_config")
    Observable<BasicResponseEntity<AdEntity>> getAdConfig();

    @POST("/api/v1/fortune/get_addfortune_data")
    Observable<BasicResponseEntity<AddfortuneDataEntity>> getAddfortuneData(@Body TimestampParams timestampParams);
}
