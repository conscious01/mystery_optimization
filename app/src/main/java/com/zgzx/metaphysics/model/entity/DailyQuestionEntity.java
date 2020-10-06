package com.zgzx.metaphysics.model.entity;

public class DailyQuestionEntity {

        /**
         * id : 36
         * question : 有否横财运？（不鼓励沉迷赌博）
         * answer : 你的财富多以自身努力奋斗赚取得来。虽然你有机会从投资获取更多钱财，但未必是一笔巨额。发横财有机会一朝破败，努力存钱，加上理财有道才是让你富起来的法则。
         * create_time : 1599483666
         */

        private int id;
        private String question;
        private String answer;
        private int create_time;
    private String detail_url;

    public String getDetail_url() {
        return detail_url;
    }

    public void setDetail_url(String detail_url) {
        this.detail_url = detail_url;
    }

    public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

}
