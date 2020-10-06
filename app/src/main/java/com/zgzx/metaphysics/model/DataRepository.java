package com.zgzx.metaphysics.model;

import android.annotation.SuppressLint;

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
import io.reactivex.schedulers.Schedulers;


/**
 * 数据提供
 */
@SuppressLint("DefaultLocale")
public class DataRepository implements IDataSource {

    private static IDataSource sDataRepository;

    public static IDataSource getInstance() {
        if (sDataRepository == null) {
            synchronized (DataRepository.class) {

                if (sDataRepository == null) {
                    sDataRepository = new DataRepository();
                }
            }
        }

        return sDataRepository;
    }

    // 本地缓存数据
    private LocalDataManager mLocalDataManager;

    private final IDataSource mRemoteDataSource, mMockRemoteDataSource;


    private DataRepository() {
        mRemoteDataSource = new RemoteDataSource();
        mMockRemoteDataSource = new MockRemoteDataSource();

        mLocalDataManager = new LocalDataManager();

        // 初始化请求
        phoneCode().subscribeOn(Schedulers.io()).subscribe(); // 区号
        areaChina().subscribeOn(Schedulers.io()).subscribe(); // 国内数据
        areaOversea().subscribeOn(Schedulers.io()).subscribe(); // 海外数据
    }

    // 格式化手机号
    private String formatPhone(String phone) {
        return phone.replaceAll(" +", "");
    }

    @Override
    public Observable<BasicResponseEntity<List<String>>> upload(List<File> files) {
        return mRemoteDataSource.upload(files);
    }

    @Override
    public Observable<BasicResponseEntity<List<AreaCodeEntity>>> phoneCode() {
        return mLocalDataManager.getPhoneCode(mRemoteDataSource.phoneCode());
    }

    @Override
    public Observable<BasicResponseEntity<List<DomesticJsonBean>>> areaChina() {
        return mLocalDataManager.areaChina(mRemoteDataSource.areaChina());
    }

    @Override
    public Observable<BasicResponseEntity<List<ForeignJsonBean>>> areaOversea() {
        return mLocalDataManager.areaOversea(mRemoteDataSource.areaOversea());
    }

    @Override
    public Observable<BasicResponseEntity<VersionUpdateEntity>> versionUpdate() {
        return mRemoteDataSource.versionUpdate();
    }

    @Override
    public Observable<BasicResponseEntity<Object>> send(String countryCode, String phone,
                                                        int scene) {
        return mRemoteDataSource.send(countryCode, formatPhone(phone), scene);
    }

    @Override
    public Observable<BasicResponseEntity<RegisterEntity>> register(
            String countryCode,
            String phone,
            String verifyCode,
            String password,
            String inviteCode) {

        return mRemoteDataSource.register(countryCode, formatPhone(phone), verifyCode, password,
                inviteCode);
    }

    @Override
    public Observable<BasicResponseEntity<UserDetailEntity>> completeInfo(
            String birth_time,
            String avatar,
            String nickname,
            int sex,
            long birthDay,
            int birthHour,
            int calendarType,
            String birthArea) {

        // 删除用户缓存
        mLocalDataManager.clearUserDetail();

        // 删除命书列表缓存
        // mLocalDataManager.clearFateBooksListCache();

        // 后台小时为1-24， 所以要+1
        return mRemoteDataSource.completeInfo(birth_time, avatar, nickname, sex, birthDay,
                birthHour + 1, calendarType, birthArea);
    }

    @Override
    public Observable<BasicResponseEntity<UserDetailEntity>> login(
            String phone,
            String countryCode,
            String password) {

        return mRemoteDataSource.login(formatPhone(phone), countryCode, password);
    }

    @Override
    public Observable<BasicResponseEntity<Object>> verify(
            String phone,
            String countryCode,
            String verifyCode) {

        return mRemoteDataSource.verify(formatPhone(phone), countryCode, verifyCode);
    }

    @Override
    public Observable<BasicResponseEntity<Object>> forgetLoginPassword(
            String password, // 登录密码，明文，格式：8-20位数字+字母/符号组合
            String phone, // 手机号
            String countryCode, // 区号，不填默认为国内
            String verifyCode // 手机验证码，短信服务关闭时默认”12345”
    ) {

        return mRemoteDataSource.forgetLoginPassword(password, formatPhone(phone), countryCode,
                verifyCode);
    }

