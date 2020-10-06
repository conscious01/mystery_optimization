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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;

/**
 * 假的数据源，方便测试不同数据的情况
 */
public class MockRemoteDataSource implements IDataSource {

    private UserDetailEntity mUserDetail = new UserDetailEntity(
//            "mock_token",
//            100101,
//            "mock_user",
//            1,
//            2, // 1-普通用户，2-师傅
//            "mock_user",
//            "mock_user",
//            "https://gank.io/images/882afc997ad84f8ab2a313f6ce0f3522",
//            "18617251684",
//            "广东省 深圳市 南山区",
//            "广东省 深圳市 南山区",
//            ""
    );


    @Override
    public Observable<BasicResponseEntity<List<String>>> upload(List<File> files) {
        return Observable.create(emitter -> {
            emitter.onNext(new BasicResponseEntity<>(Arrays.asList(
                    "https://gank.io/images/3e4423173d0a4c5e8447c0335b4458fc",
                    "https://gank.io/images/02eb8ca3297f4931ab64b7ebd7b5b89c"
            )));

            emitter.onComplete();
        });
    }

    @Override
    public Observable<BasicResponseEntity<List<AreaCodeEntity>>> phoneCode() {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<List<DomesticJsonBean>>> areaChina() {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<List<ForeignJsonBean>>> areaOversea() {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<VersionUpdateEntity>> versionUpdate() {
        return Observable.create(emitter -> {
            emitter.onNext(new BasicResponseEntity<>(new VersionUpdateEntity(
                    100,
                    "1.0.1",
                    1,
                    "https://kongming365.oss-cn-shenzhen.aliyuncs.com/app/release.1.0.0.apk",
                    "1. 更新了一些东西西些东西西些东西西些东西西些东西些东西<br>2. 其实没些东西些东西西些东西西些东西西些东西有更新什么",
                    "1. 更新了一些东西西些东西西些东西西些东西西些东西些东西<br>2. 其实没些东西些东西西些东西西些东西西些东西有更新什么"
            )));

            emitter.onComplete();
        });
    }

    @Override
    public Observable<BasicResponseEntity<Object>> send(String countryCode, String phone, int scene) {
        return Observable.create(emitter -> {
            emitter.onNext(new BasicResponseEntity<>(null));
            emitter.onComplete();
        });
    }

    @Override
    public Observable<BasicResponseEntity<RegisterEntity>> register(
            String countryCode, String phone, String verifyCode, String password, String inviteCode) {

//        return Observable.create(emitter -> {
//            emitter.onNext(new BasicResponseEntity<RegisterEntity>(mUserDetail));
//            emitter.onComplete();
//        });
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<UserDetailEntity>> completeInfo(String birth_time,String avater,
            String nickname, int sex, long birthDay, int birthHour, int calendarType, String birthArea) {

        return Observable.create(emitter -> {
            emitter.onNext(new BasicResponseEntity<>(mUserDetail));
            emitter.onComplete();
        });
    }

    @Override
    public Observable<BasicResponseEntity<UserDetailEntity>> login(String phone, String countryCode, String password) {

        return Observable.create(emitter -> {
            emitter.onNext(new BasicResponseEntity<>(mUserDetail));
            emitter.onComplete();
        });
    }

    @Override
    public Observable<BasicResponseEntity<Object>> verify(String phone, String countryCode, String verifyCode) {

        return Observable.create(emitter -> {
            emitter.onNext(new BasicResponseEntity<>(mUserDetail));
            emitter.onComplete();
        });
    }

    @Override
    public Observable<BasicResponseEntity<Object>> forgetLoginPassword(
            String password, // 登录密码，明文，格式：8-20位数字+字母/符号组合
            String phone, // 手机号
            String countryCode, // 区号，不填默认为国内
            String verifyCode // 手机验证码，短信服务关闭时默认”12345”
    ) {

        return Observable.create(emitter -> {
            emitter.onNext(new BasicResponseEntity<>(null));
            emitter.onComplete();
        });
    }

    @Override
    public Observable<BasicResponseEntity<Object>> updateLoginPassword(String password, String phone, String countryCode, String verifyCode) {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<Object>> updatePayPassword(String password, String phone, String countryCode, String verifyCode) {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<UserDetailEntity>> userDetail() {

        return Observable.create(emitter -> {
            emitter.onNext(new BasicResponseEntity<>(mUserDetail));
            emitter.onComplete();
        });
    }

    @Override
    public Observable<BasicResponseEntity<Object>> updateAvatar(String avatarPath) {

        return Observable.create(emitter -> {
            emitter.onNext(new BasicResponseEntity<>(null));
            emitter.onComplete();
        });
    }

    @Override
    public Observable<BasicResponseEntity<Object>> updateUserDetail(String birth_time,
            String nickname, String name, int sex, long birthDay, int birthHour, int calendarType, String birthArea, String liveArea) {

        return Observable.create(emitter -> {
            emitter.onNext(new BasicResponseEntity<>(null));
            emitter.onComplete();
        });
    }


    @Override
    public Observable<BasicResponseEntity<Object>> applyMaster(String content, String phone, List<String> paths) {

        return Observable.create(emitter -> {
            emitter.onNext(new BasicResponseEntity<>(null));
            emitter.onComplete();
        });
    }

    @Override
    public Observable<BasicResponseEntity<Integer>> applyMasterStatus() {
        return null;
    }


    @Override
    public Observable<BasicResponseEntity<List<MasterServiceTypeEntity>>> serviceType() {

        return Observable.create(emitter -> {
            emitter.onNext(new BasicResponseEntity<>(
                    new ArrayList<>(
                            Arrays.asList(
                                    new MasterServiceTypeEntity(0, "问事"),
                                    new MasterServiceTypeEntity(2, "择日"),
                                    new MasterServiceTypeEntity(3, "姓名"),
                                    new MasterServiceTypeEntity(4, "投石问路")
                            )
                    )
            ));

            emitter.onComplete();
        });
    }

//    @Override
//    public Observable<BasicListResponseEntity<MasterServiceEntity>> serviceList(
//            int page,
//            int typeId, // 项目类型
//            int sortType, // 排序类型，1评分; 2解答次数; 3收费金额
//            int priceSort // 价格排序
//    ) {
//        return Observable.create(emitter -> {
//
//            List<MasterServiceEntity> list = new ArrayList<>();
//            for (int i = 0; i < typeId; i++) {
//                list.add(new MasterServiceEntity());
//            }
//
//            //Thread.sleep(1000);
//
//            emitter.onNext(new BasicListResponseEntity<>(
//                    new BasicListResponseEntity.PageDataEntity<>(
//                            list, 0
//                    )
//            ));
//
//            emitter.onComplete();
//        });
//    }

    @Override
    public Observable<BasicListResponseEntity<MasterServiceEntity>> serviceList(int page) {
        return Observable.create(emitter -> {

            List<MasterServiceEntity> list = new ArrayList<>();
            //Thread.sleep(1000);

            emitter.onNext(new BasicListResponseEntity<>(
                    new BasicListResponseEntity.PageDataEntity<>(
                            list, 0
                    )
            ));

            emitter.onComplete();
        });
    }

//    @Override
//    public Observable<BasicResponseEntity<MasterDetailEntity>> masterDetail() {
//        return Observable.create(emitter -> {
//            emitter.onNext(new BasicResponseEntity<>(new MasterDetailEntity(
//                    "简介：这是一段200子的文字，庚子年为壁上土命，庚子，厚德之土，能克众水，不忌他木，盖木至子无气，若遇壬申之金，谓之明位禄，其贵必矣。…简介：这是一段200子的文字，庚子年为壁上土命，庚子，厚德之土，能克众水，不忌他木，盖木至子无气，若遇壬申之金，谓之明位禄，其贵必矣。",
//                    Arrays.asList(
//                            new MasterServiceTypeEntity(1, "婚姻情感"),
//                            new MasterServiceTypeEntity(2, "事业财运")
//                    ),
//                    Arrays.asList(
//                            new MasterPhotoEntity(1, "https://gank.io/images/3e4423173d0a4c5e8447c0335b4458fc"),
//                            new MasterPhotoEntity(2, "https://gank.io/images/02eb8ca3297f4931ab64b7ebd7b5b89c")
//                    )
//            )));
//            emitter.onComplete();
//        });
//    }

    @Override
    public Observable<BasicResponseEntity<Object>> updateMasterIntro(String intro) {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<Object>> updateMasterFields(List<Integer> fields) {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<MasterDetailEntityNew>> masterDetail(int masterid,int uid,int userid) {
        return null;
//        return Observable.create(emitter -> {
//            emitter.onNext(new BasicResponseEntity<>(new MasterDetailEntityNew(
//                    "张三",
//                    "https://gank.io/images/3e4423173d0a4c5e8447c0335b4458fc",
//                    "问事,择日",
//                    4.5F,
//                    "\t\t简介：这是一段200字的简介。道家五行学说旨在描述事物运动形式以及转化关系。" +
//                            "五行学说是我国古代的取象比类学说，不是五种元素，而是将万事万物按照润下、炎上、曲直、从革、稼穑的性质归属到水火木金土五个项目中，" +
//                            "与西方古代的地、水、火、风四元素学说有区别，是集哲学、占卜、算命、历法、中医学、社会学等诸多学于一身的理论。 \n\t\t" +
//                            "五行系指古人把宇宙万物划分为五种性质的事物，分成木、火、土、金、水五大类，叫它们为“五行”。",
//                    100,
//                    Arrays.asList(
//                            new MasterPhotoEntity(1, "https://gank.io/images/3e4423173d0a4c5e8447c0335b4458fc"),
//                            new MasterPhotoEntity(2, "https://gank.io/images/02eb8ca3297f4931ab64b7ebd7b5b89c"),
//                            new MasterPhotoEntity(3, "https://gank.io/images/3e4423173d0a4c5e8447c0335b4458fc"),
//                            new MasterPhotoEntity(4, "https://gank.io/images/882afc997ad84f8ab2a313f6ce0f3522"),
//                            new MasterPhotoEntity(5, "https://gank.io/images/3e4423173d0a4c5e8447c0335b4458fc")
//                    ),
//                    Arrays.asList(
//                            new MasterServiceItemEntity("婚姻情感", 33.4F, "https://static.easyicon.net/preview/128/1282315.gif"),
//                            new MasterServiceItemEntity("事业财运", 99.9F, "https://static.easyicon.net/preview/128/1287603.gif")
//                    )
//            )));
//
//            emitter.onComplete();
//        });
    }

    @Override
    public Observable<BasicResponseEntity<MasterCommentEntity>> getMasterComment(int page,
                                                                                 int pagesize, int uid,int masterid) {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<MasterServiceSettingEntity>> masterServices() {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<FortuneEntity>> personalFortune(int timestamp, int type) {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<List<FateBooksEntity>>> fateBooksList() {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<List<FateBookTypeEntity>>> fateBookTypes(int fateBookId) {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<FateBookDetailEntity>> fateBookDetail(int fateBookId, int cateId) {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<FateBookEntity>> createFateBook(String name, int sex, long birthDay, int birthHour) {
        return Observable.create(emitter -> {
            emitter.onNext(new BasicResponseEntity<>(null));
            emitter.onComplete();
        });
    }

    @Override
    public Observable<BasicResponseEntity<Object>> deleteFateBook(int id) {
        return Observable.create(emitter -> {
            emitter.onNext(new BasicResponseEntity<>(null));
            emitter.onComplete();
        });
    }

    @Override
    public Observable<BasicResponseEntity<Object>> buyFateBook(int id, String password, int isAll, int type, String cateId, String ak, long timestamp, String sign) {
        return null;
    }



    @Override
    public Observable<BasicResponseEntity<OrderLifeBookEntity>> orderLifeBookEntity(int id, String cateId, String ak, long timestamp, String sign) {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<OrderMemberEntity>> orderMember(int id, String ak, long timestamp, String sign) {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<Float>> balance() {
        return null;
    }

    @Override
    public Observable<BasicListResponseEntity<PropertyBillEntity>> propertyBill(int page, int type) {
        return null;
    }

    @Override
    public Observable<BasicListResponseEntity<SystemMessageEntity>> systemMessage(int page) {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<Object>> transfer(long uid, float amount, String password, long timestamp, String ak, String sign) {
        return null;
    }


//    @Override
//    public Observable<BasicResponseEntity<InviteListEntity>> inviteList(int page) {
//        return null;
//    }

    @Override
    public Observable<BasicResponseEntity<CalendarDetailEntity>> calendarDetail(int timestamp) {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<List<BannerEntity>>> bannerList() {
        return null;
    }

    @Override
    public Observable<BasicListResponseEntity<SystemMessageEntity>> noticeList(int page) {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<CatePriceEntity>> getCatePrice(int id) {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<List<RechargeActivitiesEntity>>> rechargeActivitiesList() {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<List<VipTypeEntity>>> vipList() {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<InvitationSummaryEntity>> invitationSummary() {
        return null;
    }

    @Override
    public Observable<BasicListResponseEntity<RechargeRecordEntity>> rechargeRecord(int todaySort, int totalSort, int page) {
        return null;
    }

    @Override
    public Observable<BasicListResponseEntity<InviteListEntity>> inviteList(int todaySort, int totalSort, int page) {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<HomeDataEntity>> homeData() {
        return Observable.create(emitter -> {
            emitter.onNext(new BasicResponseEntity<>(new HomeDataEntity(
                    Arrays.asList(
                            new HomeDataEntity.BannerBean("https://ossweb-img.qq.com/upload/adw/image/20200811/9e4d56ba9e78f61cd2d59d5c23c6b919.jpeg", ""),
                            new HomeDataEntity.BannerBean("https://ossweb-img.qq.com/upload/adw/image/20200821/17ad3545f2ccab7da4de0348a2a92d87.jpeg", ""),
                            new HomeDataEntity.BannerBean("https://ossweb-img.qq.com/upload/adw/image/20200807/60a6ba105f313ca9b4f66d6705b80d54.jpeg", "")
                    ),
                    Arrays.asList(
                            new HomeDataEntity.NoticeBean("游戏环境公示及处罚名单8月21日", "亲爱的召唤师：\n" +
                                    "\n" +
                                    "游戏环境与竞技公平一直是英雄联盟对局体验的基石，也是产品一路走来恪守不渝的承诺。这些年来，我们很高兴看到，保持良好游戏行为、维护峡谷环境已经成为越来越多召唤师的共同选择，但另一方面，口头谩骂、挂机、消极游戏等破坏游戏环境的现象依然时有发生，这些负面行为对广大召唤师的游戏体验造成了不良的影响。"),
                            new HomeDataEntity.NoticeBean("8月14日免费英雄更新公告", "另外,在中国的服务器中,我们额外增加了四个永久免费使用的英雄 - 迅捷斥候提莫、寒冰射手艾希、德玛西亚之力盖伦和符文法师瑞兹。主要目的也是便于那些刚刚打完新手实战训练营的玩家能够至少有一个自己会用的英雄。")
                    ),
                    Arrays.asList(
                            new HomeDataEntity.EntryBean(1, "狂战士", "https://game.gtimg.cn/images/lol/act/img/champion/Olaf.png"),
                            new HomeDataEntity.EntryBean(1, "远古恐惧", "https://game.gtimg.cn/images/lol/act/img/champion/FiddleSticks.png"),
                            new HomeDataEntity.EntryBean(1, "Doinb", "https://dss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=943587823,257477234&fm=179&app=42&f=JPEG?w=75&h=75"),
                            new HomeDataEntity.EntryBean(1, "Letme", "https://dss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2335181182,3043423220&fm=179&app=42&f=JPEG?w=75&h=75"),
                            new HomeDataEntity.EntryBean(1, "imp", "https://dss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=1703490102,2525619284&fm=179&app=42&f=JPEG?w=75&h=75"),
                            new HomeDataEntity.EntryBean(1, "UZI", "https://dss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=790604882,2677533922&fm=179&app=42&f=JPEG?w=75&h=75"),
                            new HomeDataEntity.EntryBean(1, "UZI", "https://dss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=790604882,2677533922&fm=179&app=42&f=JPEG?w=75&h=75"),
                            new HomeDataEntity.EntryBean(1, "明凯", "https://dss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2420783683,2600182030&fm=179&app=42&f=JPEG?w=75&h=75")
                    ),
                    Arrays.asList(
                            new HomeDataEntity.MasterBean("1", "https://game.gtimg.cn/images/lol/act/a20200721yone/sell-1.jpg"),
                            new HomeDataEntity.MasterBean("2", "https://game.gtimg.cn/images/lol/act/a20200721yone/sell-2.jpg")
                    ),
                    Collections.singletonList(
                            new HomeDataEntity.ToolBean("", "https://game.gtimg.cn/images/lol/act/a20200721yone/packpage.png")
                    ),
                    new HomeDataEntity.WeatherBean("深圳市", 35.1f, 33.1f, 34.2f, "晴")
            )));
            emitter.onComplete();
        });
    }

    @Override
    public Observable<BasicResponseEntity<PrePayResult>> buyFateBookPrepay(int pay_type, String order_no, int discount_id, String ak, long timestamp, String sign) {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<PrePayResult>> buyMemberPrepay(int pay_type, String order_no, int discount_id, String ak, long timestamp, String sign) {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<PrePayResult>> buyQuestion(int pay_type, int issue_id, String ak, long timestamp, String sign) {
        return null;
    }


    @Override
    public Observable<BasicResponseEntity<HomeFouncationEntity>> entry() {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<List<CalendarHourEntity>>> calendarHour() {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<CalendarDayEntity>> calendarDay() {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<ArticleListEntity>> articleCateList(int cate_id, int page, int page_size) {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<List<ArticleEntity>>> articleCate() {
        return null;
    }



    @Override
    public Observable<BasicResponseEntity<List<BannerEntity>>> banner(int type) {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<List<DailyQuestionEntity>>> getDailyQuestion(int index) {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<TrendEntity>> getTrendData() {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<MonthBlessEntity>> getMonthBless(String month) {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<String>> addMonthBless(String content) {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<OrderResultEntity>> askQuestion(String nickname, int gender, int master_id, int birth_day, int birth_hour, int calendar_type, String birth_area, String content, String paths, String ak, long timestamp, String sign) {
        return null;
    }


    @Override
    public Observable<BasicListResponseEntity<OrderResultEntity>> getOrderMaster(int status, int page,
                                                                                     int page_size) {
        return null;
    }

    @Override
    public Observable<BasicListResponseEntity<OrderResultEntity>> getOrderUser(int status, int page, int page_size) {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<QDetailEntity>> getAnswerDetailMast(int issue_id) {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<QDetailEntity>> getAnswerDetailUser(int issue_id) {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<String>> doneAnswer(int issue_id, String content, String ak, long timestamp, String sign) {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<String>> orderRefused(int issue_id, String reason, String ak, long timestamp, String sign) {
        return null;
    }


    @Override
    public Observable<BasicResponseEntity<String>> orderComment(int issue_id, int score) {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<String>> orderCancel(int issue_id, String ak, long timestamp, String sign) {
        return null;
    }


    @Override
    public Observable<BasicListResponseEntity<MessageEntity.ItemsBean>> getMessage(int page) {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<UserAssetEntity>> getUserAssets() {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<H5BaseEntity>> getH5URL() {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<UrlConfigEntity>> getConfigURL() {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<SwitchStatusEntity>> getSwitchStatus() {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<String>> updateSwitchFortune(int fortune_switch) {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<String>> updateSwitchMsg(int fortune_switch) {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<String>> sendFeedBack(String content, String images) {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<AdEntity>> getAdConfig() {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<AddfortuneDataEntity>> getAddfortuneData(int timestamp) {
        return null;
    }

    @Override
    public Observable<BasicResponseEntity<Float>> getTranFee() {
        return null;
    }

}
