package com.zgzx.metaphysics.model.entity;

import java.io.Serializable;
import java.util.List;

public class OrderEntity implements Serializable {
    /**
     * page : 1
     * page_size : 20
     * total : 3
     * items : [{"id":3,"orderno":"","trade_name":"Android-孔明在线-普通提问-麦玲玲","price":99.99,
     * "create_time":1598014730,"pay_time":1598014722,"trans_time":1599552683,"status":2,
     * "end_time":1719699},{"id":2,"orderno":"","trade_name":"Android-孔明在线-普通提问-麦玲玲","price":99
     * .99,"create_time":1598014720,"pay_time":1598014722,"trans_time":1597910630,"status":5,
     * "end_time":1719699},{"id":1,"orderno":"","trade_name":"Android-孔明在线-普通提问-麦玲玲","price":99
     * .99,"create_time":1598014710,"pay_time":1598014722,"trans_time":1597910630,"status":4,
     * "end_time":1719699}]
     */

    private int page;
    private int page_size;
    private int total;
    public List<OrderResultEntity> items;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<OrderResultEntity> getItems() {
        return items;
    }

    public void setItems(List<OrderResultEntity> items) {
        this.items = items;
    }

//    public static class ItemsBean extends OrderResultEntity implements Serializable {
//        /**
//         * id : 3
//         * orderno :
//         * trade_name : Android-孔明在线-普通提问-麦玲玲
//         * price : 99.99
//         * create_time : 1598014730
//         * pay_time : 1598014722
//         * trans_time : 1599552683
//         * status : 2
//         * end_time : 1719699
//         */
//
////        private int id;
////        private String orderno;
////        private String trade_name;
////        private double price;
////        private int create_time;
////        private int pay_time;
////        private int trans_time;
////        private int status;
////        private int end_time;
////
////        public int getId() {
////            return id;
////        }
////
////        public void setId(int id) {
////            this.id = id;
////        }
////
////        public String getOrderno() {
////            return orderno;
////        }
////
////        public void setOrderno(String orderno) {
////            this.orderno = orderno;
////        }
////
////        public String getTrade_name() {
////            return trade_name;
////        }
////
////        public void setTrade_name(String trade_name) {
////            this.trade_name = trade_name;
////        }
////
////        public double getPrice() {
////            return price;
////        }
////
////        public void setPrice(double price) {
////            this.price = price;
////        }
////
////        public int getCreate_time() {
////            return create_time;
////        }
////
////        public void setCreate_time(int create_time) {
////            this.create_time = create_time;
////        }
////
////        public int getPay_time() {
////            return pay_time;
////        }
////
////        public void setPay_time(int pay_time) {
////            this.pay_time = pay_time;
////        }
////
////        public int getTrans_time() {
////            return trans_time;
////        }
////
////        public void setTrans_time(int trans_time) {
////            this.trans_time = trans_time;
////        }
////
////        public int getStatus() {
////            return status;
////        }
////
////        public void setStatus(int status) {
////            this.status = status;
////        }
////
////        public int getEnd_time() {
////            return end_time;
////        }
////
////        public void setEnd_time(int end_time) {
////            this.end_time = end_time;
////        }
//    }
}