    @Override
    public Observable<BasicResponseEntity<Object>> updateLoginPassword(String password,
                                                                       String phone,
                                                                       String countryCode,
                                                                       String verifyCode) {
        return mRemoteDataSource.updateLoginPassword(password, formatPhone(phone), countryCode,
                verifyCode);
    }

    @Override
    public Observable<BasicResponseEntity<Object>> updatePayPassword(String password,
                                                                     String phone,
                                                                     String countryCode,
                                                                     String verifyCode) {
        mLocalDataManager.clearUserDetail();
        return mRemoteDataSource.updatePayPassword(password, formatPhone(phone), countryCode,
                verifyCode);
    }

    @Override
    public Observable<BasicResponseEntity<UserDetailEntity>> userDetail() {
        return mLocalDataManager.userDetail(mRemoteDataSource.userDetail());
    }

    @Override
    public Observable<BasicResponseEntity<Object>> updateAvatar(String avatarPath) {
        mLocalDataManager.clearUserDetail();
        return mRemoteDataSource.updateAvatar(avatarPath);
    }

    @Override
    public Observable<BasicResponseEntity<Object>> updateUserDetail(String birth_time,
                                                                    String nickname, String name,
                                                                    int sex, long birthDay,
                                                                    int birthHour,
                                                                    int calendarType,
                                                                    String birthArea,
                                                                    String liveArea) {
        mLocalDataManager.clearUserDetail();
        // 后台小时为1-24
        return mRemoteDataSource.updateUserDetail(birth_time, nickname, name, sex, birthDay,
                birthHour + 1, calendarType, birthArea, liveArea);
    }


    @Override
    public Observable<BasicResponseEntity<Object>> applyMaster(
            String content,
            String phone,
            List<String> paths) {

        return mRemoteDataSource.applyMaster(content, formatPhone(phone), paths);
    }

    @Override
    public Observable<BasicResponseEntity<Integer>> applyMasterStatus() {
        return mRemoteDataSource.applyMasterStatus();
    }


    @Override
    public Observable<BasicResponseEntity<List<MasterServiceTypeEntity>>> serviceType() {
        return mRemoteDataSource.serviceType();
    }

//    @Override
//    public Observable<BasicListResponseEntity<MasterServiceEntity>> serviceList(
//            int page,
//            int typeId, // 项目类型
//            int sortType, // 排序类型，1评分; 2解答次数; 3收费金额
//            int priceSort // 价格排序
//    ) {
//
//        return mRemoteDataSource.serviceList(page, typeId, sortType, priceSort);
//    }

    @Override
    public Observable<BasicListResponseEntity<MasterServiceEntity>> serviceList(int page) {
        return mRemoteDataSource.serviceList(page);

    }

//    @Override
//    public Observable<BasicResponseEntity<MasterDetailEntityNew>> masterDetail() {
//        return mRemoteDataSource.masterDetail();
//    }

    @Override
    public Observable<BasicResponseEntity<Object>> updateMasterIntro(String intro) {

        return mRemoteDataSource.updateMasterIntro(intro);
    }

    @Override
    public Observable<BasicResponseEntity<Object>> updateMasterFields(List<Integer> fields) {
        return mRemoteDataSource.updateMasterFields(fields);
    }

    @Override
    public Observable<BasicResponseEntity<MasterDetailEntityNew>> masterDetail(int masterid,int uid, int userid) {
        return mRemoteDataSource.masterDetail(masterid,uid, userid);
    }

    @Override
    public Observable<BasicResponseEntity<MasterCommentEntity>> getMasterComment(int page,
                                                                                 int pagesize,
                                                                                 int uid,int masterid) {
        return mRemoteDataSource.getMasterComment(page, pagesize, uid,masterid);
    }

    @Override
    public Observable<BasicResponseEntity<MasterServiceSettingEntity>> masterServices() {
        return mRemoteDataSource.masterServices();
    }

    @Override
    public Observable<BasicResponseEntity<FortuneEntity>> personalFortune(int timestamp, int type) {
//        return mRemoteDataSource.personalFortune();
        return mLocalDataManager.personalFortune(timestamp, type,
                mRemoteDataSource.personalFortune(timestamp, type));
    }

