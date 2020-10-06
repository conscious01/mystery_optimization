package com.zgzx.metaphysics.model.entity;

import java.util.List;

public class CalendarDayEntity {


        /**
         * caishen : 東北
         * xishen : 東北
         * fushen : 東南
         * chong : 沖（戊申）猴
         * sha : 煞北
         * yi : ["【日值四廢","大事勿用】","安葬","赴任","破土","祭祀","解除","啓鑽","捕捉","除服","立券","求醫","栽種","求財","詞訟","和訟","打官司","穿井"]
         * ji : ["【日值四廢","大事勿用】","搬家","裝修","開業","結婚","入宅","領證","開工","安門","安床","出行","訂婚","上梁","開張","旅遊","修造","祈福","開市","納財","納畜","嫁娶","納采","移徙","蓋屋","冠笄","豎柱","齋醮","分居","開倉","置産"]
         */

        private String caishen;
        private String xishen;
        private String fushen;
        private String chong;
        private String sha;
        private List<String> yi;
        private List<String> ji;

        public String getCaishen() {
            return caishen;
        }

        public void setCaishen(String caishen) {
            this.caishen = caishen;
        }

        public String getXishen() {
            return xishen;
        }

        public void setXishen(String xishen) {
            this.xishen = xishen;
        }

        public String getFushen() {
            return fushen;
        }

        public void setFushen(String fushen) {
            this.fushen = fushen;
        }

        public String getChong() {
            return chong;
        }

        public void setChong(String chong) {
            this.chong = chong;
        }

        public String getSha() {
            return sha;
        }

        public void setSha(String sha) {
            this.sha = sha;
        }

        public List<String> getYi() {
            return yi;
        }

        public void setYi(List<String> yi) {
            this.yi = yi;
        }

        public List<String> getJi() {
            return ji;
        }

        public void setJi(List<String> ji) {
            this.ji = ji;
        }

}
