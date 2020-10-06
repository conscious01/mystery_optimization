package com.zgzx.metaphysics.model.entity;

import com.zgzx.metaphysics.ui.activities.MasterHomepageActivity;
import com.zgzx.metaphysics.model.IDataSource;

import java.util.List;

/**
 * 发现页面师傅详情
 *
 * @see MasterHomepageActivity
 * @see IDataSource#masterDetail(int)
 */
public class FindMasterDetailEntity {

    /**
     * id : 1
     * master_name : 王师傅
     * photos :
     * fields : 问事,择日
     * score : 3
     * intro : 不错
     * answer_num : 10
     * service_item : [{"name":"问事","money_type":{"id":4,"symbol":"USDT"},"price":66.66},{"name":"择日","money_type":{"id":1,"symbol":"¥"},"price":99.99}]
     */

    private int id;
    private String master_name;
    private String photos;
    private String fields;
    private float score;
    private String intro;
    private int answer_num;
    private List<MasterPhotoEntity> master_photos;
    private List<MasterServiceItemEntity> service_item;

    public FindMasterDetailEntity() {
    }

    public FindMasterDetailEntity(
            String master_name, String photos, String fields, float score, String intro, int answer_num,
            List<MasterPhotoEntity> master_photos, List<MasterServiceItemEntity> service_item) {

        this.master_name = master_name;
        this.photos = photos;
        this.fields = fields;
        this.score = score;
        this.intro = intro;
        this.answer_num = answer_num;
        this.master_photos = master_photos;
        this.service_item = service_item;
    }

    public int getId() {
        return id;
    }

    public String getMaster_name() {
        return master_name;
    }

    public String getPhotos() {
        return photos;
    }

    public String getFields() {
        return fields;
    }

    public float getScore() {
        return score;
    }

    public String getIntro() {
        return intro;
    }

    public int getAnswer_num() {
        return answer_num;
    }

    public List<MasterServiceItemEntity> getService_item() {
        return service_item;
    }

    public List<MasterPhotoEntity> getMaster_photos() {
        return master_photos;
    }
}
