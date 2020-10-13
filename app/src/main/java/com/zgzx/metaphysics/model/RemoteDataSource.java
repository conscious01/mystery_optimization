package com.zgzx.metaphysics.model;


import com.zgzx.metaphysics.BuildConfig;
import com.zgzx.metaphysics.city_time_picker.xpopupext.bean.DomesticJsonBean;
import com.zgzx.metaphysics.city_time_picker.xpopupext.bean.ForeignJsonBean;
import com.zgzx.metaphysics.model.entity.AdCountEntity;
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
import com.zgzx.metaphysics.network.WebServiceApi;
import com.zgzx.metaphysics.network.converter.EncryptGsonConverterFactory;
import com.zgzx.metaphysics.network.interceptor.CommonParameterInterceptor;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * 远程/网络 数据源
 */
public class RemoteDataSource implements IDataSource {

    private WebServiceApi mWebServiceApi;

    RemoteDataSource() {
        mWebServiceApi = initialize().create(WebServiceApi.class);
    }

    private Retrofit initialize() {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                //.addInterceptor(new EncryptParameterInterceptor())
                .addInterceptor(new CommonParameterInterceptor())
                .addNetworkInterceptor(logInterceptor)
                .build();

        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BuildConfig.DOMAIN)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(EncryptGsonConverterFactory.create())
                .build();
    }


    @Override
    public Observable<BasicResponseEntity<List<String>>> upload(List<File> files) {
        Map<String, RequestBody> fileMap = new HashMap<>();

        for (File file : files) {
            fileMap.put("upload[]" + "\";filename=\"" + file.getName(),
                    RequestBody.create(file, MediaType.parse("image/png")));
        }

        return mWebServiceApi.upload(fileMap);
    }

    @Override
    public Observable<BasicResponseEntity<List<AreaCodeEntity>>> phoneCode() {
        return mWebServiceApi.phoneCode();
    }

    @Override
    public Observable<BasicResponseEntity<List<DomesticJsonBean>>> areaChina() {
        return mWebServiceApi.areaChina();
    }

    @Override
    public Observable<BasicResponseEntity<List<ForeignJsonBean>>> areaOversea() {
        return mWebServiceApi.areaOversea();
    }

    @Override
    public Observable<BasicResponseEntity<VersionUpdateEntity>> versionUpdate() {
        return mWebServiceApi.versionUpdate();
    }

    @Override
    public Observable<BasicResponseEntity<Object>> send(String countryCode, String phone,
                                                        int scene) {
        return mWebServiceApi.send(new PhoneParams(phone, countryCode, scene));
    }

    @Override
    public Observable<BasicResponseEntity<RegisterEntity>> register(
            String countryCode,
            String phone,
            String verifyCode,
            String password,
            String inviteCode) {

        return mWebServiceApi.register(new RegisterParams(verifyCode, phone, password, inviteCode
                , countryCode));
    }

    @Override
    public Observable<BasicResponseEntity<UserDetailEntity>> completeInfo(
            String birth_time,
            String avatar,
            String truename,
            int sex,
            long birthDay,
            int birthHour,
            int calendarType,
            String birthArea) {

        return mWebServiceApi.completeInfo(new UserInfoParams(birth_time, avatar, truename, sex,
                (int) birthDay, birthHour, calendarType, birthArea));
    }

    @Override
    public Observable<BasicResponseEntity<UserDetailEntity>> login(
            String phone,
            String countryCode,
            String password) {

        return mWebServiceApi.login(new LoginParams(countryCode, phone, password));
    }

    @Override
    public Observable<BasicResponseEntity<Object>> verify(
            String phone,
            String countryCode,
            String verifyCode) {

        return mWebServiceApi.verify(new VerifyParams(phone, countryCode, verifyCode));
    }

    @Override
    public Observable<BasicResponseEntity<Object>> forgetLoginPassword(
            String password, // 登录密码，明文，格式：8-20位数字+字母/符号组合
            String phone, // 手机号
            String countryCode, // 区号，不填默认为国内
            String verifyCode // 手机验证码，短信服务关闭时默认”12345”
    ) {
        return mWebServiceApi.forgetLoginPassword(new PasswordParams(password, countryCode, phone
                , verifyCode));
    }

    @Override
    public Observable<BasicResponseEntity<Object>> updateLoginPassword(String password,
                                                                       String phone,
                                                                       String countryCode,
                                                                       String verifyCode) {
        return mWebServiceApi.updateLoginPassword(new PasswordParams(password, countryCode, phone
                , verifyCode));
    }

    @Override
    public Observable<BasicResponseEntity<Object>> updatePayPassword(String password,
                                                                     String phone,
                                                                     String countryCode,
                                                                     String verifyCode) {
        return mWebServiceApi.updatePayPassword(new PasswordParams(password, countryCode, phone,
                verifyCode));
    }

    @Override
    public Observable<BasicResponseEntity<UserDetailEntity>> userDetail() {
        return mWebServiceApi.userDetail();
    }

    @Override
    public Observable<BasicResponseEntity<Object>> updateAvatar(String avatarPath) {
        return mWebServiceApi.updateAvatar(new UserInfoParams(avatarPath));
    }

    @Override
    public Observable<BasicResponseEntity<Object>> updateUserDetail(
            String birth_time,
            String nickname,
            String name,
            int sex,
            long birthDay,
            int birthHour,
            int calendarType,
            String birthArea,
            String liveArea) {

        return mWebServiceApi.updateUserDetail(new UserInfoParams(
                        birth_time,
                        nickname,
                        (int) birthDay,
                        birthHour,
                        calendarType,
                        birthArea,
                        liveArea
                )
        );
    }

    @Override
    public Observable<BasicResponseEntity<Object>> applyMaster(
            String intro,
            String phone,
            List<String> paths) {

        return mWebServiceApi.applyMaster(new ApplyMasterParams(phone, intro, paths));
    }

    @Override
    public Observable<BasicResponseEntity<Integer>> applyMasterStatus() {
        return mWebServiceApi.applyStatus();
    }


    @Override
    public Observable<BasicResponseEntity<List<MasterServiceTypeEntity>>> serviceType() {
        return mWebServiceApi.masterServiceType();
    }

