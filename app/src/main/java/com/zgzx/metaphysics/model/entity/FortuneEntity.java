package com.zgzx.metaphysics.model.entity;

import java.util.List;

/**
 * 运程
 */
public class FortuneEntity {

    /**
     * fortune : {"whole":{"score":46,"notes":"凡事皆能逢凶化吉，会有贵人来帮助。凡事皆能逢凶化吉，会有贵人来帮助。"},"business":{"score":60,"notes":"容易发挥不错的领导能力，应该多做主导，好好运用一下。"},"relation":{"score":40,"notes":"今天的人际关係运势没有特别。"},"health":{"score":10,"notes":"容易产生碰撞，弄伤自己身体，也要注意容易打破东西。会较容易有疲態，建议尽量多点休息保持活力。易有血光之灾或水火之意外。容易偏执或性急，记得不要操之过急及感情用事，要注意六亲和健康。要注意面部头部容易受损，亦要留意与长辈关係，易有爭执。"},"love":{"score":70,"notes":"红鸞星动，桃花畅旺。"},"finances":{"score":50,"notes":"会有破财情况出现，要好好管理支出。支出会比较大，记得不要毫无节制乱花钱。整体财运向好，有机会得意外之财。得财比较辛苦，看钱会很重，而且比较节俭，建议减少无谓开支。可借兄弟姊妹或生意拍挡之力得到不错的财运。"}}
     * detail : 凡事皆能逢凶化吉，会有贵人来帮助。凡事皆能逢凶化吉，会有贵人来帮助。
     * topic : {"id":4,"topic":"安全感","pic":"https://tools.foreseers.cn/lesson/lesson4.png","desc":"生命早期的体验教导你，让自己处於一个自然放松状况是不安全的，有时是因为父母之过於紧张，不断强调谨慎的必要。当你失去对自己固有本能的信任，便无法如是地信任实相。结果，你对於生命中的情况觉得没有安全感，很容易紧张，安全感成为生命主要的顾虑...","short_desc":"妄想一步登天跳过许多步骤，野心勃勃，没耐心，缺乏坚持和稳定度。","caution":"去学习改变，改变是唯一不变"}
     * number : 4
     * color : 银
     * color_value : #E8E8E8
     * date : 2020-08-07
     * position : {"aviodance":"东","people":"西南","gambling":"东北","sickness":"西北","finance":"北"}
     * scope : []
     */

    private FortuneBean fortune;
    private String detail;
    private TopicBean topic;
    private String number;
    private String color;
    private String color_value;
    private String date;
    private PositionBean position;
    private GeneralCommentBean general_comment;
    private List<String> detail_icons;
    public GeneralCommentBean getGeneral_comment() {
        return general_comment;
    }

    public void setGeneral_comment(GeneralCommentBean general_comment) {
        this.general_comment = general_comment;
    }

    public FortuneBean getFortune() {
        return fortune;
    }

