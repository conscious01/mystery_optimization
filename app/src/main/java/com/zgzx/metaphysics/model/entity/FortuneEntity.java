package com.zgzx.metaphysics.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 运程
 */
public class FortuneEntity implements Serializable{


    /**
     * fortune : {"whole":{"score":36,"notes":"凡事皆能逢凶化吉，会有贵人来帮助。凡事皆能逢凶化吉，会有贵人来帮助。","icon":""},"business":{"score":50,"notes":"今天的事业运势没有特别。","icon":""},"relation":{"score":34,"notes":"易犯官司、诉讼，受小人陷害或连累，遇事不讲实话或常会说谎，甚至口出狂言，处事记得小心為上。另外赌性亦会特别强，记得要多多控制自己。小心和父母兄弟、手足拍挡有口舌是非，建议要多多忍让，不要过份坚持己见。","icon":""},"health":{"score":14,"notes":"身体易有损伤。","icon":""},"love":{"score":50,"notes":"已婚者配偶能給到助力，而且与配偶相处和睦。未婚者宜经朋友、同学、同事介绍获得不错的姻缘。","icon":""},"finances":{"score":34,"notes":"情绪容易急躁，亦多是非，另外，耳、鼻、喉、小肠容易感到不适。易遭意外及被朋友连累等不如意之事。","icon":""}}
     * detail : 凡事皆能逢凶化吉，会有贵人来帮助。凡事皆能逢凶化吉，会有贵人来帮助。
     * topic : {"id":5,"topic":"恐惧","pic":"https://tools.foreseers.cn/lesson/lesson5.png","desc":"你的限制在於与能量没有连结。你学到的是：归於自己内在的中心和顺从自己并不安全。早在离开摇篮之前－你就接收到「我使人受不了」的讯息：太吵、佔用太多空间、太自私等等，所以就与自己的生命力切断了连结。你為此付出的代价，是对生命感到恐惧...","short_desc":"在依赖与独立的两个极端中摆盪。","caution":"要能节制过度自由"}
     * number : 6
     * color : 海军蓝
     * color_value : #091F5C
     * date : 2020-09-08
     * position : {"aviodance":"西北","people":"东","gambling":"西","sickness":"北","finance":"西南"}
     * scope : []
     * general_comment : {"average":36,"tips":"今天运势只有一般般，凡事小心谨慎！","add_fortune_score":12}
     * detail_icons : ["https://kongming365.oss-cn-shenzhen.aliyuncs.com/mystery/fortune/yskt.png","https://kongming365.oss-cn-shenzhen.aliyuncs.com/mystery/fortune/zyts.png"]
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
    private List<?> scope;
    private List<String> detail_icons;

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

    public GeneralCommentBean getGeneral_comment() {
        return general_comment;
    }

    public void setGeneral_comment(GeneralCommentBean general_comment) {
        this.general_comment = general_comment;
    }

    public List<?> getScope() {
        return scope;
    }

    public void setScope(List<?> scope) {
        this.scope = scope;
    }

    public List<String> getDetail_icons() {
        return detail_icons;
    }

    public void setDetail_icons(List<String> detail_icons) {
        this.detail_icons = detail_icons;
    }

    public static class FortuneBean {
        /**
         * whole : {"score":36,"notes":"凡事皆能逢凶化吉，会有贵人来帮助。凡事皆能逢凶化吉，会有贵人来帮助。","icon":""}
         * business : {"score":50,"notes":"今天的事业运势没有特别。","icon":""}
         * relation : {"score":34,"notes":"易犯官司、诉讼，受小人陷害或连累，遇事不讲实话或常会说谎，甚至口出狂言，处事记得小心為上。另外赌性亦会特别强，记得要多多控制自己。小心和父母兄弟、手足拍挡有口舌是非，建议要多多忍让，不要过份坚持己见。","icon":""}
         * health : {"score":14,"notes":"身体易有损伤。","icon":""}
         * love : {"score":50,"notes":"已婚者配偶能給到助力，而且与配偶相处和睦。未婚者宜经朋友、同学、同事介绍获得不错的姻缘。","icon":""}
         * finances : {"score":34,"notes":"情绪容易急躁，亦多是非，另外，耳、鼻、喉、小肠容易感到不适。易遭意外及被朋友连累等不如意之事。","icon":""}
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
             * score : 36
             * notes : 凡事皆能逢凶化吉，会有贵人来帮助。凡事皆能逢凶化吉，会有贵人来帮助。
             * icon :
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
         * id : 5
         * topic : 恐惧
         * pic : https://tools.foreseers.cn/lesson/lesson5.png
         * desc : 你的限制在於与能量没有连结。你学到的是：归於自己内在的中心和顺从自己并不安全。早在离开摇篮之前－你就接收到「我使人受不了」的讯息：太吵、佔用太多空间、太自私等等，所以就与自己的生命力切断了连结。你為此付出的代价，是对生命感到恐惧...
         * short_desc : 在依赖与独立的两个极端中摆盪。
         * caution : 要能节制过度自由
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
         * aviodance : 西北
         * people : 东
         * gambling : 西
         * sickness : 北
         * finance : 西南
         */

        private String aviodance;
        private String people;
        private String gambling;
        private String sickness;
        private String finance;

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
         * add_fortune_score : 12
         */

        private int average;
        private String tips;
        private int add_fortune_score;

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

        public int getAdd_fortune_score() {
            return add_fortune_score;
        }

        public void setAdd_fortune_score(int add_fortune_score) {
            this.add_fortune_score = add_fortune_score;
        }
    }
}