    @Override
    public Observable<BasicResponseEntity<List<FateBooksEntity>>> fateBooksList() {
        return mRemoteDataSource.fateBooksList();
        // return mLocalDataManager.fateBooksList(mRemoteDataSource.fateBooksList());
    }

    @Override
    public Observable<BasicResponseEntity<List<FateBookTypeEntity>>> fateBookTypes(int fateBookId) {
        return mRemoteDataSource.fateBookTypes(fateBookId);
//        return mLocalDataManager.fateBookTypes(fateBookId, mRemoteDataSource.fateBookTypes
//        (fateBookId));
    }

    @Override
    public Observable<BasicResponseEntity<FateBookDetailEntity>> fateBookDetail(int fateBookId,
                                                                                int cateId) {
//        return mRemoteDataSource.fateBookDetail(fateBookId, cateId);

        return mLocalDataManager.fateBookDetail(fateBookId, cateId,
                mRemoteDataSource.fateBookDetail(fateBookId, cateId));
    }

    @Override
    public Observable<BasicResponseEntity<FateBookEntity>> createFateBook(String name,
                                                                          int sex,
                                                                          long birthDay,
                                                                          int birthHour) {
        //  mLocalDataManager.clearFateBooksListCache();

        // birthHour: 后台小时为1-24小时
        return mRemoteDataSource
                .createFateBook(name, sex, birthDay, birthHour + 1);
    }

    @Override
    public Observable<BasicResponseEntity<Object>> deleteFateBook(int fateBookId) {
        // mLocalDataManager.clearFateBookTypeCache(fateBookId);
        //   mLocalDataManager.clearFateBooksListCache();
        return mRemoteDataSource.deleteFateBook(fateBookId);
    }

    @Override
    public Observable<BasicResponseEntity<Object>> buyFateBook(int fateBookId, String password,
                                                               int isAll, int type,
                                                               String cateId,String ak,
                                                               long timestamp,
                                                               String sign) {
        //   mLocalDataManager.clearFateBookTypeCache(fateBookId);
        return mRemoteDataSource.buyFateBook(fateBookId, password, isAll, type, cateId,ak,timestamp,sign);
    }

    @Override
    public Observable<BasicResponseEntity<OrderLifeBookEntity>> orderLifeBookEntity(int id, String cateId,String ak,
                                                                                    long timestamp,
                                                                                    String sign) {
        return mRemoteDataSource.orderLifeBookEntity(id, cateId,ak,timestamp,sign);
    }

    @Override
    public Observable<BasicResponseEntity<OrderMemberEntity>> orderMember(int id,String ak,
                                                                          long timestamp,
                                                                          String sign) {
        return mRemoteDataSource.orderMember(id,ak,timestamp,sign);
    }

    @Override
    public Observable<BasicResponseEntity<Float>> balance() {
        return mRemoteDataSource.balance();
    }

    @Override
    public Observable<BasicListResponseEntity<PropertyBillEntity>> propertyBill(int page,
                                                                                int type) {
        return mRemoteDataSource.propertyBill(page, type);
    }

    @Override
    public Observable<BasicListResponseEntity<SystemMessageEntity>> systemMessage(int page) {
        return mRemoteDataSource.systemMessage(page);
    }

    @Override
    public Observable<BasicResponseEntity<Object>> transfer(long uid, float amount,
                                                            String password, long timestamp, String ak, String sign) {
        return mRemoteDataSource.transfer(uid, amount, password, timestamp, ak, sign);
//        return mMockRemoteDataSource.transfer(uid, amount, password);
    }

//    @Override
//    public Observable<BasicResponseEntity<InviteListEntity>> inviteList(int page) {
//        return mRemoteDataSource.inviteList(page);
//    }

    @Override
    public Observable<BasicResponseEntity<CalendarDetailEntity>> calendarDetail(int timestamp) {
        return mLocalDataManager.calendarDetail(timestamp,
                mRemoteDataSource.calendarDetail(timestamp));
    }

    @Override
    public Observable<BasicResponseEntity<List<BannerEntity>>> bannerList() {
        return mRemoteDataSource.bannerList();
    }

