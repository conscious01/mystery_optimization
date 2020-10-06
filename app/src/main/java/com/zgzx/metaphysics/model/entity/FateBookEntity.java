package com.zgzx.metaphysics.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FateBookEntity {

    /**
     * id : 500
     * uid : 1001907
     * date : 2020-08-06
     * time : 11:00:00
     * real_name : 你好啊1212
     * gender : 1
     * status : 0
     * is_my : 0
     * abstract : {"horoscope":{"value":"狮子座","match":["白羊座","狮子座","射手座"]},"zodiac":{"value":"鼠","match":["牛","猴","龙"]},"bazi":{"value":["庚子","癸未","辛巳","甲午"],"match":["丑","酉","申"]}}
     * cate : [{"cate_id":30,"name":"自身","sub_cate":["性格","才能","潜在个性及心理状况","整体建议","生活与物质品味的优劣","先天较弱的器官","容易得到的疾病","一生疾病的轻重","解压良方","建议积福方法"],"is_buy":true},{"cate_id":31,"name":"爱情","sub_cate":["感情运","理想的另一半","另一半的个性","与另一半的相处模式","另一半与你相处的良方","另一半处理冷战局势的良方","性生活表现","增加情趣及翻倍享受的方法"],"is_buy":true},{"cate_id":32,"name":"事业及学业","sub_cate":["发展倾向","能力","适合从事的行业","适合打工或创业","创业方向","合伙创业趋势","部属类型及相处状况","出外际遇与状况","给予别人的印象","伯乐类型","学习态度","提升学习能力的方法"],"is_buy":true},{"cate_id":33,"name":"资产及财运","sub_cate":["整体财运","财富性质及多寡","对财富的态度或进财方法","控制支出的方法","致富之道","不动产状况","祖业庇荫状况","居家环境"],"is_buy":true},{"cate_id":34,"name":"家庭及人际","sub_cate":["父母个性","与父母的感情","父母的影响","兄弟姊妹个性及关系","生育能力及子女特质","培育子女的方法","朋友类型及相处状况","交友倾向"],"is_buy":true}]
     */

    private int id;
    private int uid;
    private String date;
    private String time;
    private String real_name;
    private int gender;
    private int status;
    private int is_my;
    @SerializedName("abstract")
    private AbstractBean abstractX;
    private List<CateBean> cate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIs_my() {
        return is_my;
    }

    public void setIs_my(int is_my) {
        this.is_my = is_my;
    }

    public AbstractBean getAbstractX() {
        return abstractX;
    }

    public void setAbstractX(AbstractBean abstractX) {
        this.abstractX = abstractX;
    }

    public List<CateBean> getCate() {
        return cate;
    }

    public void setCate(List<CateBean> cate) {
        this.cate = cate;
    }

    public static class AbstractBean {
        /**
         * horoscope : {"value":"狮子座","match":["白羊座","狮子座","射手座"]}
         * zodiac : {"value":"鼠","match":["牛","猴","龙"]}
         * bazi : {"value":["庚子","癸未","辛巳","甲午"],"match":["丑","酉","申"]}
         */

        private HoroscopeBean horoscope;
        private ZodiacBean zodiac;
        private BaziBean bazi;

        public HoroscopeBean getHoroscope() {
            return horoscope;
        }

        public void setHoroscope(HoroscopeBean horoscope) {
            this.horoscope = horoscope;
        }

        public ZodiacBean getZodiac() {
            return zodiac;
        }

        public void setZodiac(ZodiacBean zodiac) {
            this.zodiac = zodiac;
        }

        public BaziBean getBazi() {
            return bazi;
        }

        public void setBazi(BaziBean bazi) {
            this.bazi = bazi;
        }

        public static class HoroscopeBean {
            /**
             * value : 狮子座
             * match : ["白羊座","狮子座","射手座"]
             */

            private String value;
            private List<String> match;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public List<String> getMatch() {
                return match;
            }

            public void setMatch(List<String> match) {
                this.match = match;
            }
        }

        public static class ZodiacBean {
            /**
             * value : 鼠
             * match : ["牛","猴","龙"]
             */

            private String value;
            private List<String> match;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public List<String> getMatch() {
                return match;
            }

            public void setMatch(List<String> match) {
                this.match = match;
            }
        }

        public static class BaziBean {
            private List<String> value;
            private List<String> match;

            public List<String> getValue() {
                return value;
            }

            public void setValue(List<String> value) {
                this.value = value;
            }

            public List<String> getMatch() {
                return match;
            }

            public void setMatch(List<String> match) {
                this.match = match;
            }
        }
    }

    public static class CateBean {
        /**
         * cate_id : 30
         * name : 自身
         * sub_cate : ["性格","才能","潜在个性及心理状况","整体建议","生活与物质品味的优劣","先天较弱的器官","容易得到的疾病","一生疾病的轻重","解压良方","建议积福方法"]
         * is_buy : true
         */

        private int cate_id;
        private String name;
        private boolean is_buy;
        private List<String> sub_cate;

        public int getCate_id() {
            return cate_id;
        }

        public void setCate_id(int cate_id) {
            this.cate_id = cate_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isIs_buy() {
            return is_buy;
        }

        public void setIs_buy(boolean is_buy) {
            this.is_buy = is_buy;
        }

        public List<String> getSub_cate() {
            return sub_cate;
        }

        public void setSub_cate(List<String> sub_cate) {
            this.sub_cate = sub_cate;
        }
    }
}
