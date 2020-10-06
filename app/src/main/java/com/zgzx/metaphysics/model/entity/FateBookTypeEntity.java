package com.zgzx.metaphysics.model.entity;

import java.util.List;

/**
 * 命书分类
 */
public class FateBookTypeEntity {

    /**
     * id : 30
     * name : 自身
     * sub_cate_list : ["性格","才能","潜在个性及心理状况","整体建议","生活与物质品味的优劣","先天较弱的器官","容易得到的疾病","一生疾病的轻重","解压良方","建议积福方法"]
     * icons : ["https://mystery-oss.oss-cn-hongkong.aliyuncs.com/my.png","https://mystery-oss.oss-cn-hongkong.aliyuncs.com/click.png","https://mystery-oss.oss-cn-hongkong.aliyuncs.com/unopened.png"]
     * is_buy : 1
     */

    private int id;
    private String name;
    private int is_buy;
    private List<String> sub_cate_list;
    private List<String> icons;

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

    public int getIs_buy() {
        return is_buy;
    }

    public void setIs_buy(int is_buy) {
        this.is_buy = is_buy;
    }

    public List<String> getSub_cate_list() {
        return sub_cate_list;
    }

    public void setSub_cate_list(List<String> sub_cate_list) {
        this.sub_cate_list = sub_cate_list;
    }

    public List<String> getIcons() {
        return icons;
    }

    public void setIcons(List<String> icons) {
        this.icons = icons;
    }
}