//    @Override
//    public Observable<BasicListResponseEntity<MasterServiceEntity>> serviceList(
//            int page,
//            int typeId, // 项目类型:1问事,2择日,3取名
//            int sortType, // 排序类型，1评分; 2解答次数; 3收费金额
//            int priceSort // 价格排序
//    ) {
//        return mWebServiceApi.masterServiceList(new MasterListParams(new PageParams(page),
//        typeId, sortType, priceSort));
//    }

    @Override
    public Observable<BasicListResponseEntity<MasterServiceEntity>> serviceList(int page
    ) {
        return mWebServiceApi.masterServiceList(new MasterListParams(page));
    }

//    @Override
//    public Observable<BasicResponseEntity<MasterDetailEntity>> masterDetail(int masterid,int userid) {
//        return mWebServiceApi.masterDetail(new MasterDetailParams(masterid,userid));
//    }

    @Override
    public Observable<BasicResponseEntity<Object>> updateMasterIntro(String intro) {
        return mWebServiceApi.updateMasterIntro(new MasterIntroParams(intro));
    }

    @Override
    public Observable<BasicResponseEntity<Object>> updateMasterFields(List<Integer> fields) {
        return mWebServiceApi.updateMasterFields(new MasterFieldsParams(fields));
    }

    @Override
    public Observable<BasicResponseEntity<MasterDetailEntityNew>> masterDetail(int masterid,int uid, int user_id) {
        return mWebServiceApi.getMasterDetail(new MasterDetailParams(masterid,uid, user_id));
    }

    @Override
    public Observable<BasicResponseEntity<MasterCommentEntity>> getMasterComment(int page,
                                                                                 int pagesize,
                                                                                 int uid,int master_id) {
        return mWebServiceApi.getMasterComment(new MasterCommentParams(master_id,uid, page, pagesize));
    }

    @Override
    public Observable<BasicResponseEntity<MasterServiceSettingEntity>> masterServices() {
        return mWebServiceApi.masterServices();
    }

    @Override
    public Observable<BasicResponseEntity<FortuneEntity>> personalFortune(int timestamp, int type) {
        return mWebServiceApi.personalFortune(new PersonalFortuneParams(timestamp, type));
    }

    @Override
    public Observable<BasicResponseEntity<List<FateBooksEntity>>> fateBooksList() {
        return mWebServiceApi.fateBooksList();
    }

    @Override
    public Observable<BasicResponseEntity<List<FateBookTypeEntity>>> fateBookTypes(int fateBookId) {
        return mWebServiceApi.fateBookTypes(new FateBookParams(fateBookId));
    }

    @Override
    public Observable<BasicResponseEntity<FateBookDetailEntity>> fateBookDetail(int fateBookId,
                                                                                int cateId) {
        return mWebServiceApi.fateBookDetail(new FateBookParams(cateId, fateBookId));
    }

    @Override
    public Observable<BasicResponseEntity<FateBookEntity>> createFateBook(String name,
                                                                          int sex,
                                                                          long birthDay,
                                                                          int birthHour) {
        return mWebServiceApi.createFateBook(new CreateFateBookParams(name, (int) birthDay,
                birthHour, sex));
    }

    @Override
    public Observable<BasicResponseEntity<Object>> deleteFateBook(int id) {
        return mWebServiceApi.deleteFateBook(new FateBookParams(id));
    }

    @Override
    public Observable<BasicResponseEntity<Object>> buyFateBook(int id, String password, int isAll
            , int type, String cateIds, String ak,long timestamp,String sign) {
        return mWebServiceApi.buyFateBook(id, password,cateIds, isAll, type,ak,timestamp,sign);
    }

    @Override
    public Observable<BasicResponseEntity<OrderLifeBookEntity>> orderLifeBookEntity(int id, String cateId, String ak,
                                                                                    long timestamp,
                                                                                    String sign) {
        return mWebServiceApi.orderLifeBook(id, cateId, ak, timestamp, sign);
    }

    @Override
    public Observable<BasicResponseEntity<OrderMemberEntity>> orderMember(int id, String ak,
                                                                          long timestamp,
                                                                          String sign) {
        return mWebServiceApi.orderMember(id, ak, timestamp, sign);
    }

    @Override
    public Observable<BasicResponseEntity<Float>> balance() {
        return mWebServiceApi.balance();
    }

    @Override
    public Observable<BasicListResponseEntity<PropertyBillEntity>> propertyBill(int page,
                                                                                int type) {
        return mWebServiceApi.propertyBill(new PropertyBillParams(page, type));
    }

    @Override
    public Observable<BasicListResponseEntity<SystemMessageEntity>> systemMessage(int page) {
        return mWebServiceApi.systemMessage(new Page2Params(page));
    }

    @Override
    public Observable<BasicResponseEntity<Object>> transfer(long uid, float amount,
                                                            String password, long timestamp, String ak, String sign) {
        return mWebServiceApi.transfer(uid, amount, password, ak, timestamp, sign);
    }

