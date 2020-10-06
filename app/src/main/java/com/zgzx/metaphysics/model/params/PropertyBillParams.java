package com.zgzx.metaphysics.model.params;

public class PropertyBillParams {

    /**
     * 一级流水类型. 1 充值, 2 提现,3 问答,4 分佣, 5 奖励, 6 划转, 7 其他
     */
    private int type;

    /**
     * 二级流水类型.
     * 1,充值孔明珠
     * 2,师傅或股东操作提现
     * 3,向师傅提问
     * 4,问答订单退款
     * 5,师傅解答订单收益
     * 6 股东获取佣金,
     * 7奖励孔明珠,
     * 8购买会员,
     * 9划转孔明珠
     * 10,购买命书
     */
    private int sub_type;

    private PageParams page_param;

    public PropertyBillParams(int page, int type) {
        this.type = type;
        this.page_param = new PageParams(page);
    }

}
