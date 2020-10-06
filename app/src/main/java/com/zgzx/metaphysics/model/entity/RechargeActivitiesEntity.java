package com.zgzx.metaphysics.model.entity;

import java.util.List;

public class RechargeActivitiesEntity {

    /**
     * code : 0
     * msg : 成功
     * data : [{"id":1,"price":13,"coin_num":10,"give_num":0},{"id":2,"price":65,"coin_num":50,"give_num":0},{"id":3,"price":260,"coin_num":200,"give_num":20},{"id":4,"price":650,"coin_num":500,"give_num":100},{"id":5,"price":2600,"coin_num":2500,"give_num":50},{"id":6,"price":6500,"coin_num":7000,"give_num":2000}]
     */


        /**
         * id : 1
         * price : 13
         * coin_num : 10
         * give_num : 0
         */

        private int id;
        private float price;
        private int coin_num;
        private int give_num;
    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public int getCoin_num() {
            return coin_num;
        }

        public void setCoin_num(int coin_num) {
            this.coin_num = coin_num;
        }

        public int getGive_num() {
            return give_num;
        }

        public void setGive_num(int give_num) {
            this.give_num = give_num;
        }

}
