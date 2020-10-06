package com.zgzx.metaphysics.model.entity;

import java.util.List;

public class CalendarDetailEntity {

    /**
     * suitable : 嫁娶 安葬 安床 动土 修屋 求财 建造 埋葬
     * avoid : 祈福 求嗣 赴任
     * lash : 猪日冲蛇
     * day_foot : 丁亥日得病：西北方冲着外来男女私情、落水亡神、家內自縊亡神、地主动、西北方有木犯，主病身热、心痛、肚痛，用黑钱四百、纸马、水、饭、果、酒，向西北方送吉。
     * year : 2020
     * month : 8
     * day : 12
     * year_ag : 2020
     * month_ag : 六月
     * day_ag : 廿三
     * year_column : 庚子
     * month_column : 甲申
     * day_column : 丁亥
     * hour_column : 己酉
     * night28 : {"explain":"璧星造作主增财，丝蚕大熟福滔天，奴婢自来人口进，开门放水出英贤，埋葬招财官品进，家中诸事乐陶然，婚姻吉利主贵子，早播名誉著祖鞭。","good_ill_luck":"吉","name":"壁水貐"}
     * twelve_gods : {"explain":"平日为普通、平稳的意思，古人一般只会在家简单修葺一下。","good_ill_luck":"平","name":"平"}
     * peng_zu : {"shi":["丁不剃头头必生疮","亥不嫁娶不利新郎"],"explain":["逢丁日或丁时不宜理发，否则有损身体之真气，引发头疾，如头疮、掉发等。","亥为终结，气数穷尽之时，新添当然不利，故逢亥日或亥时不宜进行婚姻嫁娶之事。"]}
     * shengxiao : 鼠
     * saving_gas : {"name":"立秋","day":7}
     * medium : {"name":"处暑","day":22}
     */

    private String suitable;
    private String avoid;
    private String lash;
    private String day_foot;
    private int year;
    private int month;
    private int day;
    private int year_ag;
    private String month_ag;
    private String day_ag;
    private String year_column;
    private String month_column;
    private String day_column;
    private String hour_column;
    private Night28Bean night28;
    private TwelveGodsBean twelve_gods;
    private PengZuBean peng_zu;
    private String shengxiao;
    private SavingGasBean saving_gas;
    private MediumBean medium;

    public String getSuitable() {
        return suitable;
    }

    public void setSuitable(String suitable) {
        this.suitable = suitable;
    }

    public String getAvoid() {
        return avoid;
    }

    public void setAvoid(String avoid) {
        this.avoid = avoid;
    }

    public String getLash() {
        return lash;
    }

    public void setLash(String lash) {
        this.lash = lash;
    }

    public String getDay_foot() {
        return day_foot;
    }

    public void setDay_foot(String day_foot) {
        this.day_foot = day_foot;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getYear_ag() {
        return year_ag;
    }

    public void setYear_ag(int year_ag) {
        this.year_ag = year_ag;
    }

    public String getMonth_ag() {
        return month_ag;
    }

    public void setMonth_ag(String month_ag) {
        this.month_ag = month_ag;
    }

    public String getDay_ag() {
        return day_ag;
    }

    public void setDay_ag(String day_ag) {
        this.day_ag = day_ag;
    }

    public String getYear_column() {
        return year_column;
    }

    public void setYear_column(String year_column) {
        this.year_column = year_column;
    }

    public String getMonth_column() {
        return month_column;
    }

    public void setMonth_column(String month_column) {
        this.month_column = month_column;
    }

    public String getDay_column() {
        return day_column;
    }

    public void setDay_column(String day_column) {
        this.day_column = day_column;
    }

    public String getHour_column() {
        return hour_column;
    }

    public void setHour_column(String hour_column) {
        this.hour_column = hour_column;
    }

    public Night28Bean getNight28() {
        return night28;
    }

    public void setNight28(Night28Bean night28) {
        this.night28 = night28;
    }

    public TwelveGodsBean getTwelve_gods() {
        return twelve_gods;
    }

    public void setTwelve_gods(TwelveGodsBean twelve_gods) {
        this.twelve_gods = twelve_gods;
    }

    public PengZuBean getPeng_zu() {
        return peng_zu;
    }

    public void setPeng_zu(PengZuBean peng_zu) {
        this.peng_zu = peng_zu;
    }

    public String getShengxiao() {
        return shengxiao;
    }

    public void setShengxiao(String shengxiao) {
        this.shengxiao = shengxiao;
    }

    public SavingGasBean getSaving_gas() {
        return saving_gas;
    }

    public void setSaving_gas(SavingGasBean saving_gas) {
        this.saving_gas = saving_gas;
    }

    public MediumBean getMedium() {
        return medium;
    }

    public void setMedium(MediumBean medium) {
        this.medium = medium;
    }

    public static class Night28Bean {
        /**
         * explain : 璧星造作主增财，丝蚕大熟福滔天，奴婢自来人口进，开门放水出英贤，埋葬招财官品进，家中诸事乐陶然，婚姻吉利主贵子，早播名誉著祖鞭。
         * good_ill_luck : 吉
         * name : 壁水貐
         */

        private String explain;
        private String good_ill_luck;
        private String name;

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }

        public String getGood_ill_luck() {
            return good_ill_luck;
        }

        public void setGood_ill_luck(String good_ill_luck) {
            this.good_ill_luck = good_ill_luck;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class TwelveGodsBean {
        /**
         * explain : 平日为普通、平稳的意思，古人一般只会在家简单修葺一下。
         * good_ill_luck : 平
         * name : 平
         */

        private String explain;
        private String good_ill_luck;
        private String name;

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }

        public String getGood_ill_luck() {
            return good_ill_luck;
        }

        public void setGood_ill_luck(String good_ill_luck) {
            this.good_ill_luck = good_ill_luck;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class PengZuBean {
        private List<String> shi;
        private List<String> explain;

        public List<String> getShi() {
            return shi;
        }

        public void setShi(List<String> shi) {
            this.shi = shi;
        }

        public List<String> getExplain() {
            return explain;
        }

        public void setExplain(List<String> explain) {
            this.explain = explain;
        }
    }

    public static class SavingGasBean {
        /**
         * name : 立秋
         * day : 7
         */

        private String name;
        private int day;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }
    }

    public static class MediumBean {
        /**
         * name : 处暑
         * day : 22
         */

        private String name;
        private int day;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }
    }
}