    public void setFortune(FortuneBean fortune) {
        this.fortune = fortune;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public TopicBean getTopic() {
        return topic;
    }

    public void setTopic(TopicBean topic) {
        this.topic = topic;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor_value() {
        return color_value;
    }

    public void setColor_value(String color_value) {
        this.color_value = color_value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public PositionBean getPosition() {
        return position;
    }

    public void setPosition(PositionBean position) {
        this.position = position;
    }

    public List<String> getDetail_icons() {
        return detail_icons;
    }

    public void setDetail_icons(List<String> detail_icons) {
        this.detail_icons = detail_icons;
    }

    public static class FortuneBean {
        /**
         * whole : {"score":46,"notes":"凡事皆能逢凶化吉，会有贵人来帮助。凡事皆能逢凶化吉，会有贵人来帮助。"}
         * business : {"score":60,"notes":"容易发挥不错的领导能力，应该多做主导，好好运用一下。"}
         * relation : {"score":40,"notes":"今天的人际关係运势没有特别。"}
         * health : {"score":10,"notes":"容易产生碰撞，弄伤自己身体，也要注意容易打破东西。会较容易有疲態，建议尽量多点休息保持活力。易有血光之灾或水火之意外。容易偏执或性急，记得不要操之过急及感情用事，要注意六亲和健康。要注意面部头部容易受损，亦要留意与长辈关係，易有爭执。"}
         * love : {"score":70,"notes":"红鸞星动，桃花畅旺。"}
         * finances : {"score":50,"notes":"会有破财情况出现，要好好管理支出。支出会比较大，记得不要毫无节制乱花钱。整体财运向好，有机会得意外之财。得财比较辛苦，看钱会很重，而且比较节俭，建议减少无谓开支。可借兄弟姊妹或生意拍挡之力得到不错的财运。"}
         */

        private Bean whole;
        private Bean business;
        private Bean relation;
        private Bean health;
        private Bean love;
        private Bean finances;

        public Bean getWhole() {
            return whole;
        }

        public void setWhole(Bean whole) {
            this.whole = whole;
        }

        public Bean getBusiness() {
            return business;
        }

        public void setBusiness(Bean business) {
            this.business = business;
        }

        public Bean getRelation() {
            return relation;
        }

        public void setRelation(Bean relation) {
            this.relation = relation;
        }

        public Bean getHealth() {
            return health;
        }

        public void setHealth(Bean health) {
            this.health = health;
        }

        public Bean getLove() {
            return love;
        }

        public void setLove(Bean love) {
            this.love = love;
        }

        public Bean getFinances() {
            return finances;
        }

        public void setFinances(Bean finances) {
            this.finances = finances;
        }

        public static class Bean {
            /**
             * score : 46
             * notes : 凡事皆能逢凶化吉，会有贵人来帮助。凡事皆能逢凶化吉，会有贵人来帮助。
             */

            private int score;
            private String notes;
            private String icon;
            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public String getNotes() {
                return notes;
            }

            public void setNotes(String notes) {
                this.notes = notes;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }
        }

    }

    public static class TopicBean {
        /**
         * id : 4
         * topic : 安全感
         * pic : https://tools.foreseers.cn/lesson/lesson4.png
         * desc : 生命早期的体验教导你，让自己处於一个自然放松状况是不安全的，有时是因为父母之过於紧张，不断强调谨慎的必要。当你失去对自己固有本能的信任，便无法如是地信任实相。结果，你对於生命中的情况觉得没有安全感，很容易紧张，安全感成为生命主要的顾虑...
         * short_desc : 妄想一步登天跳过许多步骤，野心勃勃，没耐心，缺乏坚持和稳定度。
         * caution : 去学习改变，改变是唯一不变
         */

        private int id;
        private String topic;
        private String pic;
        private String desc;
        private String short_desc;
        private String caution;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getShort_desc() {
            return short_desc;
        }

        public void setShort_desc(String short_desc) {
            this.short_desc = short_desc;
        }

        public String getCaution() {
            return caution;
        }

        public void setCaution(String caution) {
            this.caution = caution;
        }
    }

    public static class PositionBean {
        /**
         * aviodance : 东
         * people : 西南
         * gambling : 东北
         * sickness : 西北
         * finance : 北
         */

        private String aviodance;//避难位
        private String people;//人缘位
        private String gambling;
        private String sickness;//病床位
        private String finance;   //财运位

        public String getAviodance() {
            return aviodance;
        }

        public void setAviodance(String aviodance) {
            this.aviodance = aviodance;
        }

        public String getPeople() {
            return people;
        }

        public void setPeople(String people) {
            this.people = people;
        }

        public String getGambling() {
            return gambling;
        }

        public void setGambling(String gambling) {
            this.gambling = gambling;
        }

        public String getSickness() {
            return sickness;
        }

        public void setSickness(String sickness) {
            this.sickness = sickness;
        }

        public String getFinance() {
            return finance;
        }

        public void setFinance(String finance) {
            this.finance = finance;
        }
    }

    public static class GeneralCommentBean {
        /**
         * average : 36
         * tips : 今天运势只有一般般，凡事小心谨慎！
         */

        private int average;
        private String tips;

        public int getAverage() {
            return average;
        }

        public void setAverage(int average) {
            this.average = average;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }
    }
}