    @Override
    public Observable<BasicListResponseEntity<SystemMessageEntity>> noticeList(int page) {
        return mRemoteDataSource.noticeList(page);
    }

    @Override
    public Observable<BasicResponseEntity<CatePriceEntity>> getCatePrice(int id) {
        return mRemoteDataSource.getCatePrice(id);
    }

    @Override
    public Observable<BasicResponseEntity<List<RechargeActivitiesEntity>>> rechargeActivitiesList() {
        return mRemoteDataSource.rechargeActivitiesList();
    }

    @Override
    public Observable<BasicResponseEntity<List<VipTypeEntity>>> vipList() {
        return mRemoteDataSource.vipList();
    }

    @Override
    public Observable<BasicResponseEntity<InvitationSummaryEntity>> invitationSummary() {
        return mRemoteDataSource.invitationSummary();
    }

    @Override
    public Observable<BasicListResponseEntity<RechargeRecordEntity>> rechargeRecord(int todaySort
            , int totalSort, int page) {
        return mRemoteDataSource.rechargeRecord(todaySort, totalSort, page);
    }

    @Override
    public Observable<BasicListResponseEntity<InviteListEntity>> inviteList(int todaySort,
                                                                            int totalSort,
                                                                            int page) {
        return mRemoteDataSource.inviteList(todaySort, totalSort, page);
    }

    @Override
    public Observable<BasicResponseEntity<HomeDataEntity>> homeData() {
        return mLocalDataManager.homeData(mRemoteDataSource.homeData());
    }

    @Override
    public Observable<BasicResponseEntity<PrePayResult>> buyFateBookPrepay(int pay_type,
                                                                           String lifebook_id,
                                                                           int cate_ids, String ak,
                                                                           long timestamp,
                                                                           String sign) {
        return mRemoteDataSource.buyFateBookPrepay(pay_type, lifebook_id, cate_ids, ak, timestamp, sign);
    }

    @Override
    public Observable<BasicResponseEntity<PrePayResult>> buyMemberPrepay(int pay_type, String order_no, int member_id, String ak,
                                                                         long timestamp,
                                                                         String sign) {

        return mRemoteDataSource.buyMemberPrepay(pay_type, order_no, member_id, ak, timestamp, sign);
    }

    @Override
    public Observable<BasicResponseEntity<PrePayResult>> buyQuestion(int pay_type, int issue_id ,String ak,
                                                                     long timestamp,
                                                                     String sign) {
        return mRemoteDataSource.buyQuestion(pay_type, issue_id,ak,timestamp,sign);
    }

    @Override
    public Observable<BasicResponseEntity<HomeFouncationEntity>> entry() {
        return mRemoteDataSource.entry();
    }

    @Override
    public Observable<BasicResponseEntity<List<CalendarHourEntity>>> calendarHour() {
        return mRemoteDataSource.calendarHour();
    }

    @Override
    public Observable<BasicResponseEntity<CalendarDayEntity>> calendarDay() {
        return mRemoteDataSource.calendarDay();
    }


    public Observable<BasicResponseEntity<ArticleListEntity>> articleCateList(int cate_id, int page, int page_size) {
        return mRemoteDataSource.articleCateList(cate_id, page, page_size);
    }

    @Override
    public Observable<BasicResponseEntity<List<ArticleEntity>>> articleCate() {
        return mRemoteDataSource.articleCate();
    }


    @Override
    public Observable<BasicResponseEntity<List<BannerEntity>>> banner(int type) {
        return mRemoteDataSource.banner(type);
    }

    @Override
    public Observable<BasicResponseEntity<List<DailyQuestionEntity>>> getDailyQuestion(int index) {
        return mRemoteDataSource.getDailyQuestion(index);
    }

    @Override
    public Observable<BasicResponseEntity<TrendEntity>> getTrendData() {
        return mRemoteDataSource.getTrendData();
    }

    @Override
    public Observable<BasicResponseEntity<MonthBlessEntity>> getMonthBless(String month) {
        return mRemoteDataSource.getMonthBless(month);
    }

    @Override
    public Observable<BasicResponseEntity<String>> addMonthBless(String content) {
        return mRemoteDataSource.addMonthBless(content);
    }

