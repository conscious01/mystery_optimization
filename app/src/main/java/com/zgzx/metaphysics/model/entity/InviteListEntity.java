package com.zgzx.metaphysics.model.entity;

public class InviteListEntity {

    /**
     * id : 1000192
     * today : 0
     * total : 0
     */

    private int id;
    private int today;
    private int total;
    private int create_time;
    private int amount;

    public int getCreate_time() {
        return create_time;
    }

    public int getAmount() {
        return amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getToday() {
        return today;
    }

    public void setToday(int today) {
        this.today = today;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