//    @Override
//    public Observable<BasicResponseEntity<InviteListEntity>> inviteList(int page) {
//        return mWebServiceApi.inviteList(new PageParams(page));
//    }

    @Override
    public Observable<BasicResponseEntity<CalendarDetailEntity>> calendarDetail(int timestamp) {
        return mWebServiceApi.calendarDetail(new TimestampParams(timestamp));
    }

    @Override
    public Observable<BasicResponseEntity<List<BannerEntity>>> bannerList() {
        return mWebServiceApi.bannerList();
    }

    @Override
    public Observable<BasicListResponseEntity<SystemMessageEntity>> noticeList(int page) {
        return mWebServiceApi.noticeList(new Page2Params(page));
    }

    @Override
    public Observable<BasicResponseEntity<CatePriceEntity>> getCatePrice(int id) {
        return mWebServiceApi.getCatePrice(new FateBookParams(id));
    }

    @Override
    public Observable<BasicResponseEntity<List<RechargeActivitiesEntity>>> rechargeActivitiesList() {
        return mWebServiceApi.rechargeActivitiesList();
    }

    @Override
    public Observable<BasicResponseEntity<List<VipTypeEntity>>> vipList() {
        return mWebServiceApi.vipList();
    }

    @Override
    public Observable<BasicResponseEntity<InvitationSummaryEntity>> invitationSummary() {
        return mWebServiceApi.invitationSummary();
    }

    @Override
    public Observable<BasicListResponseEntity<RechargeRecordEntity>> rechargeRecord(int todaySort
            , int totalSort, int page) {
        return mWebServiceApi.rechargeRecord(new InvitationParams(todaySort, totalSort, page));
    }

    @Override
    public Observable<BasicListResponseEntity<InviteListEntity>> inviteList(int todaySort,
                                                                            int totalSort,
                                                                            int page) {
        return mWebServiceApi.inviteList(new InvitationParams(todaySort, totalSort, page));
    }

    @Override
    public Observable<BasicResponseEntity<HomeDataEntity>> homeData() {
        return mWebServiceApi.homeData();
    }

    @Override
    public Observable<BasicResponseEntity<PrePayResult>> buyFateBookPrepay(int pay_type,
                                                                           String order_no,
                                                                           int discount_id,
                                                                           String ak,
                                                                           long timestamp,
                                                                           String sign
    ) {
        return mWebServiceApi.buyFateBookPrepay(pay_type, order_no, discount_id, ak, timestamp, sign);
    }

    @Override
    public Observable<BasicResponseEntity<PrePayResult>> buyMemberPrepay(int pay_type, String order_no,
                                                                         int member_id, String ak,
                                                                         long timestamp,
                                                                         String sign) {
        return mWebServiceApi.buyMemberPrepay(pay_type, order_no, member_id, ak, timestamp, sign);
    }

    @Override
    public Observable<BasicResponseEntity<PrePayResult>> buyQuestion(int pay_type, int issue_id,int discount_id, String ak,
                                                                     long timestamp,
                                                                     String sign) {
        return mWebServiceApi.buyQuestion(pay_type, issue_id,discount_id, ak, timestamp, sign);
    }

    @Override
    public Observable<BasicResponseEntity<HomeFouncationEntity>> entry() {
        return mWebServiceApi.entry();
    }

    @Override
    public Observable<BasicResponseEntity<List<CalendarHourEntity>>> calendarHour() {
        return mWebServiceApi.calendarHour();
    }

    @Override
    public Observable<BasicResponseEntity<CalendarDayEntity>> calendarDay() {
        return mWebServiceApi.calendarDay();
    }

    @Override
    public Observable<BasicResponseEntity<ArticleListEntity>> articleCateList(int cate_id, int page, int page_size) {
        return mWebServiceApi.articleCateList(new ArticleParams(cate_id, page, page_size));
    }

    @Override
    public Observable<BasicResponseEntity<List<ArticleEntity>>> articleCate() {
        return mWebServiceApi.articleCate();
    }

    @Override
    public Observable<BasicResponseEntity<List<BannerEntity>>> banner(int type) {
        return mWebServiceApi.banner(new BannerParams(type));
    }

    @Override
    public Observable<BasicResponseEntity<List<DailyQuestionEntity>>> getDailyQuestion(int index) {
        return mWebServiceApi.getDailyQuestion(new DailyQuestionParams(index));
    }

    @Override
    public Observable<BasicResponseEntity<TrendEntity>> getTrendData() {
        return mWebServiceApi.getTrendData();
    }

    @Override
    public Observable<BasicResponseEntity<MonthBlessEntity>> getMonthBless(String month) {
        return mWebServiceApi.getMonthBless(new BlessMothParams(month));
    }

    @Override
    public Observable<BasicResponseEntity<String>> addMonthBless(String content) {
        return mWebServiceApi.addMonthBless(new AddBlessMonthParams(content));
    }

    @Override
    public Observable<BasicResponseEntity<OrderLifeBookEntity>> askQuestion(String nickname,
                                                                             int gender,
                                                                             int master_id,
                                                                             int birth_day,
                                                                             int birth_hour,
                                                                             int calendar_type,
                                                                             String birth_area,
                                                                             String content,
                                                                             String paths, String ak,
                                                                             long timestamp,
                                                                             String sign) {
        return mWebServiceApi.askQuestion(nickname, gender, master_id,
                birth_day, birth_hour, calendar_type, birth_area, content, paths,ak,timestamp,sign);
    }

    @Override
    public Observable<BasicListResponseEntity<OrderResultEntity>> getOrderMaster(int status, int page,
                                                                                 int page_size) {
        return mWebServiceApi.getOrderMaster(new OrderParams(page, page_size, status));
    }

    @Override
    public Observable<BasicListResponseEntity<OrderResultEntity>> getOrderUser(int status, int page, int page_size) {
        return mWebServiceApi.getOrderUser(new OrderParams(page, page_size, status));
    }

    @Override
    public Observable<BasicResponseEntity<QDetailEntity>> getAnswerDetailMast(int issue_id) {
        return mWebServiceApi.getAnswerDetailMast(new QuestionDetailParams(issue_id));
    }

    @Override
    public Observable<BasicResponseEntity<QDetailEntity>> getAnswerDetailUser(int issue_id) {
        return mWebServiceApi.getAnswerDetailUser(new QuestionDetailParams(issue_id));

    }

    @Override
    public Observable<BasicResponseEntity<OrderLifeBookEntity>> getNotPaidQuestionDetail(int issue_id) {
        return mWebServiceApi.getNotPaidQuestionDetail(new QuestionDetailParams(issue_id));
    }

    @Override
    public Observable<BasicResponseEntity<String>> doneAnswer(int issue_id, String content,String ak,
                                                              long timestamp,
                                                              String sign) {
        return mWebServiceApi.doneAnswer(issue_id, content,ak,timestamp,sign);
    }

    @Override
    public Observable<BasicResponseEntity<String>> orderRefused(int issue_id, String reason,String ak,
                                                                long timestamp,
                                                                String sign) {
        return mWebServiceApi.orderRefused(issue_id, reason,ak,timestamp,sign);
    }

    @Override
    public Observable<BasicResponseEntity<String>> orderComment(int issue_id, int score) {
        return mWebServiceApi.orderComment(new CommentParams(issue_id, score));
    }

    @Override
    public Observable<BasicResponseEntity<String>> orderCancel(int issue_id,String ak,
                                                               long timestamp,
                                                               String sign) {
        return mWebServiceApi.orderCancel(issue_id,ak,timestamp,sign);

    }

    @Override
    public Observable<BasicListResponseEntity<MessageEntity.ItemsBean>> getMessage(int page) {
        return mWebServiceApi.getMessage(new PageParams(page));
    }

    @Override
    public Observable<BasicResponseEntity<UserAssetEntity>> getUserAssets() {
        return mWebServiceApi.getUserAssets();
    }

    @Override
    public Observable<BasicResponseEntity<H5BaseEntity>> getH5URL() {
        return mWebServiceApi.getH5BaseUrl();
    }

    @Override
    public Observable<BasicResponseEntity<UrlConfigEntity>> getConfigURL() {
        return mWebServiceApi.getConfigurl();
    }

    @Override
    public Observable<BasicResponseEntity<SwitchStatusEntity>> getSwitchStatus() {
        return mWebServiceApi.getSwitchStatus();
    }

    @Override
    public Observable<BasicResponseEntity<String>> updateSwitchFortune(int fortune_switch) {
        return mWebServiceApi.updateSwitchFortune(new SwitchFortuneParams(fortune_switch));
    }

    @Override
    public Observable<BasicResponseEntity<String>> updateSwitchMsg(int fortune_switch) {
        return mWebServiceApi.updateSwitchMsg(new SwitchMsgParams(fortune_switch));
    }

    @Override
    public Observable<BasicResponseEntity<String>> sendFeedBack(String content, String images) {
        return mWebServiceApi.sendFeedBack(new FeedBackParams(content,images));
    }

    @Override
    public Observable<BasicResponseEntity<AdEntity>> getAdConfig() {
        return mWebServiceApi.getAdConfig();
    }

    @Override
    public Observable<BasicResponseEntity<AddfortuneDataEntity>> getAddfortuneData(int timestamp) {
        return mWebServiceApi.getAddfortuneData(new TimestampParams(timestamp));
    }

    @Override
    public Observable<BasicResponseEntity<String>> getRongToken() {
        return mWebServiceApi.getRongToken();
    }

    @Override
    public Observable<BasicResponseEntity<AdCountEntity>> getAdCount() {
        return mWebServiceApi.getAdCount();
    }

    @Override
    public Observable<BasicResponseEntity<Float>> getTranFee() {
        return mWebServiceApi.getTranFerFee();
    }


}