    @Override
    public Observable<BasicResponseEntity<OrderResultEntity>> askQuestion(String nickname, int gender,
                                                                          int master_id, int birth_day,
                                                                          int birth_hour, int calendar_type,
                                                                          String birth_area, String content,
                                                                          String paths,String ak,
                                                                          long timestamp,
                                                                          String sign) {
        return mRemoteDataSource.askQuestion(nickname, gender, master_id, birth_day, birth_hour, calendar_type, birth_area, content, paths,ak,timestamp,sign);
    }

    @Override
    public Observable<BasicListResponseEntity<OrderResultEntity>> getOrderMaster(int status, int page,
                                                                                 int page_size) {
        return mRemoteDataSource.getOrderMaster(status, page, page_size);
    }

    @Override
    public Observable<BasicListResponseEntity<OrderResultEntity>> getOrderUser(int status, int page, int page_size) {
        return mRemoteDataSource.getOrderUser(status, page, page_size);
    }

    @Override
    public Observable<BasicResponseEntity<QDetailEntity>> getAnswerDetailMast(int issue_id) {
        return mRemoteDataSource.getAnswerDetailMast(issue_id);
    }

    @Override
    public Observable<BasicResponseEntity<QDetailEntity>> getAnswerDetailUser(int issue_id) {
        return mRemoteDataSource.getAnswerDetailUser(issue_id);
    }

    @Override
    public Observable<BasicResponseEntity<String>> doneAnswer(int issue_id, String content,String ak,
                                                              long timestamp,
                                                              String sign) {
        return mRemoteDataSource.doneAnswer(issue_id, content,ak,timestamp,sign);
    }

    @Override
    public Observable<BasicResponseEntity<String>> orderRefused(int issue_id, String reason,String ak,
                                                                long timestamp,
                                                                String sign) {
        return mRemoteDataSource.orderRefused(issue_id, reason,ak,timestamp,sign);
    }

    @Override
    public Observable<BasicResponseEntity<String>> orderComment(int issue_id, int score) {
        return mRemoteDataSource.orderComment(issue_id, score);
    }

    @Override
    public Observable<BasicResponseEntity<String>> orderCancel(int issue_id,String ak,
                                                               long timestamp,
                                                               String sign) {
        return mRemoteDataSource.orderCancel(issue_id,ak,timestamp,sign);
    }

    @Override
    public Observable<BasicListResponseEntity<MessageEntity.ItemsBean>> getMessage(int page) {
        return mRemoteDataSource.getMessage(page);
    }

    @Override
    public Observable<BasicResponseEntity<UserAssetEntity>> getUserAssets() {
        return mRemoteDataSource.getUserAssets();
    }

    @Override
    public Observable<BasicResponseEntity<H5BaseEntity>> getH5URL() {
        return mRemoteDataSource.getH5URL();
    }

    @Override
    public Observable<BasicResponseEntity<UrlConfigEntity>> getConfigURL() {
        return mRemoteDataSource.getConfigURL();
    }

    @Override
    public Observable<BasicResponseEntity<SwitchStatusEntity>> getSwitchStatus() {
        return mRemoteDataSource.getSwitchStatus();
    }

    @Override
    public Observable<BasicResponseEntity<String>> updateSwitchFortune(int fortune_switch) {
        return mRemoteDataSource.updateSwitchFortune(fortune_switch);
    }

    @Override
    public Observable<BasicResponseEntity<String>> updateSwitchMsg(int fortune_switch) {
        return mRemoteDataSource.updateSwitchMsg(fortune_switch);
    }

    @Override
    public Observable<BasicResponseEntity<String>> sendFeedBack(String content, String images) {
        return mRemoteDataSource.sendFeedBack(content,images);
    }

    @Override
    public Observable<BasicResponseEntity<AdEntity>> getAdConfig() {
        return mRemoteDataSource.getAdConfig();
    }

    @Override
    public Observable<BasicResponseEntity<AddfortuneDataEntity>> getAddfortuneData(int timestamp) {
        return mRemoteDataSource.getAddfortuneData(timestamp);
    }

    @Override
    public Observable<BasicResponseEntity<Float>> getTranFee() {
        return mRemoteDataSource.getTranFee();
    }

}
