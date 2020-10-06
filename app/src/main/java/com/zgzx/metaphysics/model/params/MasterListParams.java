package com.zgzx.metaphysics.model.params;

/**
 * 师傅列表参数
 */
public class MasterListParams {

    private Page2Params page_param;

    private int page,page_size=10;
//    private int item_type; // 项目类型:1问事,2择日,3取名
//
//    private int sort_type; // 排序类型:1-评分; 2-解答次数; 3-收费金额
//
//    private int price_sort; // 价格排序类型:1正序;2倒序.备注:如果sort_type传的是3,则此字段必传
//
//    public MasterListParams(PageParams page_param, int item_type, int sort_type, int price_sort) {
//        this.page_param = page_param;
//        this.item_type = item_type;
//        this.sort_type = sort_type;
//        this.price_sort = price_sort;
//    }


    public MasterListParams(Page2Params page_param) {
        this.page_param = page_param;
    }

    public MasterListParams(int page) {
        this.page = page;

    }
}
