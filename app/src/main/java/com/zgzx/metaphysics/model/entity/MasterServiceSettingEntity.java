package com.zgzx.metaphysics.model.entity;

import java.util.List;

/**
 * 师傅服务设置
 */
public class MasterServiceSettingEntity {

    /**
     * items : [{"item_type":{"id":2,"name":"择日","icon":""},"charge_amount":5.67,"opening":1},{"item_type":{"id":1,"name":"问事","icon":""},"charge_amount":5,"opening":1},{"item_type":{"id":3,"name":"","icon":""},"charge_amount":0,"opening":2},{"item_type":{"id":4,"name":"","icon":""},"charge_amount":0,"opening":2}]
     * money_type : {"id":1,"symbol":"¥"}
     * biz_status : 1
     */

    private MoneyTypeBean money_type;
    private int biz_status;
    private List<ItemsBean> items;

    public MoneyTypeBean getMoney_type() {
        return money_type;
    }

    public void setMoney_type(MoneyTypeBean money_type) {
        this.money_type = money_type;
    }

    public int getBiz_status() {
        return biz_status;
    }

    public void setBiz_status(int biz_status) {
        this.biz_status = biz_status;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class MoneyTypeBean {
        /**
         * id : 1
         * symbol : ¥
         */

        private int id;
        private String symbol;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }
    }

    public static class ItemsBean {
        /**
         * item_type : {"id":2,"name":"择日","icon":""}
         * charge_amount : 5.67
         * opening : 1
         */

        private ItemTypeBean item_type;
        private double charge_amount;
        private int opening;

        public ItemTypeBean getItem_type() {
            return item_type;
        }

        public void setItem_type(ItemTypeBean item_type) {
            this.item_type = item_type;
        }

        public double getCharge_amount() {
            return charge_amount;
        }

        public void setCharge_amount(double charge_amount) {
            this.charge_amount = charge_amount;
        }

        public int getOpening() {
            return opening;
        }

        public void setOpening(int opening) {
            this.opening = opening;
        }

        public static class ItemTypeBean {
            /**
             * id : 2
             * name : 择日
             * icon :
             */

            private int id;
            private String name;
            private String icon;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }
        }
    }

}
