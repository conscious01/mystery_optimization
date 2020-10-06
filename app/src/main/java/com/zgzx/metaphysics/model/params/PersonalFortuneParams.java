package com.zgzx.metaphysics.model.params;

/**
 * @Author: helloxiangsheng@163.com
 * @Date: 2020/8/24
 * @Description: 个人运程
 */
public class PersonalFortuneParams {

    private int timestamp;
    private int op_type; //1-首页获取，无需完善信息 2-获取每日运势详情，必须完善信息

    public PersonalFortuneParams(int timestamp, int op_type) {
        this.timestamp = timestamp;
        this.op_type = op_type;
    }

}
